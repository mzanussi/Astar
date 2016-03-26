package com.michaelzanussi.astar;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * The <tt>GridManhattan</tt> heuristic calculates the Manhattan distance
 * from the current node to the goal. This heuristic is monotonic.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class GridManhattan extends AbstractGrid implements Monotonic {

	/**
	 * Standard constructor.
	 * 
	 * @param x the <tt>x</tt> coordinate.
	 * @param y the <tt>y</tt> coordinate.
	 * @param distance the distance from this location to its parent.
	 * @param parent the parent location of this location.
	 */
	public GridManhattan( int x, int y, double distance, PuzState parent ) {
		
		super( x, y, distance, parent );
		
	}

	/**
	 * Return an iterator over the children of the current node.  If no
	 * children are available, the iterator should simply return
	 * <CODE>false</CODE> for <CODE>hasNext()</CODE>.<p>
	 * 
	 * The children for this current node are automatically generated.
	 * Diagonal movement is not allowed.  
	 *
	 * @return Iterator over the children state of this node.
	 */
	public Iterator children() {
		
		_children = new LinkedList();
		
		if ( _x != 0 ) {
			// Add the cell to the west.
			_children.add( _childLocation( _x - 1, _y, 1.0 ) );
		}
		
		if ( _y != 0 ) {
			// Add the cell to the north.
			_children.add( _childLocation( _x, _y - 1, 1.0 ) );
		}
		
		if ( _x != _totalX - 1) {
			// Add the cell to the east.
			_children.add( _childLocation( _x + 1, _y, 1.0 ) );
		}
		
		if ( _y != _totalY - 1 ) {
			// Add the cell to the south.
			_children.add( _childLocation( _x, _y + 1, 1.0 ) );
		}

		return _children.iterator();
		
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
	public boolean equals( Object o ) {
	
		return ( _string.compareTo( ((GridManhattan)o).getLabel() ) == 0 );
		
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
			throw new NullPointerException( "GridManhattan.heuristic error: " +
					"Goal state has not been set." );
		}
		
		// Manhattan distance. 
		double h = getDistance() * ( Math.abs( (double)getX() - ((GridManhattan)_theGoal).getX() ) + Math.abs( (double)getY() - ((GridManhattan)_theGoal).getY() ) );
		
		// *DEBUG*
		if( Global.getDebug() ) {
			System.out.println( "*DEBUG* [" + _string + "]  _g = " +
					_g + ", h = " + h + ", Heuristic() = " + ( _g + h ) );
		}
		
		return _g + h;

	}

	/**
	 * Helper function. Creates a new child to be added to the list
	 * of children for this state. If any obstacles have been defined
	 * for this puzzle, their cost is used instead of the default cost;
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 * @param cost the cost to get to this location.
	 * @return the new child.
	 */
	protected GridManhattan _childLocation( int x, int y, double cost ) {

		// Account for any obstacles.
		double dcost = ( _obstacles[x][y] > 0 ? _obstacles[x][y] : cost );

		// Create the new child.
		GridManhattan child = new GridManhattan( x, y, dcost, this );
		child.setTotalX( _totalX );
		child.setTotalY( _totalY );
		child.setObstacles( _obstacles );
		
		return child;
		
	}
	
}
