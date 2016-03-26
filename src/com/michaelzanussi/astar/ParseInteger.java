package com.michaelzanussi.astar;

/**
 * The <code>ParseInteger</code> class parses the BNF rules <code>POS-INTEGER</code>
 * and <code>NON-NEG-INTEGER</code>, returning either a positive integer (i.e., an 
 * integer <code>&gt; 0</code>) or a non-negative integer (i.e., an integer 
 * <code>&gt;= 0</code>). Example BNF: <p>
 * 
 * <pre>
 * NON-NEG-INTEGER := [0-9]+
 * POS-INTEGER := [1-9][0-9]+
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseInteger {

	/**
	 * Parses the token stream for a single non-negative integer and returns it.
	 * 
	 * @param lexer the lexer.
	 * @return a non-negative integer.
	 * @throws ParsingException If integer is not <code>&gt;= 0</code>.
	 */
	public static int parseNonNegInteger( Lexer lexer ) throws ParsingException {
		
		// Parse the integer.
		int i = _parseInteger( lexer.nextToken() );
		
		// Check to see if this is a positive integer.
		if( i < 0 ) {
			throw new ParsingException( "ParseInteger.parseNonNegInteger error: " +
					"Non-negative integer expected but received: " + i );
		}
		
		return i;
		
	}
	
	/**
	 * Parses the token stream for a single positive integer and returns it.
	 * 
	 * @param lexer the lexer.
	 * @return a positive integer.
	 * @throws ParsingException If integer is not <code>&gt; 0</code>.
	 */
	public static int parsePosInteger( Lexer lexer ) throws ParsingException {
		
		// Parse the integer.
		int i = _parseInteger( lexer.nextToken() );
		
		// Check to see if this is a positive integer.
		if( i <= 0 ) {
			throw new ParsingException( "ParseInteger.parsePosInteger error: " + 
					"Positive integer expected but received: " + i );
		}
		
		return i;
		
	}
	
	/**
	 * Helper function. Parses the token for an integer.
	 * 
	 * @param token the token to parse.
	 * @return an integer.
	 * @throws ParsingException If the token is not an integer.
	 */
	private static int _parseInteger( Token token ) throws ParsingException {
		
		int i = 0;
		
		try {
			i = Integer.parseInt( token.getToken() );
		}
		catch( NumberFormatException e ) {
			throw new ParsingException( "ParseInteger._parseInteger error: Integer expected " + 
					"but received: " + token.getToken() );
		}
		
		return i;
		
	}
	
}
