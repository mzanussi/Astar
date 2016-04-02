package com.michaelzanussi.astar;

import java.io.IOException;

/**
 * Wrapper class around Java's <code>BufferedReader</code> class.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class TextFileReader extends AbstractFileReader {

	/**
	 * No-arg constructor.
	 */
	public TextFileReader() {
		
		super();
		
	}
	
	/**
	 * Returns the current line read from the buffer.
	 * 
	 * @return the current line read from the buffer.
	 */
	public String readLine() {
		
		String string = null;
		
		try {
			string = buffer.readLine();
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		return string;
		
	}
	
}
