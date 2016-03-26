package com.michaelzanussi.astar;

import java.util.*;

/**
 * A hybrid priority queue data structure, combining the best of the
 * hash and heap worlds. 
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public final class HashingHeap implements PriorityQueue {

	// Position of the root element in the vector.
	private static final int _ROOT = 0;

	// Use a vector to represent the heap and a tree map
	// for the hash table.
	private Vector _heap = null;
	private Map _hash = null;
	
	/**
	 * No-arg constructor.
	 */
	public HashingHeap() {
		
		_heap = new Vector();
		_hash = new HashMap();
		
	}
	
	/**
	 * Tests whether the hybrid priority queue contains a specific 
	 * key. Runs in <tt>O(1)</tt> time.
	 *  
	 * @param key the key to test for existence.
	 * @return <code>true</code> if key was found, otherwise 
	 * <code>false</code>.
	 * @throws NullPointerException If passed key is 
	 * <code>null</code>.
	 */
	public boolean contains( PuzState key ) throws NullPointerException {
		
		// Do not allow null keys.
		if( key == null ) {
			throw new NullPointerException( "HashingHeap.contains error: " +
					"Key cannot be null." );
		}
		
		return ( _hash.get( key ) == null ? false : true );
				
	}
	
	/**
	 * Returns the element located at the specified key. The element
	 * contains the index into the heap where the key is located. 
	 * Runs in <tt>O(1)</tt> time.
	 * 
	 * @param key the key to locate.
	 * @return the element located at the specified key.
	 * @throws NullPointerException If passed key is 
	 * <code>null</code>.
	 */
	public PuzState get( PuzState key ) throws NullPointerException {
		
		// Do not allow null keys.
		if( key == null ) {
			throw new NullPointerException( "HashingHeap.get error: " +
					"Key cannot be null." );
		}
		
		// The index into the heap is stored in the hash table at
		// the key's location.
		return _getHeapElement( ((Integer)_hash.get( key )).intValue() );
		
	}
	
	/**
	 * Inserts a new element into the hybrid priority queue. Runs in
	 * <tt>O(logn)</tt> time.
	 * 
	 * @param key the element to insert into the hybrid priority queue.
	 * @throws NullPointerException If passed key is 
	 * <code>null</code>.
	 * @throws IndexOutOfBoundException If bound is exceeded.
	 */
	public void insertItem( PuzState key ) throws NullPointerException, IndexOutOfBoundsException {

		// Do not allow null keys.
		if( key == null ) {
			throw new NullPointerException( "HashingHeap.insertItem error: " +
					"Key cannot be null." );
		}
		
		// Add key to the end of the vector. This operation occurs
		// in constant O(1) time.
		_heap.add( key );
		
		// Verify we haven't reached the open list bound yet.
		if( size() > Global.getOpenListBound() ) {
			throw new IndexOutOfBoundsException( "HashingHeap.insertItem error: " +
					"OpenListBound exceeded. Set to: " + Global.getOpenListBound() + 
					", Current count: " + size() );
		}
		
		// Calculate the key's index.		
		int i = size() - 1;
		
		// Add the key to the hash table, using the index into the 
		// heap as its value. This operation occurs amortized in O(1) time.
		_hash.put( key, new Integer(i));

		// Put this new key into its correct position in the heap. This
		// is a min-heap, so the shortest distance is located at the root.
		// This operation occurs in O(logn) time.
		while( i > _ROOT && ( _getHeapElement( _parent( i ) ).heuristic() > _getHeapElement( i ).heuristic() ) ) {
			_swap( i, _parent(i) );
			i = _parent(i);
		}
		
	}

	/**
	 * Tests whether the hybrid priority queue is empty or not. Runs in
	 * constant <tt>O(1)</tt> time.
	 * 
	 * @return <code>true</code> if the hybrid priority queue is
	 * empty, otherwise <code>false</code>.
	 */
	public boolean isEmpty() {
		
		return size() == 0;
		
	}
	
	/**
	 * Returns (but does not remove) the element of the hybrid
	 * priority queue with the smallest key (in this case
	 * the key, since the key and element are the same). Runs
	 * in constant <code>O(1)</code> time.
	 * 
	 * @return the element with the smallest key.
	 */
	public PuzState minElement() {
		
		// Element and key are interchangeable, so we'll make
		// a call to minKey() here.
		return minKey();
		
	}
	
	/**
	 * Returns (but does not remove) the smallest key in the
	 * hybrid piority queue. Runs in constant <code>O(1)</code>
	 * time. 
	 * 
	 * @return the smallest key.
	 */
	public PuzState minKey() {
		
		// Min-heap, so return the root element.
		return _getHeapElement( _ROOT );
		
	}
	
	/**
	 * Removes an arbitrary key from the hybrid priority queue. 
	 * Runs in <tt>O(logn)</tt> time.
	 * 
	 * @param key the key to remove.
	 * @return the removed key.
	 * @throws NullPointerException If passed key is 
	 * <code>null</code>.
	 * @throws IndexOutOfBoundsException If <code>HashingHeap</code>
	 * is empty.
	 */
	public PuzState remove( PuzState key ) throws NullPointerException, IndexOutOfBoundsException {
		
		// Do not allow null keys.
		if( key == null ) {
			throw new NullPointerException( "HashingHeap.remove error: " +
					"Key cannot be null." );
		}
		
		// Don't allow removal from empty vectors.
		if( size() == 0 ) {
			throw new IndexOutOfBoundsException( "HashingHeap.remove error: " +
					"Cannot remove keys from an empty vector." );
		}
		
		// Find the index for this key by locating it in
		// the hash table. Runs in O(1) time.
		int index = ((Integer)_hash.get( key )).intValue();
		
		// Save the element/key we wish to remove.
		PuzState ps = _getHeapElement( index );
		
		// Remove the key from the hash table first. Runs in
		// O(1) time.
		if( _hash.remove( ps ) == null) {
			throw new NullPointerException( "HashingHeap.remove error: " +
					"Cannot locate key in hash table." );
		}

		// Remove the key from the heap. Runs in constant O(1)
		// time.
		if( index == size() - 1 ) {
			// Removal of the last element in the heap.
			_heap.removeElementAt( index );
		}
		else {
			// We're removing something in the heap that isn't the last
			// element. First store a copy of the last element since it'll
			// be replacing the element we wish to remove permanently.
			PuzState last = _getHeapElement( size() - 1 );
			// Remove the last element in the heap.
			_heap.removeElementAt( size() - 1 );
			// Remove the last element that was in the heap from the hash.
			if( _hash.remove( last ) == null) {
				throw new NullPointerException( "HashingHeap.remove error: " +
				"Cannot locate key in hash table." );
			}
			// Replace the element we'd like to remove with the last element
			// that we just removed.
			_setHeapElement( last, index );
			// Put that last element back into the hash with the new index.
			_hash.put( last, new Integer( index ) );
		}
		
		// Heapify the min-heap. Runs in O(logn) time.
		_heapify( _ROOT );
		
		// Return the removed element.
		return ps;
		
	}
	
	/**
	 * Removes from the hybrid priority queue and returns an element
	 * with the smallest key. Runs in O(logn) time.
	 * 
	 * @return the element with the smallest key.
	 * @throws IndexOutOfBoundsException If <code>HashingHeap</code>
	 * is empty.
	 * @throws NullPointerException If key cannot be located in hash
	 * table.
	 */
	public PuzState removeMin() throws IndexOutOfBoundsException, NullPointerException {
		
		// Don't allow removal from empty vectors.
		if( size() == 0 ) {
			throw new IndexOutOfBoundsException( "HashingHeap.removeMin error: " +
					"Cannot remove keys from an empty vector." );
		}
		
		// Save the minimum element first. We'll need the last
		// element to place in min element's position before
		// we heapify.
		PuzState first = _getHeapElement( _ROOT );
		PuzState last = _getHeapElement( size() - 1 );
		
		// Remove the minimum element from the hash table. Runs
		// in O(1) time.
		Integer removal = null;
		if( ( removal = (Integer)_hash.remove( first ) ) == null ) {
			throw new NullPointerException( "HashingHeap.removeMin error: " +
					"Cannot locate key in hash table." );
		}
		
		// Remove the last element on the heap (it'll be
		// moved to the first position. Runs in constant O(1) time.
		_heap.removeElementAt( size() - 1 );

		// Rebuild the heap. Runs in O(logn) time.		
		if( first != last ) {
			_setHeapElement( last, _ROOT );
			if( _hash.remove( last ) == null) {
				throw new NullPointerException( "HashingHeap.removeMin error: " +
						"Cannot locate key in hash table." );
			}
			_hash.put( last, new Integer( _ROOT ) );
			_heapify( _ROOT );
		}
		
		// Return the minimum element.
		return first;
		
	}
	
	/**
	 * Returns the number of elements in the heap. Runs in
	 * constant <tt>O(1)</tt> time.
	 * 
	 * @return the number of elements in the heap.
	 */
	public int size() {
		
		return _heap.size();
		
	}
	
	/**
	 * Returns the heap element located at a specific index. Runs in
	 * constant <tt>O(1)</tt> time.
	 * 
	 * @param i the index of the heap element.
	 * @return the heap element located at index <code>i</code>.
	 * @throws IllegalArgumentException If index is < 0.
	 */
	private PuzState _getHeapElement( int i ) throws IllegalArgumentException {
		
		if( i < 0 ) {
			throw new IllegalArgumentException( "HashingHeap._getHeapElement error: " +
					"Index must be >= 0. Received: " + i );
		}
		
		return (PuzState)_heap.elementAt( i );
		
	}
	
	/**
	 * Heapify the heap (min-heap). Runs in <tt>O(logn) time</tt>.
	 * 
	 * @param root the root node.
	 * @throws IllegalArgumentException If root index is < 0.
	 */
	private void _heapify( int root ) throws IllegalArgumentException {

		// Don't allow invalid indices.
		if( root < 0 ) {
			throw new IllegalArgumentException( "HashingHeap._heapify error: " +
					"Index must be >= 0. Received: " + root );
		}
		
		// Holds the index with the smallest heuristic.
		int smallest;
		
		// Retrieve the root's children's indices.
		int l = _left( root );
		int r = _right( root );
		
		// See if the left child is smaller than the root.
		if( l <= size()-1 && _getHeapElement(l).heuristic() < _getHeapElement(root).heuristic() ) {
			smallest = l;
		}
		else {
			smallest = root;
		}

		// See if the right child is smaller than the root or left child.
		if( r <= size()-1 && _getHeapElement(r).heuristic() < _getHeapElement(smallest).heuristic() ) {
			smallest = r;
		}

		// If either the left or right child is smaller than the root,
		// swap the positions and heapify.
		if( smallest != root ) {
			_swap( root, smallest );
			_heapify( smallest );
		}
		
	}
	
	/**
	 * Given the index <code>i</code> of a node, returns the index of
	 * the node's left child. Runs in constant <tt>O(1)</tt> time.
	 * 
	 * @param i the node index.
	 * @return the index of the node's left child.
	 * @throws IllegalArgumentException If index is < 0.
	 */
	private int _left( int i ) throws IllegalArgumentException{
		
		// Don't allow invalid indices.
		if( i < 0 ) {
			throw new IllegalArgumentException( "HashingHeap._left error: " +
					"Index must be >= 0. Received: " + i );
		}
		
		return ( ( i * 2 ) + 1 );
		
	}

	/**
	 * Given the index <code>i</code> of a node, returns the index of
	 * the node's parent or -1 if <code>null</code>. Runs in constant
	 * <tt>O(1)</tt> time.
	 * 
	 * @param i the node index.
	 * @return the index of the node's parent.
	 * @throws IllegalArgumentException If index is < 0.
	 */
	private int _parent( int i ) throws IllegalArgumentException {
		
		// Don't allow invalid indices.
		if( i < 0 ) {
			throw new IllegalArgumentException( "HashingHeap._parent error: " +
					"Index must be >= 0. Received: " + i );
		}
		
		return (int)Math.floor( ( ( i + 1 ) / 2 ) - 1 );
		
	}
	
	/**
	 * Given the index <code>i</code> of a node, returns the index of
	 * the node's right child. Runs in constant <tt>O(1)</tt> time.
	 * 
	 * @param i the node index.
	 * @return the index of the node's right child.
	 * @throws IllegalArgumentException If index is < 0.
	 */
	private int _right( int i ) throws IllegalArgumentException {
		
		// Don't allow invalid indices.
		if( i < 0 ) {
			throw new IllegalArgumentException( "HashingHeap._right error: " +
					"Index must be >= 0. Received: " + i );
		}
		
		return ( ( i * 2 ) + 2 );
		
	}

	/**
	 * Sets the element at index <code>i</code> to new <code>PuzState</code>.
	 * Runs in constant <tt>O(1)</tt> time.
	 * 
	 * @param p the new element.
	 * @param i the index to set element to.
	 * @throws NullPointerException If state <code>p</code> is <code>null</code>.
	 * @throws IllegalArgumentException If index < 0.
	 */
	private void _setHeapElement( PuzState p, int i ) throws NullPointerException, IllegalArgumentException {
		
		// Do not allow null keys.
		if( p == null ) {
			throw new NullPointerException( "HashingHeap._setHeapElement error: " +
					"State cannot be null." );
		}
		
		// Don't allow invalid indices.
		if( i < 0 ) {
			throw new IllegalArgumentException( "HashingHeap._setHeapElement error: " +
					"Index must be >= 0. Received: " + i );
		}
		
		_heap.setElementAt( p, i );
		
	}

	/**
	 * Swap the contents of two cells. Not only do we swap positions in the
	 * binary tree (vector) but we'll need to update the hash table too. Runs
	 * in <tt>O(1)</tt> time.
	 * 
	 * @param a the first element to swap.
	 * @param b the second element to swap.
	 * @throws IllegalArgumentException If either index is < 0.
	 * @throws NullPointerException If key cannot be located in hash table.
	 */
	private void _swap( int a, int b ) throws IllegalArgumentException, NullPointerException {
		
		// Don't allow invalid indices.
		if( a < 0 || b < 0 ) {
			throw new IllegalArgumentException( "HashingHeap._swap error: " +
					"Indices must be >= 0. Received: " + a + " and " + b );
		}
		
		// Save copies of the elements to swap. Both run
		// in constant O(1) time.
		PuzState psA = _getHeapElement( a );
		PuzState psB = _getHeapElement( b );
		
		// Swap in hash table first. Runs in O(1) time.
		if( _hash.remove( psA ) == null) {
			throw new NullPointerException( "HashingHeap._swap error: " +
					"Cannot locate key in hash table." );
		}
		_hash.put( psA, new Integer( b ) );
		if( _hash.remove( psB ) == null) {
			throw new NullPointerException( "HashingHeap._swap error: " +
					"Cannot locate key in hash table." );
		}
		_hash.put( psB, new Integer( a ) );
		
		// Now swap in the heap. Runs in constant O(1) time.
		PuzState temp = psA;
		_setHeapElement( psB, a );
		_setHeapElement( temp, b );
		
	}
	
}