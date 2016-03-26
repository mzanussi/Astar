package com.michaelzanussi.astar;

/**
 * The <code>ParseMCState</code> class parses the BNF rule <code>MCSTATE</code>,
 * returning an two integer pairs (array) containing the number of cannibals and
 * missionaries for the start and goal states, respectively. Example BNF: <p>
 * 
 * <pre>
 * MCSTATE := 
 *   {
 *     "WEST" "BANK" ":" BANKSPEC
 *     "EAST" "BANK" ":" BANKSPEC
 *     "BOAT" "is" "on" ( "EAST" | "WEST" ) "BANK"
 *   }
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseMCState {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return the city name.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static int[][] parse( Lexer lexer ) throws ParsingException {
		
		// Get "{" token.
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "{" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected '{' but received '" + token.getToken() + "'." );
		}
		
		// Get "WEST" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "WEST" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'WEST' but received '" + token.getToken() + "'." );
		}
		
		// Get "BANK" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "BANK" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'BANK' but received '" + token.getToken() + "'." );
		}
		
		// Get ":" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( ":" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected ':' but received '" + token.getToken() + "'." );
		}
		
		// Get the BANKSPEC
		int[] westBank = ParseBankSpec.parse( lexer );

		// Get "EAST" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "EAST" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'WEST' but received '" + token.getToken() + "'." );
		}
		
		// Get "BANK" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "BANK" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'BANK' but received '" + token.getToken() + "'." );
		}
		
		// Get ":" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( ":" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected ':' but received '" + token.getToken() + "'." );
		}
		
		// Get the BANKSPEC
		int[] eastBank = ParseBankSpec.parse( lexer );

		// Get "BOAT" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "BOAT" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'BOAT' but received '" + token.getToken() + "'." );
		}

		// Get "is" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "is" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'is' but received '" + token.getToken() + "'." );
		}

		// Get "on" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "on" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'on' but received '" + token.getToken() + "'." );
		}

		// Get "EAST" or "WEST" token.
		int location = -1;
		token = lexer.nextToken();
		if( !token.getToken().equals( "EAST" ) && !token.getToken().equals( "WEST" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'EAST' or 'WEST' but received '" + token.getToken() + "'." );
		}
		else {
			location = ( token.getToken().equals( "WEST" ) ? 1 : 0 );
		}

		// Get "BANK" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "BANK" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected 'BANK' but received '" + token.getToken() + "'." );
		}

		// Get "}" token.
		token = lexer.nextToken();
		if( !token.getToken().equals( "}" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected '}' but received '" + token.getToken() + "'." );
		}
		
		// Store off the int pair.
		int array[][] = new int[3][2];
		array[0][0] = westBank[0];		// cannibals
		array[0][1] = westBank[1];		// missionaries
		array[1][0] = eastBank[0];		// cannibals
		array[1][1] = eastBank[1];		// missionaries
		array[2][0] = location;
		
		// Return the array.
		return array;
		
	}
	
}
