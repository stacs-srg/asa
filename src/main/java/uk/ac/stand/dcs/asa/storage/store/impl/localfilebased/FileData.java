/*
 * Created on May 23, 2005 at 10:51:17 AM.
 */
package uk.ac.stand.dcs.asa.storage.store.impl.localfilebased;

import uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData;
import uk.ac.stand.dcs.asa.util.Error;

import java.io.*;
import java.util.Arrays;

/**
 * IData implementation using a conventional file.
 *
 * @author al
 */
public class FileData implements IData {

    private File theFile;
    
    /**
     * Creates an instance using a given file.
     * 
     * @param theFile a file containing the underlying data
     */
    public FileData(File theFile) {
        this.theFile = theFile;
    }
    
    /**
     * Gets the data.
     * 
     * @return the underlying data
     * @see uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData#getState()
     */
    public byte[] getState() {
        byte[] bytes = new byte[(int) getSize()];
        try {
            new FileInputStream(theFile).read(bytes);
        } catch (FileNotFoundException e) {
            Error.exceptionError("Cannot find file: " + theFile.getName(), e);
        } catch (IOException e) {
            Error.exceptionError("IO error: " + theFile.getName(),e);
        }
        return bytes;
    }
    
    /**
     * Gets the size of the data in bytes.
     * 
     * @return the size of the data
     * @see uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData#getSize()
     */
    public long getSize() {
        return theFile.length();
    }

    /**
     * Creates an input stream reading from the file.
     * 
     * @return an input stream reading from the file
     * @see uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData#getInputStream()
     */
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(theFile);
    }

    /**
     * Tests equality with another instance.
     * 
     * @return true if the file's contents are equivalent to those of the given file
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object o ) {
        return o instanceof IData && Arrays.equals( getState(), ((IData)(o)).getState() );
    }
}
