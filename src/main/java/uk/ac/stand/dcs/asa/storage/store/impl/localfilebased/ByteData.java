/*
 * Created on May 23, 2005 at 10:51:17 AM.
 */
package uk.ac.stand.dcs.asa.storage.store.impl.localfilebased;

import uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * IData implementation using a byte array.
 *
 * @author al
 */
public class ByteData implements IData {
	
    private byte[] state;
    
    /**
     * Creates an instance using a given byte array.
     * 
     * @param state a byte array containing the underlying data
     */
    public ByteData(byte[] state) {
        this.state = state;
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
