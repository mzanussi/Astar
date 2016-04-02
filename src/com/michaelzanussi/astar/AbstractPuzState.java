package com.michaelzanussi.astar;

import java.util.Iterator;
import java.util.List;

/**
 * A partial implementation of the <code>PuzState</code> interface, 
 * representing a unique state in a puzzle.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public abstract class AbstractPuzState implements PuzState {

	/**
	 * Distance from the start.
	 */
	protected double g;
	
	/**
	 * The parent state of this state.
	 */
	protected PuzState parent;

	/**
	 * The children (if any) of this state.
	 */
	protected List<Object> children;
	
	/**
	 * Provides access to the goal state. 
	 */
	protected static PuzState theGoal;
	
	/**
	 * No-arg constructor.
	 */
	public AbstractPuzState() {
		
		g = Double.POSITIVE_INFINITY;
		parent = null;
		children = null;

	}
	
	/**
	 * Return an iterator over the children of the current node.  If no
	 * children are available, the iterator should simply return
	 * <CODE>false</CODE> for <CODE>hasNext()</CODE>. This method will be
	 * typically overridden in subclasses.
	 *
	 * @return Iterator over the children state of this node.
	 */
	public Iterator<Object> children() {
		
		return children.iterator();
		
	}

	/**
	 * Return the distance from the start state, or <code>g(s)</code>.  
	 * Required to be non-negative and identically 0 for a start state.  
	 * Will probably be overridden in subclass.
	 * 
	 * @return the recorded cost-from-start function for this node,
	 * along the path that generated it.
	 */
	public double distFromStart() {
		
		return g;
		
	}
	
	/**
	 * Tests whether the current node is a goal state or not. <p>
	 *
	 * To be overridden in inner-class <code>PuzStateWrapper</code>.
	 * 
	 * @return <CODE>true</CODE> if this state is a goal state, else
	 * <CODE>false</CODE>.
	 */
	public boolean goalP() {
		
		return false;
		
	}

	/**
	 * Returns the hash code value for this object. Needed mainly by the
	 * <code>HashMap</code> in <code>HashingHeap</code>.<p>
	 * 
	 * For <code>PuzzleMuncher</code> to operate properly, this method
	 * must be implemented in subclasses of <code>AbstractPuzState</code>.
	 *
	 * @return the hash code value for this object.
	 */
	public abstract int hashCode();
	
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
	 */
	public abstract double heuristic();
	
	/**
	 * Retrieve the parent of the current node.  This should return a
	 * reference to the node that created this node via some action.  If
	 * this node is parentless (e.g., is the START node), this should
	 * return <code>null</code>.
	 *
	 * @return parent pointer for this node, or <code>null</code> for the
	 * root node.
	 */
	public PuzState parent() {
		
		return parent;
		
	}
	
	/**
	 * Set or reset the cost-from-start value for the current node.
	 * This is intended to be used when re-ordering a previously
	 * existing node.
	 *
	 * @param d new value for the cost-from-start function
	 * (<code>g(s)</code>) for this node.
	 */
	public abstract void setDistFromStart(double d);
	
	/**
	 * Sets the goal state static var <code>_theGoal</code>.
	 * 
	 * @param g the goal state.
	 * @throws NullPointerException If the goal state <code>g</code>
	 * is <code>null</code>.
	 */
	public void setGoal(PuzState g) {
		
		if (g == null) {
			throw new NullPointerException("AbstractPuzState.setGoal error: Goal state cannot be null.");
		}
		
		theGoal = g;
		
	}
	
}
