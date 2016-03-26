package com.michaelzanussi.astar;

/**
 * A standard interface for a priority queue data structure. A
 * more general interace would use <code>Object</code> rather than
 * <code>PuzState</code>, but for this project <code>PuzState</code>
 * allows for a simpler implementation to be utilized.
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public interface PriorityQueue {

	/**
	 * Inserts a new element into the priority queue.
	 * 
	 * @param key the element to insert into the priority queue.
	 */
	public void insertItem( PuzState key );
	
	/**
	 * Tests whether the priority queue is empty or not.
	 * 
	 * @return <code>true</code> if the priority queue is
	 * empty, otherwise <code>false</code>.
	 */
	public boolean isEmpty();
	
	/**
	 * Returns (but does not remove) the element of the
	 * priority queue with the smallest key (in this case
	 * the key, since the key and element are the same).
	 * 
	 * @return the element with the smallest key.
	 */
	public PuzState minElement();
	
	/**
	 * Returns (but does not remove) the smallest key in the
	 * piority queue. 
	 * 
	 * @return the smallest key.
	 */
	public PuzState minKey();
	
	/**
	 * Removes from the priority queue and returns an element
	 * with the smallest key.
	 * 
	 * @return the element with the smallest key.
	 */
	public PuzState removeMin();

	/**
	 * Returns the number of elements stored in the priority queue.
	 * 
	 * @return the number of elements stored in the priority queue.
	 */
	public int size();
	
}
