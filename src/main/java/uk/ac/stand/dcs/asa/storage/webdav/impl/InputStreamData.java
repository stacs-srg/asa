/*
 * Created on June 24, 2005 at 10:51:17 AM.
 */
package uk.ac.stand.dcs.asa.storage.webdav.impl;

import uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData;
import uk.ac.stand.dcs.asa.util.Diagnostic;
import uk.ac.stand.dcs.asa.util.Error;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * IData implementation that gets data from an input stream.
 *
 * @author al
 */
public class InputStreamData implements IData {
	
    private byte[] state;
    
    /**
     * Creates an instance using a given stream.
     * 
     * @param inputStream a stream containing the underlying data
     */
    public InputStreamData(InputStream inputStream, int expected_byte_count) {
    	
        state = new byte[expected_byte_count];
        int bytes_read = 0;
        
        try {
            do {
                int available = inputStream.available();

                int read = inputStream.read(state,bytes_read,available);
                bytes_read += read;
            }
            while (bytes_read != expected_byte_count); // TODO Al - need to protect against erroneous clients
        }
        catch (IOException e) { Error.exceptionError( "I/O error during stream read",e ); }
        
        Diagnostic.trace( "Total read in: " + bytes_read + " bytes", Diagnostic.RUN );
    }
    
    /**
     * Gets the data.
     * 
     * @return the underlying data
     * @see uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData#getState()
     */
    public byte[] getState() {
        return state;
    }
    
    /**
     * Gets the size of the data in bytes.
     * 
     * @return the size of the data
     * @see uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData#getSize()
     */
    public long getSize() {
        return state.length;
    }

    /**
     * Creates an input stream reading from the byte array.
     * 
     * @return an input stream reading from the byte array
     * @see uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData#getInputStream()
     */
    public InputStream getInputStream() {
        return new ByteArrayInputStream(state);
    }

    /**
     * Tests equality with another instance.
     * 
     * @return true if the array's contents are equivalent to those of the given array
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object o ) {
        return o instanceof IData && Arrays.equals( getState(), ((IData)(o)).getState() );
    }
    
    public String toString() {
    	return new String(state);
    }
}
