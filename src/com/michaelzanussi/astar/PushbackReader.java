package com.michaelzanussi.astar;

import java.io.File;
import java.io.IOException;

/**
 * Wrapper class around Java's <code>PushbackReader</code> class.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class PushbackReader extends AbstractFileReader {

	// The pushback buffer.
	java.io.PushbackReader pbuffer;
	
	/**
	 * No-arg constructor.
	 */
	public PushbackReader() {
		
		super();
		pbuffer = null;
		
	}
	
	/**
	 * Opens the specified file for reading. Supports direct file 
	 * support or an input stream from the standard input.
	 * 
	 * @param file the file to open or <code>null</code> for standard input.
	 * @return <code>true</code> if successful.
	 */
	public boolean open(File file) {
		
		if (super.open(file)) {
			pbuffer = new java.io.PushbackReader(buffer);
			return (pbuffer == null ? false : true);
		} else {
			// super.open() failed.
			return false;
		}
	}

	/**
	 * Returns a single character as an integer in the 
	 * range 0 - 65535, or -1 if the end of file has been
	 * reached.
	 * 
	 * @return a single character.
	 */
	public int read() {
		
		int c = -1;
		
		try {
			c = pbuffer.read();
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		return c;
		
	}
	
	/**
	 * Push back a single character.
	 * 
	 * @param c the character to push back.
	 */
	public void unread(int c) {
		
		try {
			pbuffer.unread(c);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		
	}
	
}
