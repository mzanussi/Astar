package com.michaelzanussi.astar;

/**
 * The <tt>AbstractToken</tt> class provides a minimal implementation of the 
 * <tt>Token</tt> interface.
 *
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractToken implements Token {

	/**
	 * The token.
	 */
	protected String token;
	
	/**
	 * The token type.
	 */
	protected int type;
	
	/**
	 * Standard constructor.
	 * 
	 * @param token the contents of the token.
	 */
	public AbstractToken(String token, int type) {
		
		// Do not allow null tokens.
		if (token == null) {
			throw new NullPointerException("AbstractToken.AbstractToken error: Null tokens are not allowed.");
		}
		
		this.token = token;
		this.type = type;
		
	}
	
	/**
	 * Returns the token.
	 * 
	 * @return the token.
	 */
	public String getToken() { 
		
		return token; 
	
	}
	
	/**
	 * Returns the type of this token.
	 * 
	 * @return the token type.
	 */
	public int getTokenType() { 
		
		return type; 
	
	}
	
}
