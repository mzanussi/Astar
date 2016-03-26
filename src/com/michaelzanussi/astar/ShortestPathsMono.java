package com.michaelzanussi.astar;

/**
 * The <code>ShortestPathsMono</code> calculates no value for h(). Rather, it is
 * set to 0.0 which effectively turns A* into Djikstra's. This heuristic is 
 * guaranteed to be monotonic.
 *  
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class ShortestPathsMono extends AbstractShortestPaths implements Monotonic {

	/**
	 * Standard constuctor.
	 * 
	 * @param city the name of the city.
	 * @param distance the cost to travel to this city 
	 * from the "parent" city.
	 * @param parent the city we travelled from.
	 */
	public ShortestPathsMono( String city, double distance, PuzState parent ) {
		
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
		
		return ( _city.compareTo( ((ShortestPathsMono)o).getLabel() ) == 0 );
		
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
	public double heuristic() {
		
		if( _theGoal == null ) {
			throw new NullPointerException( "ShortestPathsMono.heuristic error: " +
			"Goal state has not been set." );
		}
		
		// It's Djikstra's algorihm now; still guaranteed to find the
		// shortest path. 
		double h = 0.0;
		
		// *DEBUG*
		if( Global.getDebug() ) {
			System.out.println( "*DEBUG* [" + _city + "]  _g = " +
					_g + ", h = " + h + ", Heuristic() = " + ( _g + h ) );
		}
		
		return _g + h;
		
	}

}
