package uk.ac.stand.dcs.asa.storage.store.impl.localfilebased.test;

import junit.framework.TestCase;
import uk.ac.stand.dcs.asa.interfaces.IGUID;
import uk.ac.stand.dcs.asa.storage.store.factories.LocalFileBasedStoreFactory;
import uk.ac.stand.dcs.asa.storage.store.general.NameGUIDBinding;
import uk.ac.stand.dcs.asa.storage.store.impl.localfilebased.NameGUIDMap;
import uk.ac.stand.dcs.asa.storage.store.interfaces.IGUIDStore;
import uk.ac.stand.dcs.asa.storage.store.interfaces.INameGUIDMap;
import uk.ac.stand.dcs.asa.util.GUIDFactory;

import java.util.Iterator;

/**
 * Test class for Name_GUID_Map:
 * 
 * public class Name_GUID_Map extends Properties implements IPersistentObject,IName_GUID_Map {
 *   public synchronized GUID get(String s);
 *   public synchronized void put(String s, GUID g) throws Exception;
 *   public synchronized void delete(String s) throws Exception;
 *   public synchronized void rename(String oldname, String newname);
 *   public synchronized Iterator iterator();
 *   public IData reify();
 *   public void initialise(IData data, PID pid, GUID guid);
 *   public void persist(IRootedGUIDStore store) throws Exception;
 *   public PID getPid();
 *   public GUID getGUID();
 * }
 * 
 * @author al, graham
 */
public class NameGUIDMapTest extends TestCase {

	public void testPut() {
		
		try {
		
	        IGUIDStore store = new LocalFileBasedStoreFactory().makeStore();
	
	        INameGUIDMap map = new NameGUIDMap(store);
	        
	        IGUID guid1 = GUIDFactory.generateRandomGUID();
	        
	        map.put("name1", guid1);
	        
	        assertEquals(guid1, map.get("name1"));             // Retrieval from the map should result in the original GUID.
	        assertNull(map.get("name2"));                      // Retrieval using a different name should return null.
	        
	        try {
	        	
	        	map.put("name1", guid1);
	        }
	        catch (Exception e) { return; }
	        
	        fail();                                            // A repeated addition to the map with the same name should throw an exception.
		}
		catch (Exception e) { e.printStackTrace(); fail(); }
	}

	public void testDelete() {
		
		try {
		
            IGUIDStore store = new LocalFileBasedStoreFactory().makeStore();
	
	        INameGUIDMap map = new NameGUIDMap(store);
	        
	        IGUID guid1 = GUIDFactory.generateRandomGUID();
	        
	        map.put("name1", guid1);
	        map.delete("name1");
	        
	        assertNull(map.get("name1"));                      // Retrieval using a deleted name should return null.
	        
	        try {
	        	
	        	map.delete("name2");
	        }
	        catch (Exception e) { return; }
	        
	        fail();                                            // Deletion of a non-existent name should throw an exception.
		}
		catch (Exception e) { e.printStackTrace(); fail(); }
	}

	public void testRename() {
		try {
		
            IGUIDStore store = new LocalFileBasedStoreFactory().makeStore();
	
	        INameGUIDMap map = new NameGUIDMap(store);
	        
	        IGUID guid1 = GUIDFactory.generateRandomGUID();
	        
	        map.put("name1", guid1);
	        map.rename("name1", "name2");
	        
	        assertNull(map.get("name1"));                      // Retrieval using a renamed name should return null.
	        assertEquals(guid1, map.get("name2"));             // Retrieval using the new name should result in the original GUID.
		}
		catch (Exception e) { e.printStackTrace(); fail(); }
	}

	public void testIterator() {
		try {
		
            IGUIDStore store = new LocalFileBasedStoreFactory().makeStore();
	
	        INameGUIDMap map = new NameGUIDMap(store);
	        
	        IGUID guid1 = GUIDFactory.generateRandomGUID();

	        map.put("name1", guid1);
	        map.put("name2", guid1);
	        
	        Iterator iterator = map.iterator();

	        assertTrue(iterator.hasNext());                    // The iterator should have a next element.
	        
	        NameGUIDBinding b1 = (NameGUIDBinding) iterator.next();
	        
	        assertEquals(b1.getName(), "name2");               // The next element should contain ("name2", guid1).
	        assertEquals(b1.getGUID(), guid1);
	        
	        assertTrue(iterator.hasNext());                    // The iterator should have a next element.
	        
	        NameGUIDBinding b2 = (NameGUIDBinding) iterator.next();
	        
	        assertEquals(b2.getName(), "name1");               // The next element should contain ("name1", guid1).
	        assertEquals(b2.getGUID(), guid1);
	        
	        assertFalse(iterator.hasNext());                    // The iterator shouldn't have any more elements.
		}
		catch (Exception e) { e.printStackTrace(); fail(); }
	}
}
