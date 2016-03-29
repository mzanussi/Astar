package com.michaelzanussi.astar;

/**
 * The interface for the <code>Grid</code> family of puzzles. <p>
 * 
 * Related to the <code>ShortestPaths</code> puzzle, but set on a grid 
 * instead, it searches for the shortest path between two points. Has
 * the ability to provide obstacles in an effort to make the search
 * engine work harder and to manipulate the path it takes.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public interface Grid {

	/**
	 * Returns the distance for this location.
	 * 
	 * @return the distance.
	 */
	public double getDistance();
	
	/**
	 * Returns the location.
	 * 
	 * @return the location.
	 */
	public String getLabel();

	/**
	 * Returns the x coordinate for this location.
	 * 
	 * @return the x coordinate for this location.
	 */
	public int getX();
	
	/**
	 * Returns the y coordinate for this location.
	 * 
	 * @return the y coordinate for this location.
	 */
	public int getY();
	
	/**
	 * Set the grid obstacles.
	 * 
	 * @param obstacles the obstacles table.
	 */
	public void setObstacles(int[][] obstacles);
	
	/**
	 * Set the total number of rows.
	 * 
	 * @param value the total number of rows.
	 */
	public void setTotalX(int value);
	
	/**
	 * Set the total number of columns.
	 * 
	 * @param value the total number of columns.
	 */
	public void setTotalY(int value);
	
}
