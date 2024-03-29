/*
 * Created on May 21, 2005 at 1:09:52 PM.
 */
package uk.ac.stand.dcs.asa.storage.persistence.impl;

import uk.ac.stand.dcs.asa.interfaces.IGUID;
import uk.ac.stand.dcs.asa.interfaces.IPID;
import uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData;
import uk.ac.stand.dcs.asa.storage.persistence.interfaces.IStatefulObject;
import uk.ac.stand.dcs.asa.util.Error;

/**
 * Simple persistent object implementation.
 * 
 * @author al
 */
public abstract class StatefulObject extends PersistentBase implements IStatefulObject {
    
    protected IData state;
   
    /**
     * Creates a new instance without state.
     */
    public StatefulObject() {
        super();
        state = null;
    }
    
    public StatefulObject(IGUID guid) {
        super(guid);
        state = null;
    }
    
    /**
     * Creates a new instance with given state.
     * 
     * @param data the new state
     */
    public StatefulObject(IData data) {
    	super();
        state = data;
    }
    
    /**
     * Reinstantiates a previously stored object.
     * 
     * @param data the object's state
     * @param pid the object's PID
     * @param guid the object's GUID
     */
    public StatefulObject(IData data, IPID pid, IGUID guid) {
        super(guid, pid);
        state = data;
    }
    
    /**
     * Gets a representation of the object.
     * 
     * @return the object's state
     */
    public IData reify() {
        if (state == null) Error.error("Attempt to reify stateless object");
        return state;
    }

    /**
     * Initialises the object.
     * Note this is very dangerous and should only be used for re-instating reified state - al.
     * 
     * @param data the new state
     * @param pid the new PID
     * @param guid the new GUID
     * 
     * @see uk.ac.stand.dcs.asa.storage.persistence.interfaces.IPersistentObject#initialise(uk.ac.stand.dcs.asa.storage.persistence.interfaces.IData, uk.ac.stand.dcs.asa.interfaces.IPID, uk.ac.stand.dcs.asa.interfaces.IGUID)
     */
    public void initialise(IData data, IPID pid, IGUID guid) {
        this.state = data;
        this.pid = pid;
        this.guid = guid;
    }
    
    /**
     * Updates the object's transient state.
     * 
     * @param data the new state
     */
    public void update(IData data) {
    	this.state = data;
    }
}
