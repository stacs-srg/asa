package uk.ac.stand.dcs.asa.storage.webdav.impl.methods;

import uk.ac.stand.dcs.asa.storage.webdav.exceptions.HTTPException;
import uk.ac.stand.dcs.asa.storage.webdav.impl.HTTP;
import uk.ac.stand.dcs.asa.storage.webdav.impl.Request;
import uk.ac.stand.dcs.asa.storage.webdav.impl.Response;

import java.io.IOException;

/**
 * @author Ben Catherall, al, graham
 */
public class MOVE extends CopyOrMove {

    public String getMethodName() {
        return HTTP.METHOD_MOVE;
    }

    public void execute(Request request, Response response) throws IOException, HTTPException {
    	
    	// Use the generic code in the superclass.
    	execute(request, response, false);
    }
}
