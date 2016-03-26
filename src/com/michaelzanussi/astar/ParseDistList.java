package com.michaelzanussi.astar;

import java.util.Vector;

/**
 * The <code>ParseDistList</code> class parses the BNF rule <code>DISTLIST</code>
 * and <code>DISTPAIR</code>. This class creates the table of city/distance pairs 
 * for the <code>ShortestPaths</code> puzzle. Example BNF: <p>
 * 
 * <pre>
 * DISTLIST := "(" ( DISTPAIR ( "," DISTPAIR )* )? ")"
 * DISTPAIR := CITYNAME "->" CITYNAME "=" NON-NEG-INTEGER
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseDistList {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return a vector containing city/distance list.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static Vector parse( Lexer lexer ) throws ParsingException {

		// The distance table.	
		Vector table = new Vector();
		
		// Check for an open parenthesis.
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "(" ) ) {
			throw new ParsingException( "ParseDistList.parse error: " +
					"Expected '(' but received '" + token.getToken() + "'." );
		}
		
		// Check for an end paranethesis (empty list) 
		token = lexer.nextToken();
		if( token.getToken().equals( ")" ) ) {
			return null;
		}
		else {
			// Push back the token.
			lexer.pushBack( token );
		}
		
		while( true ) {
			
			// Get the start city.
			String start = ParseCityName.parse( lexer );
			
			// Check for "arrow".
			token = lexer.nextToken();
			if( !token.getToken().equals( "->") ) {
				throw new ParsingException( "ParseDistList.parse error: " +
						"Expected '->' but received '" + token.getToken() + "'." );
			}
			
			// Get the end city.
			String end = ParseCityName.parse( lexer );
			
			// Check for "=".
			token = lexer.nextToken();
			if( !token.getToken().equals( "=") ) {
				throw new ParsingException( "ParseDistList.parse error: " +
						"Expected '=' but received '" + token.getToken() + "'." );
			}
			
			// Get the distance.
			Integer dist = new Integer( ParseInteger.parseNonNegInteger( lexer ) );

			// Store the city/distance data into an array and add it to the table. 
			String[] triple = new String[3];
			triple[0] = start;
			triple[1] = end;
			triple[2] = dist.toString();
			table.add( triple );
			
			// Check for more city/distance pairs.
			token = lexer.nextToken();
			if( token.getToken().equals( "," ) ) {
				continue;
			}
			
			// Check for ")".
			if( !token.getToken().equals( ")" ) ) {
				throw new ParsingException( "ParseDistList.parse error: " +
						"Expected ')' but received '" + token.getToken() + "'." );
			}
			
			// Return the distance list.
			return table;
			
		}
		
	}
	
}
