package com.michaelzanussi.astar;

/**
 * Interface for a standard token extracted by a lexer.
 *  
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public interface Token {

	/**
	 * Unknown token type.
	 */
	public static final int TT_UNK = 0;
	
	/**
	 * Alphabetic token type.
	 */
	public static final int TT_ALPHA = 1;
	
	/**
	 * Digits token type.
	 */
	public static final int TT_DIGIT = 2;

	/**
	 * Returns the string representation of the token.
	 * 
	 * @return the token.
	 */
	public String getToken();
	
	/**
	 * Returns the type of this token.
	 * 
	 * @return the token type.
	 */
	public int getTokenType();
	
}
