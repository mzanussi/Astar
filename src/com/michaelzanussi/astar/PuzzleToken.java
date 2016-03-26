package com.michaelzanussi.astar;

/**
 * Defines a token specific to parsing the input files of the 
 * <tt>PuzzleMuncher</tt> application.
 *
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class PuzzleToken extends AbstractToken {

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
	 * Symbol token type.
	 */
	public static final int TT_SYMBOL = 3;
	
	/**
	 * Symbol pair token type.
	 */
	public static final int TT_PAIR = 4;
	
	/**
	 * Filename token type.
	 */
	public static final int TT_FILE = 5;
	
	/**
	 * Standard constructor, only calls the superclass constructor.
	 * 
	 * @param token the token.
	 * @param type the token type.
	 */
	public PuzzleToken( String token, int type ) {
		
		super( token, type );
		
	}
	
}
