package com.michaelzanussi.astar;

/**
 * The interface for the Missionaries and Cannibals family of puzzles. <p>
 * 
 * In the Missionaries and Cannibals puzzle, there are missionaries and cannibals on 
 * one bank of a river, together with a boat that can carry passengers. Initially, 
 * missionaries <tt>&lt;=</tt> cannibals. The object is to reach a state in which all 
 * missionaries and cannibals are on the other bank of the river. The difficulty is 
 * that if the missionaries ever outnumber the cannibals on either side of the river, 
 * that group of missionaries eats their unfortunate cannibal associates. To 
 * successfully solve the puzzle, no cannibals may be devoured.<p> 
 * 
 * A move consists of selecting a group of one or more passengers to load in the boat, 
 * rowing the boat across the river, and unloading the boat on the other side. The boat 
 * cannot cross the river without any passengers and the boat cannot carry more 
 * passengers than its capacity. The boat must be fully unloaded on the far side 
 * of the river, at which point the assessment of conversions is performed.<p>
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public interface MandC {

	/**
	 * Constant representation of the EAST bank.
	 */
	public static final int EAST = 0;
	
	/**
	 * Constant representation of the WEST bank.
	 */
	public static final int WEST = 1;
	
	/**
	 * Returns the boat location.
	 * 
	 * @return the boat location.
	 */
	public int getBank();
	
	/**
	 * Returns the number of cannibals.
	 * 
	 * @return the number of cannibals.
	 */
	public int getC();
	
	/**
	 * Returns the cannibals, missionaries and boat location.
	 * 
	 * @return the cannibals, missionaries and boat location.
	 */
	public String getLabel();
	
	/**
	 * Returns the number of missionaries.
	 * 
	 * @return the number of missionaries.
	 */
	public int getM();
	
	/**
	 * Set the boat capacity.
	 * 
	 * @param value the boat capacity.
	 */
	public void setBoatCapacity( int value );
	
	/**
	 * Set the total number of cannibals in the current puzzle.
	 * 
	 * @param value the total number of cannibals.
	 */
	public void setTotalC( int value );
	
	/**
	 * Set the total number of missionaries in the current puzzle.
	 * 
	 * @param value the total number of missionaries.
	 */
	public void setTotalM( int value );
	
}
