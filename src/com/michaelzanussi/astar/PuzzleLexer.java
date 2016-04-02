package com.michaelzanussi.astar;

import java.io.IOException;
import java.io.Reader;

/**
 * A table-driven lexer for the <code>PuzzleMuncher</code> application. 
 * It recognizes various token types unique to <code>PuzzleMuncher</code>, 
 * including words, integers, single symbols such as "<tt>=</tt>" and 
 * "<tt>:</tt>", filenames (which might contain "<tt>_</tt>", "<tt>-</tt>", 
 * or the "<tt>.</tt>" symbols), and symbol pairs such as "<tt>-></tt>".<p> 
 * 
 * A simpler lexer for <code>PuzzleMuncher</code> could have just looked at 
 * whole words, integers, and symbols, and then left it up to the parser
 * to put together filenames and symbol pairs. However, this seemed a
 * natural function for this type of lexer and a good exercise in working 
 * with FSM states and actions.<p> 
 * 
 * Input consists of 7-bit ASCII characters, valid input characters being
 * digits, letters or punctuation. Output is any sequence of characters
 * delimited by whitespace or other non-valid punctuation. Valid punctuation 
 * cannot be considered a termination character since tokens may contain 
 * punctuation (<tt>DIR-OR-FILENAME</tt>) and punctuation can occur in 
 * pairs (e.g. "<tt>-></tt>" in <tt>DISTPAIR</tt>).
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public final class PuzzleLexer extends AbstractLexer {

	// Define the available states for parsing a puzzle input file.
	private static final int STATE_NONE = 0;		// not parsing anything
	private static final int STATE_INT = 1;			// parsing an int
	private static final int STATE_WORD = 2;		// parsing a word
	private static final int STATE_SYMBOL = 3;		// parsing a symbol
	private static final int STATE_FILE = 4;		// parsing a filename
	private static final int STATE_PAIR = 5;		// parsing symbol pair (e.g. "->")
	
	// Valid punctuation used within the input file.
	private static final String punctuation = "=/\\.-_:(){}[]\",>";

	// The current token classification.
	private int type = PuzzleToken.TT_UNK;
	
	/**
	 * Standard constructor.
	 * 
	 * @param in the input stream.
	 * @throws NullPointerException If not reader is specified.
	 */
	public PuzzleLexer(Reader in) throws NullPointerException {

		// Call the superclass's constructor.
		super();
		
		// Did the user specify a valid reader?
		if (in == null) {
			throw new NullPointerException("PuzzleLexer.PuzzleLexer error: PuzzleLexer requires a Reader.");
		}
		
		super.in = in;
		state = STATE_NONE;
		
	}

	/**
	 * Returns the next token in the token stream. <p>
	 * 
	 * The building of a token is accomplished by extracting single characters 
	 * from the input stream and building the tokens based on a finite state 
	 * machine (FSM) state table. Whereas a two-dimensional array works wonderful 
	 * for representing the state table, this implementation uses a series of
	 * if-else statements wrapped around a switch statement. Each if-else 
	 * statement corresponds to the current input character being parsed and the
	 * switch statement the current state the machine is in. Once reached, a
	 * corresponding "action" is performed and a new state is selected.<p>
	 * 
	 * The if-else/switch construct is especially useful when debugging in an
	 * IDE such as Eclipse or JBuilder because the debugger steps directly into
	 * the construct. Since the state tables are generally not very large, 
	 * performance doesn't take that much of a hit when compared to an array 
	 * representation, for example. 
	 * 
	 * @return the next token in the token stream.
	 */
	public Token nextToken() {
		
		// If there's a token already on the pushback buffer, 
		// pop and return that token first.
		if (!pushBackBuffer.isEmpty()) {
			return pushBackBuffer.pop();
		}
		
		// We'll never miss an exit point from this "endless" loop, 
		// so it's okay to block here.
		while (true) {
			
			// Read in the next character and test for EOF. If not EOF,
			// convert the integer to a character. If EOF, set availability 
			// and then return the token (as it currently exists).
			
			char ch = 0;
			try {
				int i = in.read();
				if (i < 0) {
					avail = false;
					return new PuzzleToken(token.toString(), type);
				}
				ch = (char)i;
			} catch (IOException e) {
				Global.error("Error reading from data stream: " + e.getMessage());
			}
			
			// Input character is a period or an underscore. These symbols are 
			// valid when they appear within a filename (DIR-OR-FILENAME). They 
			// never appear as stand-alone symbols.
			
			if (ch == '.' || ch == '_') {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_INT:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_DIGIT);
					case STATE_WORD:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_SYMBOL:
						// Save, Return
						return actionSaveReturn(ch, STATE_FILE, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_PAIR:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_PAIR);
				}
			}
			
			// Input character is a forward slash. This symbol is only
			// valid within a path (PATH), and therefore is a filename
			// (DIR-OR-FILENAME). It never appears as a stand-alone
			// symbol.
			
			else if (ch == '/') {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_INT:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_WORD:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_SYMBOL:
						// Save, Return
						return actionSaveReturn(ch, STATE_FILE, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_PAIR:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
				}
			}
			
			// Input character is a dash. The dash is valid when it
			// appears within a filename (DIR-OR-FILENAME) or as the
			// start of the "->" symbol (DISTPAIR).
			
			else if (ch == '-') {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Append
						actionAppend(ch, STATE_PAIR, PuzzleToken.TT_PAIR);
						break;
					case STATE_INT:
						// Save, Return
						return actionSaveReturn(ch, STATE_PAIR, PuzzleToken.TT_PAIR);
					case STATE_WORD:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_SYMBOL:
						// Save, Return
						return actionSaveReturn(ch, STATE_PAIR, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_PAIR:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_PAIR);
				}
			}

			// Input character is a greater-than sign. The greater-than sign 
			// is only valid when it appears at the end of the "->" symbol
			// (DISTPAIR). If never appears as a first character or a
			// stand-alone symbol.
			
			else if (ch == '>') {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Ignore
						break;
					case STATE_INT:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_DIGIT);
					case STATE_WORD:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_ALPHA);
					case STATE_SYMBOL:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_FILE);
					case STATE_PAIR:
						// Append, Return
						return actionAppendReturn(ch, STATE_NONE, PuzzleToken.TT_PAIR);
				}
			}
			
			// Input character is punctuation that appears in the 
			// punctuation list (see _punctuation). This list comprises 
			// the possible symbol list.
			
			else if (punctuation.indexOf(ch) >= 0) {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Append
						actionAppend(ch, STATE_SYMBOL, PuzzleToken.TT_SYMBOL);
						break;
					case STATE_INT:
						// Save, Return
						return actionSaveReturn(ch, STATE_SYMBOL, PuzzleToken.TT_DIGIT);
					case STATE_WORD:
						// Save, Return
						return actionSaveReturn(ch, STATE_SYMBOL, PuzzleToken.TT_ALPHA);
					case STATE_SYMBOL:
						// Save, Return
						return actionSaveReturn(ch, STATE_SYMBOL, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Save, Return
						return actionSaveReturn(ch, STATE_SYMBOL, PuzzleToken.TT_FILE);
					case STATE_PAIR:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_PAIR);
				}
			}
			
			// Input character is an alphabetic character (A-Z, a-z).
			
			else if (Character.isLetter(ch)) {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Append
						actionAppend(ch, STATE_WORD, PuzzleToken.TT_ALPHA);
						break;
					case STATE_INT:
						// Save, Return
						return actionSaveReturn(ch, STATE_WORD, PuzzleToken.TT_DIGIT);
					case STATE_WORD:
						// Append
						actionAppend(ch, STATE_WORD, PuzzleToken.TT_ALPHA);
						break;
					case STATE_SYMBOL:
						// Save, Return
						return actionSaveReturn(ch, STATE_WORD, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_PAIR:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_PAIR);
				}
			}
			
			// Input character is a digit (0-9).
			
			else if (Character.isDigit(ch)) {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Append
						actionAppend(ch, STATE_INT, PuzzleToken.TT_DIGIT);
						break;
					case STATE_INT:
						// Append
						actionAppend(ch, STATE_INT, PuzzleToken.TT_DIGIT);
						break;
					case STATE_WORD:
						// Save, Return
						return actionSaveReturn(ch, STATE_INT, PuzzleToken.TT_ALPHA);
					case STATE_SYMBOL:
						// Save, Return
						return actionSaveReturn(ch, STATE_INT, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Append
						actionAppend(ch, STATE_FILE, PuzzleToken.TT_FILE);
						break;
					case STATE_PAIR:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_PAIR);
				}
			}
			
			// Whatever else that has not been handled will be treated 
			// as a terminating character.
			
			else {
				// Switch on the current state...
				switch (state) {
					case STATE_NONE:
						// Ignore
						break;
					case STATE_INT:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_DIGIT);
					case STATE_WORD:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_ALPHA);
					case STATE_SYMBOL:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_SYMBOL);
					case STATE_FILE:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_FILE);
					case STATE_PAIR:
						// Ignore, Return
						return actionIgnoreReturn(STATE_NONE, PuzzleToken.TT_PAIR);
				}			
			}
		
		}
		
	}
	
	/**
	 * Helper function. Appends the current character to the token, then
	 * continues parsing at the next state.
	 * 
	 * @param ch the current character.
	 * @param state the next state.
	 * @param type the current token type.
	 * @return the current token.
	 * @throws IllegalArgumentException If state < 0.
	 */
	private void actionAppend(char ch, int state, int type) throws IllegalArgumentException {
		
		// Is this a valid state?
		if (state < 0) {
			throw new IllegalArgumentException("PuzzleLexer._actionAppend error: Invalid state specified for the Append action: " + state);
		}
		
		// Append current character to token.
		token.append(ch);
		// Set the next state.
		super.state = state;
		// Set the current token type being processed.
		this.type = type;
		
	}
	
	/**
	 * Helper function. Appends the current character to the token, then
	 * returns the current token.
	 * 
	 * @param ch the current character.
	 * @param state the next state.
	 * @param type the current token type.
	 * @return the current token.
	 * @throws IllegalArgumentException If state < 0.
	 */
	private Token actionAppendReturn(char ch, int state, int type) throws IllegalArgumentException {
		
		// Is this a valid state?
		if (state < 0) {
			throw new IllegalArgumentException("PuzzleLexer._actionAppendReturn error: Invalid state specified for the AppendReturn action: " + state);
		}
		
		// Append current character to token.
		token.append(ch);
		
		return actionIgnoreReturn(state, type);
		
	}
	
	/**
	 * Helper function. Ignores (drops) the current character, then
	 * returns the current token.
	 * 
	 * @param state the next state.
	 * @param type the current token type.
	 * @return the current token.
	 * @throws IllegalArgumentException If state < 0.
	 */
	private Token actionIgnoreReturn(int state, int type) throws IllegalArgumentException {

		// Is this a valid state?
		if (state < 0) {
			throw new IllegalArgumentException("PuzzleLexer._actionIgnoreReturn error: Invalid state specified for the IgnoreReturn action: " + state);
		}
		
		// Save the token.
		Token ntoken = new PuzzleToken(token.toString(), type);
		// Empty the contents of the current token.
		token.delete(0, token.length());
		// Set the next state.
		super.state = state;
		
		return ntoken;
		
	}

	/**
	 * Helper function. Appends the current character to a new token, then
	 * returns the previous token.
	 * 
	 * @param ch the current character.
	 * @param state the next state.
	 * @param type the current token type.
	 * @return the current token.
	 * @throws IllegalArgumentException If state < 0.
	 */
	private Token actionSaveReturn(char ch, int state, int type) throws IllegalArgumentException {

		// Is this a valid state?
		if (state < 0) {
			throw new IllegalArgumentException("PuzzleLexer._actionSaveReturn error: Invalid state specified for the SaveReturn action: " + state);
		}
		
		// Save the token.
		Token ntoken = new PuzzleToken(token.toString(), type);
		// Empty the contents of the current token.
		token.delete(0, token.length());
		// Append the current character to token.
		token.append(ch);
		// Set the next state.
		super.state = state;
		
		return ntoken;
		
	}
	
}
