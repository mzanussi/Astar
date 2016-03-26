package com.michaelzanussi.astar;

/**
 * The <code>ShortestPathsMono</code> calculates the straight line
 * distance between two cities.
 *   
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ShortestPathsNonMono extends AbstractShortestPaths {

	/**
	 * Standard constuctor.
	 * 
	 * @param city the name of the city.
	 * @param distance the cost to travel to this city 
	 * from the "parent" city.
	 * @param parent the city we travelled from.
	 */
	public ShortestPathsNonMono( String city, double distance, PuzState parent ) {
		
		super( city, distance, parent );
		
	}

	/**
	 * Indicates whether some other object is "equal to" this one. In the 
	 * Shortest Path's case, we compare city names. Needed mainly by the
	 * <code>HashMap</code> in <code>HashingHeap</code>.
	 * 
	 * @param o the state to test the current state against.
	 * @return <code>true</code> if this object is the same as the obj
     * argument; <code>false</code> otherwise.
	 */
	public boolean equals( Object o ) {
		
		return ( _city.compareTo( ((ShortestPathsNonMono)o).getLabel() ) == 0 );
		
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
	public double heuristic() throws NullPointerException {
		
		if( _theGoal == null ) {
			throw new NullPointerException( "ShortestPathsNonMono.heuristic error: " +
					"Goal state has not been set." );
		}
		
		// Straight-line distance would work great here; however,
		// not sure how to calculate with the input we're given. Instead,
		// we'll max out h(), thus behaving as BFS. Definitely not monotonic.
		double h = Double.MAX_VALUE;
		
		// *DEBUG*
		if( Global.getDebug() ) {
			System.out.println( "*DEBUG* [" + _city + "]  _g = " +
					_g + ", h = " + h + ", Heuristic() = " + ( _g + h ) );
		}
		
		// Ignore _g altogether.
//		return _g + h;
		return h;
		
	}

}
