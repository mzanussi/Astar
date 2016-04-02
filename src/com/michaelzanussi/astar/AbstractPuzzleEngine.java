package com.michaelzanussi.astar;

import java.util.LinkedList;

/**
 * This class provides a skeletal implementation of the <tt>PuzzleEngine</tt>
 * interface, providing access to the start and goal states and a heuristic's
 * monotonicity.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractPuzzleEngine implements PuzzleEngine {

	/**
	 * The starting puzzle state.
	 */
	protected PuzState start;
	
	/**
	 * The ending puzzle state.
	 */
	protected PuzState goal;

	/**
	 * Is the heuristic monotonic?
	 */
	protected boolean monotonic;
		
	/**
	 * Standard constructor.
	 * 
	 * @param start the starting puzzle state.
	 * @param goal the ending puzzle state.
	 */
	public AbstractPuzzleEngine(PuzState start, PuzState goal) {
		
		this.start = start;
		this.goal = goal;

		// The heuristic is monotonic if it implements the marker
		// interface Monotonic.
		monotonic = (this.start instanceof Monotonic ? true : false);
		
	}
	
	/**
	 * Searches for the optimal path between two puzzle states. If a path is
	 * found, the resultant goal <code>PuzState</code> is returned so that
	 * the path can be acted upon, such as printing the path to the standard
	 * output. If no path could be found, <code>path()</code> returns
	 * <code>null</code>. <p>
	 * 
	 * Implementation deferred to the subclass so that various search 
	 * algorithms other than A* (for example) can be implemented easier.
	 * 
	 * @return the goal puzzle state if a path is found, or <code>null</code> 
	 * if no path exists.
	 */
	public abstract LinkedList<Object> path();

}
