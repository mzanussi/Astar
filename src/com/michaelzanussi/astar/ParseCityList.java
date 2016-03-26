package com.michaelzanussi.astar;

import java.util.Vector;

/**
 * The <code>ParseCityList</code> class parses the BNF rule <code>CITYLIST</code>.
 * This list provides the names of all the cities for the <code>ShortestPaths</code> 
 * puzzle. Example BNF: <p>
 * 
 * <pre>
 * CITYLIST := "(" ( CITYNAME ( "," CITYNAME )* )? ")"
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseCityList {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return a vector containing the city list.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static Vector parse( Lexer lexer ) throws ParsingException {

		// The city table.	
		Vector table = new Vector();
		
		// Check for an open parenthesis.
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "(" ) ) {
			throw new ParsingException( "ParseCityList.parse error: " +
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
			
			// Get the city and add to list.
			String city = ParseCityName.parse( lexer );
			table.add( city );

			// Check for more cities to add.
			token = lexer.nextToken();
			if( token.getToken().equals( "," ) ) {
				continue;
			}

			// Check for an end parenthesis.
			if( !token.getToken().equals( ")" ) ) {
				throw new ParsingException( "ParseCityList.parse error: " +
						"Expected ')' but received '" + token.getToken() + "'." );
			}
			
			// Return the city list.
			return table;
			
		}
		
	}
	
}
