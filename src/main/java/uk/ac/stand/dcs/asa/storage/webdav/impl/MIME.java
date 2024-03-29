package uk.ac.stand.dcs.asa.storage.webdav.impl;

import uk.ac.stand.dcs.asa.util.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben Catherall
 * @version 21-Apr-2005
 */
public class MIME {
	
    private static final Map extMap = new HashMap();	// <String,String>

    static {

        try {
            InputStream in = MIME.class.getResourceAsStream("MIME.properties");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                String[] split = line.split("\\s+");
                if (split.length > 1){
                    String contentType = split[0].toLowerCase();
                    for (int i=1;i<split.length;i++){
                        String ext = split[i].toLowerCase();
                        extMap.put(ext,contentType);
                        //Diagnostic.trace(ext + " -> "+ contentType, Diagnostic.FULL );
                    }
                }
            }
        } catch (IOException e){
            Error.exceptionError("While parsing MIME types",e);
        }
    }

    public static String getContentTypeFromFileName(String fileName){
        int i = fileName.lastIndexOf('.');
        if (i > 0){
            String ext = fileName.substring(i+1);
            return getContentType(ext);
        } else {
            return null;
        }
    }

    public static String getContentType(String ext){
        return (String) extMap.get(ext);
    }
}
