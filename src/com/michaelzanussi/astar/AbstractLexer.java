package com.michaelzanussi.astar;

import java.io.Reader;
import java.util.Stack;

/**
 * The <tt>AbstractLexer</tt> class provides a minimal implementation of the 
 * <tt>Lexer</tt> interface. The <code>nextToken()</code> method is deferred 
 * to the subclass for implementation.
 *
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractLexer implements Lexer {

	/**
	 * 	Are there any more tokens available?
	 */
	protected boolean _avail;
	
	/**
	 * 	The input stream.
	 */
	protected Reader _in;
	
	/**
	 * The pushback buffer.
	 */
	protected Stack _pushBackBuffer;

	/**
	 * The current state of the token.
	 */
	protected int _state;
	
	/**
	 * The current token being constructed.
	 */
	protected StringBuffer _token;
	
	/**
	 * No-arg constructor.
	 */
	public AbstractLexer() {
		
		_avail = true;
		_in = null;
		_pushBackBuffer = new Stack();
		_state = -1;
		_token = new StringBuffer();
		
	}
	
	/**
	 * Determines if there are any more tokens available in this lexer.
	 * 
	 * @return <code>true</code> if there are more tokens, otherwise
	 * <code>false</code>.
	 */
	public boolean hasMoreTokens() {
		
		return _avail;
		
	}
	
	/**
	 * Returns the next token in the token stream.
	 * 
	 * Defer to the subclass for implementation as this is subclass
	 * dependant.
	 * 
	 * @return the next token in the token stream.
	 */
	public abstract Token nextToken();
	
	/**
	 * Pushes back a single token onto the token stream.
	 * 
	 * @param t the token to push back.
	 * @throws NullPointerException If a <code>null</code> token is specified.
	 */
	public void pushBack( Token t ) throws NullPointerException {
		
		// Verify token isn't null.
		if( t == null ) {
			throw new NullPointerException( "AbstractLexer.pushBack error: " +
					"Token cannot be null." );
		}
		
		_pushBackBuffer.push( t );
		
	}
	
}
