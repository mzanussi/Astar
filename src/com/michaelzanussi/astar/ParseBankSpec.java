package com.michaelzanussi.astar;

/**
 * The <code>ParseBankSpec</code> class parses the BNF rule <code>BANKSPEC</code>,
 * returning an integer pair (array) containing the number of cannibals and
 * missionaries. Example BNF: <p>
 * 
 * <pre>
 * BANKSPEC := NON-NEG-INTEGER "Cannibals" "and" NON-NEG-INTEGER "Missionaries"
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseBankSpec {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return the city name.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static int[] parse( Lexer lexer ) throws ParsingException {
		
		// Get the next token, which should be the number of cannibals.
		int c = ParseInteger.parseNonNegInteger( lexer );

		// Get "Cannibals" token.
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "Cannibals" ) ) {
			throw new ParsingException( "ParseBankSpec.parse error: " +
					"Expected 'Cannibals' but received '" + token.getToken() + "'." );
		}
		
		// Get "and" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "and" ) ) {
			throw new ParsingException( "ParseBankSpec.parse error: " +
					"Expected 'and' but received '" + token.getToken() + "'." );
		}
		
		// Get the next token, which should be the number of missionaries.
		int m = ParseInteger.parseNonNegInteger( lexer );

		// Get "Missionaries" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "Missionaries" ) ) {
			throw new ParsingException( "ParseBankSpec.parse error: " +
					"Expected 'Missionaries' but received '" + token.getToken() + "'." );
		}

		// Store off the int pair.
		int array[] = new int[2];
		array[0] = c;
		array[1] = m;
		
		// Return the city name.
		return array;
		
	}
	
}
