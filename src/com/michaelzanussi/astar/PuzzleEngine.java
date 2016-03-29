package com.michaelzanussi.astar;

import java.util.LinkedList;

/**
 * An interface for a puzzle engine containing a single method,
 * <code>path()</code>.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public interface PuzzleEngine {

	/**
	 * Searches for the optimal path between two puzzle states. If a path is
	 * found, the resultant linked list path of <code>PuzState</code>'s is 
	 * returned so that the path can be acted upon, such as printing the path 
	 * to the standard output. If no path could be found, <code>path()</code> returns
	 * <code>null</code>.
	 * 
	 * @return the path to the goal puzzle state if a path is found, or 
	 * <code>null</code> if no path exists.
	 */
	public LinkedList<Object> path();

}
