package com.michaelzanussi.astar;

/**
 * A parse exception indicating problems were encountered during
 * a parsing session.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParsingException extends Exception {

	/**
	 * Standard constructor.
	 * 
	 * @param message the exception message.
	 */
	public ParsingException( String message ) {
		
		super( message );
		
	}
	
}
