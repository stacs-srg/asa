/*
 * Created on May 30, 2005 at 1:28:34 PM.
 */
package uk.ac.stand.dcs.asa.storage.absfilesystem.factories;

import uk.ac.stand.dcs.asa.interfaces.IGUID;
import uk.ac.stand.dcs.asa.storage.absfilesystem.exceptions.FileSystemCreationException;
import uk.ac.stand.dcs.asa.storage.absfilesystem.impl.localfilebased.FileBasedFileSystem;
import uk.ac.stand.dcs.asa.storage.absfilesystem.interfaces.IFileSystem;
import uk.ac.stand.dcs.asa.storage.absfilesystem.interfaces.IFileSystemFactory;

import java.io.File;

/**
 * Factory providing methods to create a new file system using a given store.
 *  
 * @author al, graham
 */
public class LocalFileBasedFileSystemFactory implements IFileSystemFactory {
	
	IGUID root_GUID;
	File real_root_directory;
    
    /**
     * Creates a file system factory using the specified store.
     */
   public LocalFileBasedFileSystemFactory(File real_root_directory, IGUID root_GUID) {
	   
	   this.real_root_directory = real_root_directory;
	   this.root_GUID = root_GUID;
     }
   
   public IFileSystem makeFileSystem() throws FileSystemCreationException {
	   
	   return new FileBasedFileSystem(real_root_directory, root_GUID);
   }
}
