package com.michaelzanussi.astar;

import java.util.Iterator;

/**
 * The <tt>AbstractMandC</tt> class provides a minimal implementation 
 * of the <tt>MandC</tt> interface. When subclassing, at a minimum only
 * the <code>children()</code>, <code>equals()</code>, <code>heuristic()</code> 
 * and <code>toString()</code> methods need to be overridden.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractMandC extends AbstractPuzState implements MandC {

	/**
	 * The cannibal count.
	 */
	protected int _c;
	
	/**
	 * The missionary count.
	 */
	protected int _m;
	
	/**
	 * The current bank the boat is located at.
	 */
	protected int _bank;
	
	/**
	 * The boat capacity.
	 */
	protected int _capacity;
	
	/**
	 * A string representation of this state's cannibals,
	 * missionaries, and bank. For example: 3,3,1
	 */
	protected String _string;
	
	/**
	 * The puzzle's total number of cannibals.
	 */
	protected int _totalC;

	/**
	 * The puzzle's total number of cannibals.
	 */
	protected int _totalM;
	
	/**
	 * Standard constuctor.
	 * 
	 * @param c the cannibals.
	 * @param m the missionaries.
	 * @param bank the boat location.
	 * @param parent the previous state.
	 * @throws IllegalArgumentException If any of the constructor arguments
	 * are invalid.
	 */
	public AbstractMandC( int c, int m, int bank, PuzState parent ) throws IllegalArgumentException {
		
		super();
		
		if( m < 0 || c < 0 || bank < 0 || bank > 1 ) {
			throw new IllegalArgumentException( "AbstractMandC.AbstractMandC error: " +
					"Illegal constructor arguments: c = " + c + ", m = " + m + ", bank = " +
					bank + ", parent = " + parent );
		}
		
		_c = c;
		_m = m;
		_bank = bank;
		_parent = parent;
		
		_totalC = 0;
		_totalM = 0;
		
		// This state's label (e.g. 3,3,1).
		_string = Integer.toString( _c ) + "," + 
		Integer.toString( _m ) + "," + 
		Integer.toString( _bank );
		
	}

	/**
	 * Return an iterator over the children of the current node.  If no
	 * children are available, the iterator should simply return
	 * <CODE>false</CODE> for <CODE>hasNext()</CODE>.<p>
	 * 
	 * The children for this current node are automatically generated
	 * based on boat capacity and current node tuple settings.<p>  
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
		
		return ( _parent == null ? 0.0 : _parent.distFromStart() + 1.0 );
		
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one. In the 
	 * Missionaries and Cannibals case, we compare the tuple. Needed mainly by the
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
	 * Returns the boat location.
	 * 
	 * @return the boat location.
	 */
	public int getBank() { 
		
		return _bank; 
		
	}
	
	/**
	 * Returns the number of cannibals.
	 * 
	 * @return the number of cannibals.
	 */
	public int getC() { 
		
		return _c; 
		
	}
	
	/**
	 * Returns the distance for this state.
	 * 
	 * @return the distance.
	 */
	public double getDistance() { 
		
		return 1.0; 
		
	}
	
	/**
	 * Returns the cannibals, missionaries and boat location.
	 * 
	 * @return the cannibals, missionaries and boat location.
	 */
	public String getLabel() { 
		
		return _string; 
		
	}
	
	/**
	 * Returns the number of missionaries.
	 * 
	 * @return the number of missionaries.
	 */
	public int getM() { 
		
		return _m; 
		
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
	 * Set the boat capacity.
	 * 
	 * @param value the boat capacity.
	 * @throws IllegalArgumentException If boat capacity <= 0.
	 */
	public void setBoatCapacity( int value ) throws IllegalArgumentException {
		
		if( value < 1 ) {
			throw new IllegalArgumentException( "AbstractMandC.setBoatCapacity error: " +
					"Boat capacity must be > 0. Received: " + value );
		}
		
		_capacity = value; 
		
	}
	
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
			throw new IllegalArgumentException( "AbstractMandC.setDistFromStart error: " +
					"Cost/distance must be >= 0.0. Received: " + d );
		}
		
		_g = d;
		
	}
	
	/**
	 * Set the total number of cannibals in the current puzzle.
	 * 
	 * @param value the total number of cannibals.
	 * @throws IllegalArgumentException If <code>value</code> < 0.
	 */
	public void setTotalC( int value ) throws IllegalArgumentException { 
		
		if( value < 0 ) {
			throw new IllegalArgumentException( "AbstractMandC.setTotalC error: " +
					"Cannibals must be >= 0. Received: " + value );
		}
		
		_totalC = value; 
		
	}
	
	/**
	 * Set the total number of missionaries in the current puzzle.
	 * 
	 * @param value the total number of missionaries.
	 * @throws IllegalArgumentException If <code>value</code> < 0.
	 */
	public void setTotalM( int value ) throws IllegalArgumentException { 
		
		if( value < 0 ) {
			throw new IllegalArgumentException( "AbstractMandC.setTotalM error: " +
					"Missionaries must be >= 0. Received: " + value );
		}
		
		_totalM = value; 
		
	}
	
	/**
	 * The string representation of this state, which is the string defined
	 * by the <code>MCSTATE</code> BNF code in the input file. <p>
	 * 
	 * Defer to subclass for implementation. 
	 *
	 * @return the string representation of this state.
	 */
	public abstract String toString();
	
}
