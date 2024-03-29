/*
 * Created on May 23, 2005 at 1:59:30 PM.
 */
package uk.ac.stand.dcs.asa.storage.persistence.interfaces;

import uk.ac.stand.dcs.asa.interfaces.IGUID;
import uk.ac.stand.dcs.asa.interfaces.IPID;
import uk.ac.stand.dcs.asa.storage.exceptions.PersistenceException;

/**
 * A potentially persistent object.
 *
 * @author al
 */
public interface IPersistentObject extends IGUIDObject {
	
    /**
     * Gets a representation of the object.
     * 
     * @return the object's state
     */
    IData reify();
    
    /**
     * Initialises the object.
     * 
     * @param data the new state
     * @param pid the new PID
     * @param guid the new GUID
     */
    void initialise( IData data, IPID pid, IGUID guid );
    
    /**
     * Records the object's current state.
     * 
     * @throws PersistenceException if the object's state could not be recorded
     */
    void persist() throws PersistenceException;
    
    /**
     * Gets the PID referring to the object's most recently recorded persistent state.
     * 
     * @return the PID for the object's most recent persistent state
     */
    IPID getPID();
}
