package com.michaelzanussi.astar;

import java.io.IOException;

/**
 * Wrapper class around Java's <code>BufferedWriter</code> class.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class TextFileWriter extends AbstractFileWriter {

	/**
	 * Writes a string to the buffer, without a newline.
	 * 
	 * @param string the string to write to the buffer.
	 */
	public void write(String string) {
		
		try {
			buffer.write(string);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		
	}

	/**
	 * Writes a string to the buffer, followed by a newline.
	 * 
	 * @param string the string to write to the buffer.
	 */
	public void writeln(String string) {
		
		try {
			buffer.write(string);
			buffer.newLine();
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		
	}

	/**
	 * Writes a newline to the buffer.
	 */
	public void writeln() {
		
		try {
			buffer.newLine();
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		
	}

}
