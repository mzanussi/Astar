package com.michaelzanussi.astar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Abstract wrapper class around Java's <code>BufferedWriter</code> class.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractFileWriter implements BasicIO {

	/**
	 * The output buffer.
	 */
	protected BufferedWriter buffer;
		 
	/**
	 * No-arg constructor.
	 */
	public AbstractFileWriter() { 
		
		buffer = null;
		
	}
	
	/**
	 * Opens the specified file for writing.
	 * 
	 * @param file the file to open.
	 * @return <code>true</code> if successful.
	 */
	public boolean open( File file ) {

		try {
			buffer = new BufferedWriter( new FileWriter( file, true ) );
			return true;
		} catch( IOException e ) {
			System.err.println( "ERROR: " + e.getMessage() );
			System.exit( 1 );
		}
		
		return false;
		
	}

	/**
	 * Closes the file writer.
	 * 
	 * @return <code>true</code> if successful.
	 */
	public void close() {
		
		try {
			if( buffer != null ) {
				buffer.close();
			}
		} catch( IOException e ) {
			System.err.println( "ERROR: " + e.getMessage() );
		}
		
	}
	
}
