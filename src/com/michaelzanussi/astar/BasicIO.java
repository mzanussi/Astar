package com.michaelzanussi.astar;

import java.io.File;

/**
 * Generic I/O interface for such classes as <code>TextFileReader</code>,
 * <code>TextFileWriter</code>, etc.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public interface BasicIO {

	/**
	 * Open specified file.
	 * 
	 * @param file file to open.
	 * @return <code>true</code> if successful.
	 */
	public boolean open(File file);

	/**
	 * Close file.
	 * 
	 * @return <code>true</code> if successful.
	 */
	public void close();
	
}
