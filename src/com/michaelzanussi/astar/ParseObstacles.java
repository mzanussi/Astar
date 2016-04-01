package com.michaelzanussi.astar;

/**
 * The <code>ParseObstacles</code> class parses the BNF rule <code>OBSTACLES</code>.
 * This class creates the table of obstacle locations for the <code>Grid</code>
 * family of puzzle. Example BNF: <p>
 * 
 * <pre>
 * OBSTACLES := "(" ( LOCATIONS ( "," LOCATIONS )* )? ")"
 * LOCATIONS := NON-NEG-INTEGER ":" NON-NEG-INTEGER "=" POS-INTEGER
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseObstacles {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return a vector containing city/distance list.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static int[][] parse(Lexer lexer, int size) throws ParsingException {

		// The obstacles table.	
		int[][] table = new int[size][size];
		
		// Check for an open parenthesis.
		Token token = lexer.nextToken();
		if (!token.getToken().equals("(")) {
			throw new ParsingException("ParseObstacles.parse error: Expected '(' but received '" + token.getToken() + "'.");
		}
		
		// Check for an end parenthesis (empty list) 
		token = lexer.nextToken();
		if (token.getToken().equals(")")) {
			return table;
		} else {
			// Push back the token.
			lexer.pushBack(token);
		}
		
		
		while (true) {
			
			// Get the start city.
			int x = ParseInteger.parseNonNegInteger(lexer);
			
			// Verify coord is in range.
			if (x < 0 || x > size) {				
				throw new ParsingException("ParseObstacles.parse error: X value out of range: " + x);
			}
			
			// Check for ":".
			token = lexer.nextToken();
			if (!token.getToken().equals(":")) {
				throw new ParsingException("ParseObstacles.parse error: Expected ':' but received '" + token.getToken() + "'.");
			}
			
			// Get the end city.
			int y = ParseInteger.parseNonNegInteger(lexer);
			
			// Verify coord is in range.
			if (y < 0 || y > size) {				
				throw new ParsingException("ParseObstacles.parse error: Y value out of range: " + y);
			}
			
			// Check for "=".
			token = lexer.nextToken();
			if (!token.getToken().equals("=")) {
				throw new ParsingException("ParseObstacles.parse error: Expected '=' but received '" + token.getToken() + "'.");
			}
			
			// Get the cost.
			int cost = ParseInteger.parsePosInteger(lexer);

			table[x][y] = cost;
			
			// Check for more pairs.
			token = lexer.nextToken();
			if (token.getToken().equals(",")) {
				continue;
			}
			
			// Check for ")".
			if (!token.getToken().equals(")")) {
				throw new ParsingException( "ParseObstacles.parse error: Expected ')' but received '" + token.getToken() + "'.");
			}
			
			// Return the distance list.
			return table;
			
		}
		
	}
	
}
