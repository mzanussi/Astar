package com.michaelzanussi.astar;

/**
 * The <code>ParseFile</code> class parses the BNF rule <code>OUTFILE</code>,
 * <code>LOGFILE</code>, and <code>ERRFILE</code>. These rules rely on 
 * filenames, which require special parsing. Example BNF: <p>
 * 
 * <pre>
 * OUTFILE := "OutFile" "=" FILESPEC
 * LOGFILE := "LogFile" "=" FILESPEC
 * ERRFILE := "ErrFile" "=" FILESPEC
 * </pre>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ParseFile {

	/**
	 * Parse the input file.
	 * 
	 * @param lexer the lexer.
	 * @return the fully-qualified filename.
	 * @throws ParsingException If a problem is encountered while parsing.
	 */
	public static String parse(Lexer lexer) throws ParsingException {

		// Get the option
		Token token = lexer.nextToken();
		String option = token.getToken();
		
		// Verify this is a valid SEARCH-CTRL option.
		if (!option.equals("OutFile") && !option.equals("LogFile") && !option.equals("ErrFile")) {
			throw new ParsingException("ParseFile.parse error: Invalid OUTFILE, LOGFILE, or ERRFILE option: " + token.getToken());
		}
		
		// Next token should be the "=" symbol.
		token = lexer.nextToken();
		if (!token.getToken().equals("=")) {
			throw new ParsingException("ParseFile.parse error: Expected '=' but received '" + token.getToken() + "'.");
		}
		
		// Next token should be the "\"" symbol.
		token = lexer.nextToken();
		if (!token.getToken().equals("\"")) {
			throw new ParsingException("ParseFile.parse error: Expected '\"' but received '" + token.getToken() + "'.");
		}
		
		// Get the value, verify it, then store it off.
		token = lexer.nextToken();
		if (token.getTokenType() != PuzzleToken.TT_FILE && token.getTokenType() != PuzzleToken.TT_ALPHA) {
			throw new ParsingException("ParseFile.parse: Invalid file name - " + token.getToken() + ". Token type was: " + token.getTokenType() + ".");
		}
		String strToken = token.getToken();
		
		// Next token should be the "\"" symbol.
		token = lexer.nextToken();
		if (!token.getToken().equals("\"")) {
			throw new ParsingException("ParseFile.parse error: Expected '\"' but received '" + token.getToken() + "'.");
		}
		
		// Return the filename.
		return strToken;
		
	}

}
