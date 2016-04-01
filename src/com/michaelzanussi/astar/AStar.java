package com.michaelzanussi.astar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * The A* algorithm for puzzle solving. Supports both admissible and
 * monotonic heuristics. In the case of monotonicity, the 
 * <code>PuzState</code>-derived puzzle class must implement the
 * <code>Monotonic</code> marker class.
 * 
 * A puzzle's start and goal states are fed into <code>AStar</code> at
 * the time the object is created (see the lone constructor). There are
 * no other provisions for setting the start and goal states. 
 * 
 * If a path is found, a linked list representing the path to
 * completion is returned with the most recently found states
 * at the top or beginning of the list (i.e., the goal state
 * begins the list and descends to the start state). If no path
 * is found, <code>null</code> is returned.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class AStar extends AbstractPuzzleEngine {

	// Didn't include these in the abstract class for the puzzle
	// engine because HashingHeap and the open/closed queues are 
	// unique to the A* algorithm and the AbstractPuzzleEngine 
	// class shouldn't lock into a particular data structure.
	private HashingHeap open;
	private Map<Object, Object> closed;
	
	/**
	 * Standard constructor for creation of an A* object.
	 * 
	 * @param start the start puzzle state.
	 * @param goal the ending goal state.
	 */
	public AStar(PuzState start, PuzState goal) {
		
		super(start, goal);
		open = new HashingHeap();
		closed = new HashMap<Object, Object>();
		
	}
	
	/**
	 * Searches for the shortest path between two puzzle states. If a path is
	 * found, the resultant goal <code>PuzState</code> is returned. If no path 
	 * could be found, <code>path()</code> returns <code>null</code>.
	 * 
	 * @return the goal puzzle state if a path is found, or <code>null</code> 
	 * if no path exists.
	 * @throws IllegalStateException If system times out searching for a path.
	 * @throws IndexOutOfBoundsException If bounds have been exceeded.
	 */
	public LinkedList<Object> path() throws IllegalStateException, IndexOutOfBoundsException {

		PuzStateWrapper psw = new PuzStateWrapper(start, 0, null);
		open.insertItem(psw);
		
		while (!open.isEmpty()) {
			
			// Have we timed out?
			if (Global.isTimeUp()) {
				throw new IllegalStateException("AStar.path error: System timed out searching for a solution.");
			}
			
			// Pop off the state with the smallest heuristic.
			PuzStateWrapper parent = (PuzStateWrapper)open.removeMin();

			// Increment global nodes opened.
			Global.incNodesOpened();

			// Are we at the goal?
			if (parent.goalP()) {
				
				// It's the goal. Create a linked list containing the
				// path back to the start.
				LinkedList<Object> path = new LinkedList<Object>();
				PuzStateWrapper s = parent;
				while (s != null) {
					path.add(s.getState());
					s = (PuzStateWrapper)s.parent();
				}
				
				// Set global nodes closed.
				Global.setNodesClosed(closed.size());
				
				// Return the goal path.
				return path;
				
			}
			
			// If applicable, print the current state.
			if (Global.reportStatePath()) {
				Global.output(parent.toString());
			}
			
			// Start looking at this state's children.
			Iterator<Object> it = parent.getState().children();
			while (it.hasNext()) {
				
				// Grab a child from the parent and wrap it up.
				PuzState child = (PuzState)it.next();
				PuzStateWrapper newChild = new PuzStateWrapper(child, child.distFromStart(), parent);
				
				// If applicable, print the newly visited child state.
				if (Global.reportStatePath()) {
					Global.output(newChild.toString());
				}
				
				// Is this child on the closed list?
				if (closed.containsKey(newChild)) {
					
//					System.out.println("*** child is on the CLOSED list ***");
					
					// Check if this child's heuristic is less than what's
					// already on the open list (non-monotonic only).
					if (!monotonic && newChild.heuristic() < ((PuzStateWrapper)(closed.get(newChild))).heuristic()) {
						
						// We've found a better path. Remove the old
						// child from the closed list and add the
						// new one onto the open list for later visitation.
						closed.remove(newChild);
						open.insertItem(newChild);
						
						// Increment global nodes reopened.
						Global.incNodesReopened();
						
					} else {
						// Not a better path. Ignore this child and continue.
						continue;
					}
				}
				// Is this child on the open list?
				// TODO: Ok, hash used to provide quick contains(), get(), and remove() operations.
				// But hash implementation in the PQ is problematic due to possibility of duplicate
				// keys. Need to rethink the data structure.
				else if (open.contains(newChild)) {
//					System.out.println("*** child is on the open list ***");
					// Check if this child's heuristic is less than what's
					// already on the open list (non-monotonic only).
					double newH = newChild.heuristic();
					double openH = (open.get(newChild)).heuristic();
					if (!monotonic && newH < openH) {
						
						// We've found a better path. Remove the old
						// child from the open list and add the new
						// one onto the open list for later visitation.
						open.remove(newChild);
						open.insertItem(newChild);

					} else {
						// Not a better path. Ignore this child and continue. 
						continue;
					}
				}
				
				// It's a state we haven't seen, so put it on the
				// open list for visitation later.
				else {
					open.insertItem(newChild);
				}
				
				// Verify we haven't reached the total nodes bound yet.
				if ((closed.size() + open.size()) > Global.getTotalNodesBound()) {
					throw new IndexOutOfBoundsException( "AStar.path error: TotalNodesBound exceeded. Set to: " + Global.getTotalNodesBound() + ", Current count: " + (closed.size() + open.size()));
				}
				
			}
			
			// We're done processing this state. Put it on the closed
			// list and continue searching.
			Object oldc = closed.put(parent, parent);
			
			// TODO: Do we need to worry about duplicate keys? may need to change this data struct.
			assert(oldc == null);
			//if (old == null) System.out.println("MAPPING EXISTS FOR KEY: " + old);
			
			// Record the list sizes and then calculate the open/closed
			// ratio (performed by setClosedListSize and setOpenListSize).
			Global.setClosedListSize(closed.size());
			Global.setOpenListSize(open.size());
			
			// Verify we haven't reached the total nodes bound yet.
			if ((closed.size() + open.size()) > Global.getTotalNodesBound()) {
				throw new IndexOutOfBoundsException( "AStar.path error: TotalNodesBound exceeded. Set to: " + Global.getTotalNodesBound() + ", Current count: " + (closed.size() + open.size()));
			}
			
		}
		
		// No path found!
		return null;
		
	}

	/**
	 * An inner class for wrapping a <code>PuzState</code>-derived class. This
	 * inner class is key to getting <code>PuzState</code> working properly
	 * since the <code>PuzState</code> interface doesn't allow direct access to
	 * a goal state (as required by method <code>goalP()</code>) or allow
	 * manipulation of a state's parent. Most of these methods call the 
	 * wrapped class's methods directly.
	 * 
	 * @author <a href="mailto:zanussi@cs.unm.edu">Michael Zanussi</a>
	 * @version 1.0 (29 Mar 2004) 
	 */
	private class PuzStateWrapper extends AbstractPuzState {

		// The puzzle state that requires wrapping.
		private PuzState wrappedState;
		
		/**
		 * Standard constructor to build a wrapped PuzState object.
		 * 
		 * @param state the state to wrap up.
		 * @param cost the cost to get to this state.
		 * @param parent the wrapped state's parent, if any.
		 */
		public PuzStateWrapper(PuzState state, double cost, PuzStateWrapper parent) {

			// Set the current state and parent state.
			wrappedState = state;
			super.parent = parent;
			
			// Set the cost-from-start, g().
			setDistFromStart(cost);
			
		}
		
		/**
		 * Indicates whether some other object is "equal to" this one by
		 * comparing hash codes. The <code>hashCode()</code> method MUST 
		 * be overridden in the puzzle subclass otherwise results will vary. 
		 * 
		 * @return <code>true</code> if this object is the same as the obj
		 * argument; <code>false</code> otherwise.
		 */
		public boolean equals(Object o) {
			return (hashCode() == o.hashCode());
		}

		/**
		 * Accessor method that provides direct access to the wrapped state.
		 * 
		 * @return the wrapped state.
		 */
		public PuzState getState() { 
			
			return wrappedState; 
		
		}
		
		/**
		 * Tests whether the wrapped state is a goal state or not.
		 * 
		 * @return <CODE>true</CODE> if the wrapped state is a goal 
		 * state, else <CODE>false</CODE>.
		 */
		public boolean goalP() {
			
			return (wrappedState.equals(goal));
			
		}
		
		/**
		 * Returns the hash code value for this object. Returns the wrapped
		 * state's hash code. Needed mainly by the <code>HashMap</code> in 
		 * <code>HashingHeap</code>.
		 *
		 * @return the hash code value for this object.
		 */
		public int hashCode() {
			
			return wrappedState.hashCode();
			
		}
		
		/**
		 * Return the heuristic estimate of the value of this state, as
		 * calculated by the wrapped state.  This function provides the combined 
		 * "cost-so-far" function <code>g()</code> with the "estimated cost-to-goal" 
		 * function, <code>h()</code>.  That is, this represents the complete 
		 * function <code>f(s)=g(s)+h(s)</code>.
		 * 
		 * @return the total function representing cost-from-start
		 * <em>plus</em> estimated cost-to-goal.
		 */
		public double heuristic() {
			
			return wrappedState.heuristic();
			
		}
		
		/**
		 * Sets the cost-from-start value for this state. If this is
		 * the start state, set to 0.0.
		 *
		 * @param d new value for the cost-from-start function
		 * (<code>g(s)</code>) for this node.
		 */
		public void setDistFromStart(double d) {
			
			if (parent == null) {
				g = 0.0;
				wrappedState.setDistFromStart(0.0);
			} else {
				g = d + parent.distFromStart();
				wrappedState.setDistFromStart(d + parent.distFromStart());
			}
			
		}
		
		/**
		 * Returns a string representation of this state, in this
		 * case the wrapped state's own representation.
		 *
		 * @return the string representation of this state.
		 */
		public String toString() {
			
			return wrappedState.toString();
			
		}
				
	}
	
}
