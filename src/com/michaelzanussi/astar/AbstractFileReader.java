package com.michaelzanussi.astar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Abstract wrapper class around Java's <code>BufferedReader</code> class.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractFileReader extends Reader implements BasicIO {

	/**
	 * The input buffer.
	 */
	protected BufferedReader buffer;
	
	/**
	 * No-arg constructor.
	 */
	public AbstractFileReader() {
		
		buffer = null;
		
	}
	
	/**
	 * Opens the specified file for reading. Supports direct file 
	 * support or an input stream from the standard input.
	 * 
	 * @param file the file to open or <code>null</code> for standard input.
	 * @return <code>true</code> if successful.
	 */
	public boolean open( File file ) {
		
		try {
			if( file == null ) {
				// Standard input. 
				buffer = new BufferedReader( new InputStreamReader( System.in ) );
			}
			else {	
				// Direct file.
				buffer = new BufferedReader( new FileReader( file ) );
			}
			return true;
		} catch( IOException e ) {
			System.err.println( "ERROR: " + e.getMessage() );
			System.exit( 1 );
		}
		
		return false;

	}

	/**
	 * Closes the file reader.
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

	public int read( char cbuf[], int off, int len ) throws IOException {
		// Although this method is abstract in the abstract Reader
		// class, we're not going to implement it here. We'll let
		// derived subclasses deal with it if they want. 
		throw new UnsupportedOperationException( "The 'read' operation is not" +
				"supported at this time." );
	}
	
}
