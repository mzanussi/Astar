package com.michaelzanussi.astar;

/**
 * The interface for the <code>ShortestPaths</code> family of puzzles. <p>
 * 
 * In the <code>ShortestPaths</code> puzzles, the system is presented with a 
 * description of a map, giving a list of cities and the distances between 
 * various pairs of cities. The initial state is a starting city and the final 
 * state is a goal city. The object is to find the shortest path between the 
 * two cities. A move consists of traveling from the current city to one of its 
 * adjacent cities.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public interface ShortestPaths {

	/**
	 * Returns the distance for this city.
	 * 
	 * @return the distance.
	 */
	public double getDistance();
	
	/**
	 * Returns the city name.
	 * 
	 * @return the city name.
	 */
	public String getLabel();
	
}
