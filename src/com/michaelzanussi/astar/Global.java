package com.michaelzanussi.astar;

/**
 * Provides global access to various puzzle statistics and file
 * handles. Being global, all methods can be called from anywhere.
 *
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class Global {

	// Debug mode flag.
	private static boolean _debug;

	// STATS options.
	private static int _nodesClosed;
	private static int _nodesOpened;
	private static int _nodesReopened;
	private static int _openListBound;
	private static int _openListMaxLen;
	private static long _timeBound;
	private static int _totalNodesBound;
	
	// RESULTS
	private static boolean _statePath;
	
	// Application start time.
	private static long _startTime;
	
	// Other useful stats.
	private static int _closedListSize;
	private static double _maxRatio;
	private static double _minRatio;
	private static int _openListSize;

	// Define the output files.
	private static TextFileWriter _outFile = null;
	private static TextFileWriter _logFile = null;
	private static TextFileWriter _errFile = null;
	
	// Unit test support.
	private static String data;
	
	/**
	 * No-arg constructor.
	 */
	public Global() {
	
		reset();
		
	}

	/**
	 * Outputs a line of text to the standard output or an error file.
	 * If the file handle is <code>null</code>, output goes to the standard 
	 * output; otherwise, output is directed to the necessary file.
	 * 
	 * @param message the message to log.
	 */
	public static void error( String message ) {
		
		if( getErrFile() == null ) {
			System.err.println( message );
		}
		else {
			getErrFile().writeln( message );
		}
		
	}
	
	/**
	 * Returns the debug status.
	 * 
	 * @return the debug status.
	 */
	public static boolean getDebug() { 
		
		return _debug; 
	
	}

	/**
	 * Returns the error file.
	 * 
	 * @return the error file.
	 */
	public static TextFileWriter getErrFile() {
		
		return _errFile;
		
	}
	
	/**
	 * Returns the log file.
	 * 
	 * @return the log file.
	 */
	public static TextFileWriter getLogFile() {
		
		return _logFile;
		
	}
	
	/**
	 * Returns the maximum ratio of open list nodes to closed
	 * list nodes.
	 * 
	 * @return the maximum ratio.
	 */
	public static double getMaxRatio() {
		
		return _maxRatio;
		
	}
	
	/**
	 * Returns the minimum ratio of open list nodes to closed
	 * list nodes.
	 * 
	 * @return the minimum ratio.
	 */
	public static double getMinRatio() {
		
		return _minRatio;
		
	}
	
	/**
	 * Returns the total number of nodes on the closed list.
	 * 
	 * @return the total number of nodes on the closed list.
	 */
	public static int getNodesClosed() { 
		
		return _nodesClosed;
		
	}
	
	/**
	 * Returns the total number of nodes opened during the search.
	 * 
	 * @return the total number of nodes opened during the search.
	 */
	public static int getNodesOpened() { 
		
		return _nodesOpened; 
	
	}
	
	/**
	 * Returns the number of nodes moved from the closed list to
	 * the open list during the search.
	 * 
	 * @return the number of nodes moved from the closed list to
	 * the open list.
	 */
	public static int getNodesReopened() { 
		
		return _nodesReopened; 
	
	}
	
	/**
	 * Returns the maximum number of elements that are allowed in the
	 * open list. The search is terminated if this value is exceeded.
	 * The default value is <code>Integer.MAX_VALUE</code>.
	 * 
	 * @return the maximum allowable size of the open list.
	 */
	public static int getOpenListBound() { 
		
		return _openListBound; 
	
	}
	
	/**
	 * Returns the maximum number of nodes on the open list encountered
	 * so far.
	 * 
	 * @return the maximum number of nodes on the open list.
	 */
	public static int getOpenListMaxLen() {
		
		return _openListMaxLen;
		
	}
	
	/**
	 * Returns the output file.
	 * 
	 * @return the output file.
	 */
	public static TextFileWriter getOutFile() {
		
		return _outFile;
		
	}
	
	/**
	 * Returns the maximum number of nodes that can be in the search
	 * at one time (total of the open list and the closed list). The
	 * search is terminated if this number is exceeded. The default 
	 * value is <code>Integer.MAX_VALUE</code>.
	 * 
	 * @return the maximum allowable number of nodes.
	 */
	public static int getTotalNodesBound() { 
		
		return _totalNodesBound; 
	
	}

	/**
	 * Increment the open node count.
	 */
	public static void incNodesOpened() { 
		
		_nodesOpened++; 
	
	}
	
	/**
	 * Increment the reopened node count.
	 */
	public static void incNodesReopened() { 
		
		_nodesReopened++; 
	
	}
	
	/**
	 * Returns <code>true</code> if the amount of time spent searching
	 * (current time - start time) exceeds the time bound, otherwise
	 * returns <code>false</code>.
	 * 
	 * @return <code>true</code> if time is up, otherwise <code>false</code>.
	 */
	public static boolean isTimeUp() { 
		
		return ( System.currentTimeMillis() - _startTime > _timeBound ? true : false ); 
		
	}
	
	/**
	 * Return data for unit testing.
	 * 
	 * @return data for unit testing.
	 */
	public static String getData() {
		
		return data;
		
	}
	
	/**
	 * Outputs a line of text to the standard output or a log file.
	 * If the file handle is <code>null</code>, output goes to the standard 
	 * output; otherwise, output is directed to the necessary file.
	 * 
	 * @param message the message to log.
	 */
	public static void log( String message ) {
		
		data += message;	// unit testing
		
		if( getLogFile() == null ) {
			System.out.println( message );
		}
		else {
			getLogFile().writeln( message );
		}
		
	}
	
	/**
	 * Outputs a line of text to the standard output or an output file.
	 * If the file handle is <code>null</code>, output goes to the standard 
	 * output; otherwise, output is directed to the necessary file.
	 * 
	 * @param message the message to output.
	 */
	public static void output( String message ) {
		
		data += message;	// unit testing
		
		if( getOutFile() == null ) {
			System.out.println( message );
		}
		else {
			getOutFile().writeln( message );
		}
		
	}
	
	/**
	 * Returns whether to report the complete sequence of states encountered
	 * on the solution path, including the start and goal states.
	 * 
	 * @return whether to report the complete sequence of states encountered.
	 */
	public static boolean reportStatePath() {
		
		return _statePath;
		
	}
	
	/**
	 * Reset the puzzle statistics.
	 */
	public static void reset() {
	
		_closedListSize = 0;
		_debug = false;
		_maxRatio = 0.0;
		_minRatio = 0.0;
		_nodesClosed = 0;
		_nodesOpened = 0;
		_nodesReopened = 0;
		_openListBound = Integer.MAX_VALUE;
		_openListMaxLen = 0;
		_openListSize = 0;
		_startTime = System.currentTimeMillis();
		_statePath = false;
		_timeBound = Long.MAX_VALUE;
		_totalNodesBound = Integer.MAX_VALUE;
		data = "";
		
	}
	
	/**
	 * Set the size of the closed list.
	 * 
	 * @param value the size of the closed list.
	 */
	public static void setClosedListSize( int value ) {
		
		_closedListSize = value;
		
		_calculateRatios();
		
	}
	
	/**
	 * Set the debug status.
	 * 
	 * @param value the debug status.
	 */
	public static void setDebug( boolean value ) { 
		
		_debug = value; 
	
	}
	
	/**
	 * Sets the error file.
	 * 
	 * @param tfw the error file.
	 */
	public static void setErrFile( TextFileWriter tfw ) {
		
		_errFile = tfw;
		
	}
	
	/**
	 * Sets the log file.
	 * 
	 * @param tfw the log file.
	 */
	public static void setLogFile( TextFileWriter tfw ) {
		
		_logFile = tfw;
		
	}
	
	/**
	 * Set the number of nodes currently on the closed list.
	 * 
	 * @param value the number of closed nodes.
	 */
	public static void setNodesClosed( int value ) { 
		
		_nodesClosed = value; 
	
	}
	
	/**
	 * Sets the maximum number of elements that may be in the
	 * open list. The search is terminated if this number is exceeded.
	 * The default value is <code>Integer.MAX_VALUE</code>.
	 * 
	 * @param value the maximum number of elements.
	 */
	public static void setOpenListBound( int value ) { 
		
		_openListBound = value; 
	
	}
	
	/**
	 * Set the size of the open list. Also updates the open
	 * list maximum length as necessary.
	 * 
	 * @param value the size of the open list.
	 */
	public static void setOpenListSize( int value ) {
		
		_openListSize = value;
		
		if( _openListSize > _openListMaxLen ) {
			_openListMaxLen = _openListSize;
		}
		
		_calculateRatios();
		
	}
	
	/**
	 * Sets the output file.
	 * 
	 * @param tfw the output file.
	 * @throws NullPointerException If <code>tfw</code> is <code>null</code>.
	 */
	public static void setOutFile( TextFileWriter tfw ) {
		
		_outFile = tfw;
		
	}
	
	/**
	 * Set whether to report the complete sequence of states encountered
	 * on the solution path. The default value is <code>false</code>.
	 * 
	 * @param value the number of closed nodes.
	 */
	public static void setStatePath( boolean value ) { 
		
		_statePath = value; 
		
	}
	
	/**
	 * Sets the maximum running time of the search, given in milliseconds.
	 * The search is terminated if this value is exceeded. The default
	 * value is <code>Long.MAX_VALUE</code>.
	 * 
	 * @param value the maximum running time.
	 */
	public static void setTimeBound( long value ) { 
		
		_timeBound = value; 
	
	}
	
	/**
	 * Sets the maximum number of nodes that can be in the search
	 * at one time (total of the open list and the closed list). The
	 * search is terminated if this number is exceeded. The default 
	 * value is <code>Integer.MAX_VALUE</code>.
	 * 
	 * @param value the maxium number of nodes.
	 */
	public static void setTotalNodesBound( int value ) { 
		
		_totalNodesBound = value; 
	
	}

	/**
	 * Helper function Calculates the min and max ratios of the size 
	 * of the open list to the size of the closed list.
	 */
	private static void _calculateRatios() {

		// The calculated ratio.
		double ratio = 0.0;
		
		// Special case when there are no closed nodes.
		if( _closedListSize ==  0) {
			if( _openListSize == 0) {
				ratio = 1.0;
			}
			else {
				ratio = (double)_openListSize;
			}
		}
		// Normal case, both lists have nodes.
		else {
			ratio = (double)_openListSize / _closedListSize;
		}
		
		// Set max ratio.
		if( ratio > _maxRatio ) {
			_maxRatio = ratio;
		}
		
		// Set min ratio.
		if( ratio < _minRatio ) {
			_minRatio = ratio;
		}
		
		// *DEBUG*
		if( Global.getDebug() ) {
			System.out.println("*DEBUG* Open/Closed ratio: " + ratio );
		}
	}
	
}
