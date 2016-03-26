package com.michaelzanussi.astar;

/**
 * Marker interface for a {@link PuzState} indicating that its
 * heuristic is globally monotonic.  A <code>PuzState</code> object
 * that implements this interface <em>guarantees</em> that its
 * heuristic is, in fact, monotonic; deviations from this guarantee
 * may be punished by arbitrarily poor and/or incorrect performance of
 * any search algorithm that takes the object at its word.
 *
 * @author Terran Lane
 * @version 1.0
 */
 public interface Monotonic {};
