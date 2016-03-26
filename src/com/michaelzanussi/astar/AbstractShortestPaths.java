package com.michaelzanussi.astar;

/**
 * The <tt>AbstractShortestPaths</tt> class provides a minimal implementation 
 * of the <tt>ShortestPaths</tt> interface. When subclassing, at a minimum only
 * the <code>equals()</code> and <code>heuristic()</code> methods need to be
 * overridden.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractShortestPaths extends AbstractPuzState implements ShortestPaths {

	/**
	 * The current city name.
	 */
	protected String _city;
	
	/**
	 * The distance from this city to its parent city.
	 */
	protected double _distance;

	/**
	 * Standard constuctor.
	 * 
	 * @param city the name of the city.
	 * @param distance the cost to travel to this city 
	 * from the "parent" city.
	 * @param parent the city we travelled from.
	 */
	public AbstractShortestPaths( String city, double distance, PuzState parent ) {
		
		super();
		_city = city;
		_distance = distance;
		_parent = parent;
		
	}

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
	 * Indicates whether some other object is "equal to" this one. In the 
	 * Shortest Path's case, we compare city names. Needed mainly by the
	 * <code>HashMap</code> in <code>HashingHeap</code>.<p>
	 * 
	 * Defer to subclass for implementation. 
	 *
	 * @param o the state to test the current state against.
	 * @return <code>true</code> if this object is the same as the obj
	 * argument; <code>false</code> otherwise.
	 */
	public abstract boolean equals( Object o );

	/**
	 * Returns the current city name.
	 * 
	 * @return the current city name.
	 */
	public String getLabel() { 
		
		return _city; 
		
	}
	
	/**
	 * Returns the distance for this city.
	 * 
	 * @return the distance.
	 */
	public double getDistance() { 
		
		return _distance; 
		
	}
	
	/**
	 * Returns the hash code value for this object. Needed mainly by the
	 * <code>HashMap</code> in <code>HashingHeap</code>.
	 *
	 * @return the hash code value for this object.
	 */
	public int hashCode() {
		
		return _city.hashCode();
		
	}
	
	/**
	 * Return the heuristic estimate of the value of this node.  This
	 * function should provide the combined "cost-so-far" function <code>g()</code>
	 * with the "estimated cost-to-goal" function, <code>h()</code>.  That is, 
	 * this represents the complete function <code>f(s)=g(s)+h(s)</code>.<p>
	 * 
	 * Defer to subclass for implementation. 
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
			throw new IllegalArgumentException( "AbstractShortestPaths.setDistFromStart " +
					"error: Cost/distance must be >= 0.0. Received: " + d );
		}
		
		_g = d;
		
	}
	
	/**
	 * A string representation of the current state
	 *
	 * @return a string representation of the current state.
	 */
	public String toString() {
		
		return ( "CurrentCity = " + _city );
		
	}
	
}
