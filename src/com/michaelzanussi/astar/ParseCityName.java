package com.michaelzanussi.astar;

/**
 * The <code>ParseCityName</code> class parses the BNF rule <code>CITYNAME</code>,
 * returning a string specifying the city name. The first character of the city name 
 * must be capitalized. Example BNF: <p>
 * 
 * <pre>
 * CITYNAME := [A-Z][a-zA-Z]+
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseCityName {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return the city name.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static String parse(Lexer lexer) throws ParsingException {
		
		// Get the next token, which should be a city name.
		Token token = lexer.nextToken();

		// Check to see if the city name is alphabetic.
		if (token.getTokenType() != PuzzleToken.TT_ALPHA) {
			throw new ParsingException("ParseCityName.parse: Invalid city name - " + token.getToken() + ". Token type was: " + token.getTokenType() + ". City name must be alphabetic only.");
		}

		// Verify the first letter is capitalized.
		char ch = token.getToken().charAt(0);
		if (!(ch >= 'A' && ch <= 'Z')) {
			throw new ParsingException("ParseCityName.parse: Invalid city name - " + token.getToken() + ". First letter must be uppercase."); 
		}
		
		// Return the city name.
		return token.getToken();
		
	}
	
}
