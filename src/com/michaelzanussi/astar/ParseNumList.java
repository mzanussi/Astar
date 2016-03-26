package com.michaelzanussi.astar;

import java.util.Vector;

/**
 * The <code>ParseNumList</code> class parses the BNF rule <code>NUMLIST</code>,
 * returning a vector of one or more integers. Example BNF: <p>
 * 
 * <pre>
 * NUMLIST := NON-NEG-INTEGER ( "," NON-NEG-INTEGER )*
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseNumList {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return a vector containing the intereger list.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static Vector parse( Lexer lexer ) throws ParsingException {
		
		Token token = null;
		Vector numList = new Vector();
		
		while( true ) {
			
			// Get the integer, verify it, then store it off.
			try {
				Integer i = new Integer( ParseInteger.parseNonNegInteger( lexer ) );
				numList.add( i );
			}
			catch( ParsingException e ) {
				throw new ParsingException( e.getMessage() );
			}
			
			// See if the next token is a comma.
			token = lexer.nextToken();
			if( token.getToken().equals( "," ) ) {
				// More integers follow, continue.
				continue;
			}
			else {
				// We're done here, but pushback the last token
				// onto the stack.
				lexer.pushBack( token );
				return numList;
			}
			
		}
		
	}
	
}
