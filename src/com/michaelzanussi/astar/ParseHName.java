package com.michaelzanussi.astar;

/**
 * The <code>ParseHName</code> class parses the BNF rule <code>HNAME</code>,
 * returning a string specifying the heuristic name. Example BNF: <p>
 * 
 * <pre>
 * HNAME := [a-zA-Z]+
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseHName {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return the name of the heuristic.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static String parse( Lexer lexer ) throws ParsingException {
		
		// Get the next token, which should be a heuristic name.
		Token token = lexer.nextToken();

		// Check to see if this is a valid heuristic name. If the token type
		// is alpha, it's guaranteed to be valid.
		if( token.getTokenType() != PuzzleToken.TT_ALPHA ) {
			
			throw new ParsingException( "ParseHName.parse: Invalid heuristic name - " +
					token.getToken() + ". Token type was: " + 
					token.getTokenType() + ". HNAME must be alphabetic only.");
			
		}
		
		// The name of the heuristic.
		return token.getToken();
		
	}
	
}
