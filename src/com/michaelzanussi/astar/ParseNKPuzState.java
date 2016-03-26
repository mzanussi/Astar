package com.michaelzanussi.astar;

import java.util.Vector;

/**
 * The <code>ParseNKPuzState</code> class parses the BNF rule <code>NKPUZSTATE</code>,
 * returning a Vector containing number lists or other NKPUZSTATES. Recursion was
 * necessary to parse this structure (in fact, it was the only structure that
 * required recursion). Example BNF: <p>
 * 
 * <pre>
 * NKPUZSTATE := "[" ( NUMLIST | NKPUZSTATE ( "," NKPUZSTATE )* ) "]"
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseNKPuzState {

	public static Vector parse( Lexer lexer ) throws ParsingException {
		
		Vector nkPuzState = new Vector();
		
		// Get "[" token.
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "[" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected '[' but received '" + token.getToken() + "'." );
		}

		// Get the next token and...
		token = lexer.nextToken();
		
		// ...is it another opening brace?
		if( token.getToken().equals( "[" ) ) {
			
			// Push the left bracket back onto the stack.
			lexer.pushBack( token );
			// Recursively call the parser.
			Vector temp = parse( lexer );
			// Add results to the vector.
			nkPuzState.add( temp );
			
		}
		
		// ...is it a number list?
		else if( Character.isDigit( token.getToken().charAt( 0 ) ) ) {
			
			// Looks to be an integer. Push back onto stack.
			lexer.pushBack( token );
			
			// Parse number lists.
			while( true ) {
				
				// Extract the number list and add it.
				Vector numList = ParseNumList.parse( lexer );
				nkPuzState.add( numList );
				
				// Get "]" token. Closes number list.
				token = lexer.nextToken();
				if( !token.getToken().equals( "]" ) ) {
					throw new ParsingException( "ParseMCState.parse error: " +
							"Expected ']' but received '" + token.getToken() + "'." );			
				}
				
				// No more lists?
				token = lexer.nextToken();
				if( token.getToken().equals( "]" ) ) {
					// Push the token back onto the stack.
					lexer.pushBack( token );
					return nkPuzState;
				}
				
				// Better be another number list.
				if( !token.getToken().equals( "," ) ) {
					throw new ParsingException( "ParseMCState.parse error: " +
							"Expected ',' but received '" + token.getToken() + "'." );
				}
				
				// Check for the "[".
				token = lexer.nextToken();
				if( !token.getToken().equals( "[" ) ) {
					throw new ParsingException( "ParseMCState.parse error: " +
							"Expected '[' but received '" + token.getToken() + "'." );
				}
			}
			
		}
		
		else {
			// Illegal token.
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected '[', ',' or a number list but received '" + 
					token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "]" ) ) {
			throw new ParsingException( "ParseMCState.parse error: " +
					"Expected ']' but received '" + token.getToken() + "'." );			
		}
		
		return nkPuzState;
		
	}
}
