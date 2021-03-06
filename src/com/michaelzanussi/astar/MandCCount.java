package com.michaelzanussi.astar;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * The <tt>MandCCount</tt> heuristic calculates the remaining number of
 * cannibals and missionaries that need to be moved from one side of the
 * river to the other. This count also includes the boat location. This
 * heuristic is monotonic.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class MandCCount extends AbstractMandC implements Monotonic {

	/**
	 * Standard constructor.
	 * 
	 * @param c the cannibals.
	 * @param m the missionaries.
	 * @param bank the boat location.
	 * @param parent the previous state.
	 */
	public MandCCount( int c, int m, int bank, PuzState parent ) {
		
		super( c, m, bank, parent );
		
	}

	/**
	 * Return an iterator over the children of the current node.  If no
	 * children are available, the iterator should simply return
	 * <code>false</code> for <code>hasNext()</code>. <p>
	 * 
	 * The children for this current node are automatically generated
	 * based on boat capacity and current node tuple settings.  
	 *
	 * @return Iterator over the children state of this node.
	 */
	public Iterator<Object> children() {
		
		// The child list.
		children = new LinkedList<Object>();
		
		// The possible successor states for a given M & C state is
		// based on the boat capacity. That is, for a two-person
		// capacity boat, there are 5 possible successor states:
		// 		1,0   0,1   2,0   0,2   1,1
		// where the first value is the number of cannibals being
		// moved and the second value is the number of missionaries
		// being moved (0,0 is not included as null moves are not 
		// allowed). Given the possible moves, the danger of the
		// cannibals being killed is then checked. Only those moves
		// where the cannibals are safe are added to the child list.
		for (int i = 0; i <= capacity; i++) {
			
			for (int j = 0; j <= capacity; j++) {
				
				// Discard null moves and moves where the sum of
				// cannibals and missionaries exceeds the boat
				// capacity.				
				if ((i + j) > 0 && (i + j) <= capacity) {
					
					// Initialization.
					MandCCount child = null;
					int m = -1;
					int c = -1;
					
					// Computations differ depending on which bank
					// we're moving from. From west to east 
					// successor states are subtracted from the
					// current state, whereas from east to west
					// successor states are added to the current
					// state.					
					if (bank == WEST) {
						
						// Calculate new cannibal population.
						c = super.c - i;
						
						// Ignore this successor state if the new
						// cannibal population exceeds the max or
						// there are a negative number of cannibals.
						if (c > totalC || c < 0) {
							continue;
						}
						
						// Caclulate the new missionary population.
						m = super.m - j;
						
						// Ignore this successor state if the new
						// missionary population exceeds the max or
						// there are a negative number of missionaries.
						if (m > totalM || m < 0) {
							continue;
						}
						
						// Given the new populations and if the successor
						// state has at least 1 cannibal, check whether
						// the cannibal(s) might get killed. If so, ignore
						// this successor state.
						if (c > 0) {
							if (c < m) {
								continue;
							}
						}
						
						// Also, make sure things are safe on the other bank.
						// If not, ignore this successor state.
						int otherC = totalC - c;
						int otherM = totalM - m;
						if (otherC > 0) { 
							if (otherC < otherM) {
								continue;
							}
						}
						
						// We've found a good successor state! Add it to the
						// children list and set a few variables.
						child = new MandCCount(c, m, EAST, this);
						child.setTotalC(totalC);
						child.setTotalM(totalM);
						child.setBoatCapacity(capacity);
						
					}
					
					// EAST bank calculations
					else { 
						
						// Calculate new cannibal population.
						c = super.c + i;
						
						// Ignore this successor state if the new
						// cannibal population exceeds the max or
						// there are a negative number of cannibals.
						if (c > totalC || c < 0) {
							continue;
						}
						
						// Calculate new missionary population.
						m = super.m + j;
						
						// Ignore this successor state if the new
						// missionary population exceeds the max or
						// there are a negative number of missionaries.
						if (m > totalM || m < 0) {
							continue;
						}
						
						// Given the new populations and if the successor
						// state has at least 1 cannibal, check whether
						// the cannibal(s) might get killed. If so, ignore
						// this successor state.
						if (c > 0) {
							if (c < m) {
								continue;
							}
						}
						
						// Also, make sure things are safe on the other bank.
						// If not, ignore this successor state.
						int otherC = totalC - c;
						int otherM = totalM - m;
						if (otherC > 0) { 
							if (otherC < otherM) {
								continue;
							}
						}
						
						// We've found a good successor state! Add it to the
						// children list and set a few variables.
						child = new MandCCount(c, m, WEST, this);
						child.setTotalC(totalC);
						child.setTotalM(totalM);
						child.setBoatCapacity(capacity);
						
					}
					
					// Add child to child list.
					children.add(child);
					
				}
				
			}
			
		}
		
		// Return an iterator over this list of children.
		return children.iterator();
		
	}

	/**
	 * Indicates whether some other object is "equal to" this one. In the 
	 * Missionaries and Cannibals case, we compare the tuple. Needed mainly by the
	 * <code>HashMap</code> in <code>HashingHeap</code>.
	 * 
	 * @param o the state to test the current state against.
	 * @return <code>true</code> if this object is the same as the obj
     * argument; <code>false</code> otherwise.
	 */
	public boolean equals(Object o) {
		
		boolean mMatch = (m == ((MandCCount)o).getM());
		boolean cMatch = (c == ((MandCCount)o).getC());
		boolean bMatch = (bank == ((MandCCount)o).getBank());
		
		return (mMatch && cMatch && bMatch);
		
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
		
		if (theGoal == null) {
			throw new NullPointerException("MandCCount.heuristic error: The goal state has not been set.");
		}
		
		// Count the missionaries, cannibals, and boat location
		// on this side of the river.
		double h = c + m + bank;

		// *DEBUG*
		if (Global.getDebug()) {
			System.out.println("*DEBUG* [" + string + "]  g = " + g + ", h = " + h + ", Heuristic() = " + (g + h));
		}
		
		return g + h;
		
	}

	/**
	 * The string representation of this state, which is the string defined
	 * by the <code>MCSTATE</code> BNF code in the input file. 
	 *
	 * @return the string representation of this state.
	 */
	public String toString() {
		
		if (parent == null) {
			return ("");
		}
		
		int c = ((MandCCount)parent).getC();
		int m = ((MandCCount)parent).getM();
		int bank = ((MandCCount)parent).getBank();
		
		String initState = "Initial State = { \n" +
				"\tWEST BANK : " + c + " Cannibals and " + m + " Missionaries\n" +
				"\tEAST BANK : " + (totalC - c) + " Cannibals and " + (totalM - m) + " Missionaries\n" +
				"\tBOAT is on " + ( bank == WEST ? "WEST" : "EAST" ) + " BANK\n";
		
		String goalState = "Goal State = { \n" +
		"\tWEST BANK : " + super.c + " Cannibals and " + super.m + " Missionaries\n" +
		"\tEAST BANK : " + (totalC - super.c) + " Cannibals and " + (totalM - super.m) + " Missionaries\n" +
		"\tBOAT is on " + (super.bank == WEST ? "WEST" : "EAST") + " BANK\n";
		
		return (initState + goalState);
		
	}

}
