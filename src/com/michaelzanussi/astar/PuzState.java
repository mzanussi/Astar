package com.michaelzanussi.astar;

import java.util.Iterator;

/**
 * Interface for a generic "state" node for use by a heuristic search
 * routine.  This interface is intended to represent a PUZZLE STATE
 * <em>along with</em> all of its cost and heuristic information.  The
 * model is that to implement different heuristics, it's simply
 * necessary to override {@link #heuristic()}.  In one architecture,
 * then, an implementing PUZZLE would provide an abstract class that
 * implements the core functionality of the puzzle mechanics, state,
 * rules, next state function, etc. and then a series of concrete
 * sub-classes that actually instantiate different heuristics.
 * <p>
 * This class provides direct access to the parent state that
 * generated a particular node; the designer may also wish to provide
 * access to some representation of the move that generated this
 * state.
 *
 * @author Terran Lane
 * @version 1.0
 */
public interface PuzState {
  /**
   * Return heuristic estimate of the value of this node.  This
   * function should provide the combined "cost-so-far" function with
   * the "estimated cost-to-goal" function, <code>h()</code>.  That
   * is, this represents the complete function
   * <code>f(s)=g(s)+h(s)</code> from the lecture's notation.
   * <p>
   * To provide different search strategies, override this function
   * with different heuristic estimates.
   *
   * @return The total function representing cost-from-start
   * <em>plus</em> estimated cost-to-goal.
   */
  public double heuristic();

  /**
   * Return the distance from the start state.  Required to be
   * non-negative and identically 0 for a start state.  May be 0 if
   * unimplemented in some deriving class.  This is <code>g(s)</code>
   * from the lectures's notation.
   * <p>
   * Note that this represents only the distance along the path by
   * which this particular instance node was generated; it's possible
   * to generate the same node along different paths, all of which may
   * have different cost-from-start's.
   *
   * @return The recorded cost-from-start function for this node,
   * along the path that generated it.
   */
  public double distFromStart();

  /**
   * Set or reset the cost-from-start value for the current node.
   * This is intended to be used when re-ordering a previously
   * existing node.
   *
   * @param d New value for the cost-from-start function
   * (<code>g(s)</code>) for this node.
   */
  public void setDistFromStart(double d);

  /**
   * Tests whether the current node is a goal state or not.
   *
   * @return <CODE>true</CODE> if this state is a goal state, else
   * <CODE>false</CODE>.
   */
  public boolean goalP();

  /**
   * Return an iterator over the children of the current node.  If no
   * children are available, the iterator should simply return
   * <CODE>false</CODE> for <CODE>hasNext()</CODE>.  The returned
   * iterator may or may not produce the "parent" state of the current
   * node, at the designer's choice.
   *
   * @return Iterator over the children state of this node.
   */
  public Iterator children();

  /**
   * Retrieve the parent of the current node.  This should return a
   * reference to the node that created this node via some action.  If
   * this node is parentless (e.g., is the START node), this should
   * return <code>null</code>
   *
   * @return parent pointer for this node, or <code>null</code> for the
   * root node.
   */
  public PuzState parent();
}
