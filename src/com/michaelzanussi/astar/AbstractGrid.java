package com.michaelzanussi.astar;

import java.util.Iterator;

/**
 * The <tt>AbstractGrid</tt> class provides a minimal implementation 
 * of the <tt>Grid</tt> interface. When subclassing, at a minimum only
 * the <code>children()</code>, <code>equals()</code> and <code>heuristic()</code> 
 * methods need to be overridden.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractGrid extends AbstractPuzState implements Grid {

	/**
	 * String representation of a location on the grid. 
	 * For example: 1,2
	 */
	protected String _string;
	
	/**
	 * X coordinate.
	 */
	protected int _x;

	/**
	 * Y coordinate.
	 */
	protected int _y;
	
	/**
	 * Grid size (X axis).
	 */
	protected int _totalX;
	
	/**
	 * Grid size (Y axis).
	 */
	protected int _totalY;
	
	/**
	 * The distance from this location to its parent location.
	 */
	protected double _distance;
	
	/**
	 * The obstacle table.
	 */
	protected int[][] _obstacles;
	
	/**
	 * Standard constructor.
	 * 
	 * @param x the <tt>x</tt> coordinate.
	 * @param y the <tt>y</tt> coordinate.
	 * @param distance the distance from this location to its parent.
	 * @param parent the parent location of this location.
	 */
	public AbstractGrid( int x, int y, double distance, PuzState parent ) {
		
		super();
		_distance = distance;
		_parent = parent;

		_x = x;
		_y = y;
		
		// This state's label (e.g. 3,3,1).
		_string = Integer.toString( _x ) + "," + Integer.toString( _y );
		
	}

	/**
	 * Return an iterator over the children of the current node.  If no
	 * children are available, the iterator should simply return
	 * <CODE>false</CODE> for <CODE>hasNext()</CODE>.<p>
	 * 
	 * Defer to subclass for implementation.  
	 *
	 * @return Iterator over the children state of this node.
	 */
	public abstract Iterator children();

	/**
	 * Return the distance from the start state, or <code>g(s)</code>.  
	 * Required to be non-negative and identically 0 for a start state.  
	 *   
	 * @return the recorded cost-from-start function for this node,
	 * along the path that generated it.
	 */
	public double distFromStart() {
		
		return ( _parent == null ? 0.0 : _parent.distFromStart() + _distance );
		
	}
	
	/**
	 * Determines whether the passed <code>state</code> and the current state
	 * are one and the same. Checks against <code>x</code> and <code>y</code>
	 * coordinates.
	 * 
	 * @param o the state to test the current state against.
	 * @return <code>true</code> if these states are equal, otherwise
	 * <code>false</code>
	 */
	public abstract boolean equals( Object o );

	/**
	 * Returns the location.
	 * 
	 * @return the location.
	 */
	public String getLabel() { 
		
		return _string; 
		
	}

	/**
	 * Returns the distance for this location.
	 * 
	 * @return the distance.
	 */
	public double getDistance() { 
		
		return _distance; 
		
	}
	
	/**
	 * Returns the x coordinate for this location.
	 * 
	 * @return the x coordinate for this location.
	 */
	public int getX() { 
		
		return _x; 
		
	}
	
	/**
	 * Returns the y coordinate for this location.
	 * 
	 * @return the y coordinate for this location.
	 */
	public int getY() { 
		
		return _y; 
		
	}
	
	/**
	 * Returns the hash code value for this object. Needed mainly by the
	 * <code>HashMap</code> in <code>HashingHeap</code>.
	 *
	 * @return the hash code value for this object.
	 */
	public int hashCode() {
		
		return _string.hashCode();
		
	}
	
	/**
	 * Return the heuristic estimate of the value of this node.  This
	 * function should provide the combined "cost-so-far" function <code>g()</code>
	 * with the "estimated cost-to-goal" function, <code>h()</code>.  That is, 
	 * this represents the complete function <code>f(s)=g(s)+h(s)</code>.
	 * 
	 * @return the total function representing cost-from-start
	 * <em>plus</em> estimated cost-to-goal.
	 * @throws NullPointerException If the goal state <code>_theGoal</code>
	 * is <code>null</code>.
	 */
	public abstract double heuristic();

	/**
	 * Set or reset the cost-from-start value for the current node.
	 * This is intended to be used when re-ordering a previously
	 * existing node.
	 *
	 * @param d new value for the cost-from-start function
	 * (<code>g(s)</code>) for this node.
	 * @throws IllegalArgumentException If <code>d</code> is less than 0.0.
	 */
	public void setDistFromStart( double d ) throws IllegalArgumentException {

		if( d < 0.0 ) {
			throw new IllegalArgumentException( "AbstractGrid.setDistFromStart error: " +
					"Cost/distance must be >= 0.0. Receieved: " + d );
		}
		
		_g = d;
		
	}
	
	/**
	 * Set the grid obstacles.
	 * 
	 * @param obstacles the obstacles table.
	 */
	public void setObstacles( int[][] obstacles ) {
		
		_obstacles = obstacles;
		
	}
	
	/**
	 * Set the total number of rows in the current puzzle.
	 * 
	 * @param value the total number of rows.
	 * @throws IllegalArgumentException If <code>value</code> <= 0.
	 */
	public void setTotalX( int value ) throws IllegalArgumentException { 
		
		if( value <= 0 ) {
			throw new IllegalArgumentException( "AbstractGrid.setTotalX error: " +
					"X must be > 0. Received: " + value );
		}
		
		_totalX = value; 
		
	}
	
	/**
	 * Set the total number of columns in the current puzzle.
	 * 
	 * @param value the total number of columns.
	 * @throws IllegalArgumentException If <code>value</code> < 0.
	 */
	public void setTotalY( int value ) throws IllegalArgumentException { 
		
		if( value < 0 ) {
			throw new IllegalArgumentException( "AbstractMandC.setTotalY error: " +
					"Y must be > 0. Received: " + value );
		}
		
		_totalY = value; 
		
	}

	/**
	 * The string representation of this state
	 * 
	 * @return the string representation of this state.
	 */
	public String toString() {
		
		return "Current state: " + _string + ", heursitic: " + heuristic();
		
	}
		
}
