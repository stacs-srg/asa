/*
 * Created on Aug 2, 2005 at 4:07:11 PM.
 */
package uk.ac.stand.dcs.asa.storage.webdav.impl;

import uk.ac.stand.dcs.asa.storage.absfilesystem.interfaces.IFileSystem;
import uk.ac.stand.dcs.asa.storage.locking.interfaces.ILockManager;
import uk.ac.stand.dcs.asa.storage.webdav.exceptions.HTTPException;
import uk.ac.stand.dcs.asa.storage.webdav.interfaces.HTTPMethod;
import uk.ac.stand.dcs.asa.util.Diagnostic;
import uk.ac.stand.dcs.asa.util.Error;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
	
	private static final int INPUT_BUFFER_SIZE = 2048;
	private Socket socket;
	private IFileSystem file_system;
	private ILockManager lock_manager;
	
	public RequestHandler(Socket socket, IFileSystem file_system, ILockManager lock_manager) {
		this.socket = socket;
		this.file_system = file_system;
		this.lock_manager = lock_manager;
	}
	
	public void run() {
		
		// Diagnostic.trace( "Thread started " + Thread.currentThread().hashCode(), Diagnostic.INIT );
		
		try {
			
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			
			BufferedInputStream bufferedIn = new BufferedInputStream(in, INPUT_BUFFER_SIZE);
			
			Request request = new Request(bufferedIn, out);
			request.setSocket(socket);
			
			try {
				
				// Partly functioning support for keep-alive of the socket connection removed on 23/8/05 from:
				//
				// uk.ac.stand.dcs.asa.storage.webdav.impl.RequestHandler
				// uk.ac.stand.dcs.asa.storage.webdav.impl.Response
				
				request.processRequestLine();
				Diagnostic.trace("********** Request: " + request.getVerb() + " " + request.getUri() + " [thread " + Thread.currentThread().hashCode() + "]", Diagnostic.RUN);
				
				// Decide what action to take.
				HTTPMethod method = HTTP.HTTP_METHOD(request.getVerb());
				
				method.init(file_system, lock_manager);
				request.processHeaders();
				Response response = request.getResponse();
				method.execute(request, response);
				
				Diagnostic.trace("************* Completed Request: " + request.getVerb() + " " + request.getUri() + " [thread " + Thread.currentThread().hashCode() + "]", Diagnostic.RUN);
			}
			catch (IOException e) {
				
				// Comms error.
				// No point in setting the error code in the response if we can't send it...
				
				try {
					Error.exceptionError("While processing request from " + socket.getInetAddress().getHostName(), e);
					
					socket.close();
					socket = null;
				}
				catch (IOException ioe) { Error.exceptionError("IOException while closing socket", ioe); }
			}
			catch (HTTPException e) {
				
				// HTTP related error thrown by HTTP method, so translate to appropriate response code.
				
				Response response = request.getResponse();
				response.writeError(e.getStatusCode(), e.getMessage());
				
				//Error.exceptionError("HTTP error code: " + e.getStatusCode(), e);
				
				Diagnostic.trace("************* Completed Request: " + request.getVerb() + " " + request.getUri() + " [thread " + Thread.currentThread().hashCode() + "]", Diagnostic.RUN);
			}
			catch (RuntimeException e) {
				
				// Absorb any unchecked exceptions.
				
				Error.exceptionError("While processing request", e);
				
				Response response = request.getResponse();
				response.writeError(HTTP.RESPONSE_INTERNAL_SERVER_ERROR, e.getMessage());
			}
		}
		catch (IOException e) { Error.exceptionError("While setting up client socket streams", e); }
		
		finally {
			
			//Diagnostic.trace( "Thread completed " + Thread.currentThread().hashCode(), Diagnostic.RUN );
			
			try {
				if (socket != null && socket.isConnected()) socket.close();
			}
			catch (IOException e) { Error.exceptionError("While trying to close socket", e); }
		}
	}
}
