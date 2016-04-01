package com.michaelzanussi.astar;

/**
 * The <code>ParseSearchCtrl</code> class parses the BNF rule <code>SEARCH-CTRL</code>,
 * and in particular <code>OPENLIST-BOUND</code>, <code>TOTALNODES-BOUND</code>, and
 * <code>TIME-BOUND</code>. Returns an integer containing the setting. Example BNF: <p>
 * 
 * <pre>
 * SEARCH-CTRL := ( OPENLIST-BOUND | TOTALNODES-BOUND | TIME-BOUND )
 * OPENLIST-BOUND := "OpenListBound" "=" POS-INTEGER
 * TOTALNODES-BOUND := "TotalNodesBound" "=" POS-INTEGER
 * TIME-BOUND := "TimeBound" "=" POS-INTEGER
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseSearchCtrl {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return the option value.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static int parse(Lexer lexer) throws ParsingException {

		// Get the option
		Token token = lexer.nextToken();
		String option = token.getToken();
		
		// Verify this is a valid SEARCH-CTRL option.
		if (!option.equals("OpenListBound") && !option.equals("TotalNodesBound") && !option.equals("TimeBound")) {
			throw new ParsingException("ParseSearchCtrl.parse error: Invalid SEARCH-CTRL option: " + token.getToken());
		}
		
		// Next token should be the "=" symbol.
		token = lexer.nextToken();
		if (!token.getToken().equals("=")) {
			throw new ParsingException("ParseSearchCtrl.parse error: Expected '=' but received '" + token.getToken() + "'.");
		}
		
		// Get the value and verify it (implicit).
		int value;
		try {
			value = ParseInteger.parsePosInteger(lexer);
		} catch (ParsingException e) {
			throw new ParsingException(e.getMessage());
		}
		
		// Return the value.
		return value;
		
	}
	
}
