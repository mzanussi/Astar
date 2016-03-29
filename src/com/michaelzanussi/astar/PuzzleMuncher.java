package com.michaelzanussi.astar;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * The <code>PuzzleMuncher</code> universal puzzle engine driver. <p>
 * 
 * The driver loads the input file and begins the parsing. If necessary, 
 * output, log and error files are opened. During parsing, puzzle data is
 * stored in private inner classes, and start and goal states are defined.
 * The inner classes are also responsible for printing solutions (if found)
 * after <code>AStar.path()</code> has been called. All exceptions are
 * filtered down to the <code>PuzzleMuncher</code> driver and are handled
 * accordingly with calls to the <code>Global</code> output mechanism. 
 * 
 * @author <a href="mailto:iosdevx@gmail.com">Michael Zanussi</a>
 * @version 1.0 (29 Mar 2004) 
 */
public class PuzzleMuncher {

	// The lexer.
	private Lexer _lexer;
	
	// Current heuristic name.
	private String _hName;
	
	// The output, log, and error files.
	private String _outFile;
	private String _logFile;
	private String _errFile;

	// The RESULTS options.
	private boolean _solnPathLen;
	private boolean _moveSeq;
	
	// The STATS options.
	private boolean _nodesOpened;
	private boolean _openListMaxLen;
	private boolean _nodesClosed;
	private boolean _numReopened;
	
	// The individual puzzle data.
	private _GridPuzzleData _gridData;
	private _SPPuzzleData _spData;
	private _MCPuzzleData _mcData;
	
	/**
	 * Standard constructor.
	 * 
	 * @param lexer the lexer.
	 * @throws NullPointerException If no lexer was specified.
	 */
	public PuzzleMuncher( Lexer lexer ) throws NullPointerException {

		// Was a lexer specified?
		if( lexer == null ) {
			throw new NullPointerException( "PuzzleMuncher.PuzzleMuncher error: " +
					"The lexer cannot be null." );
		}
		
		_lexer = lexer;
		_hName = null;
		_outFile = null;
		_logFile = null;
		_errFile = null;
		_spData = null;
		_mcData = null;
		_gridData = null;
		
		_reset();
		
	}

	/**
	 * Begins the parsing of the puzzle input file.
	 * 
	 * @throws ParsingException If any unknown grammar is encountered.
	 */
	public void parse() throws ParsingException, UnsupportedOperationException {
		
		while( _lexer.hasMoreTokens() ) {
			
			// Get the first token.
			Token token = _lexer.nextToken();
			String strToken = token.getToken();

			// Empty file?
			if( strToken.length() == 0 ) {
				return;
			}

			// The beginning of a puzzle definition. The puzzle definition
			// defines what the puzzle is and the initial data (start and
			// goal states, for example). The individual puzzle data
			// structures are filled with calls to their respective parsers.
			
			if( strToken.equals( "Puzzle" ) ) {
				
				// Get the puzzle family. 
				token = _lexer.nextToken();
				strToken = token.getToken();
				
				if( strToken.equals( "MissionariesAndCannibals" ) ) {
					// Push the current token back onto the stack.
					_lexer.pushBack( token );
					// Parse the MissionariesAndCannibals puzzle portion from the input
					// file and store the resultant data off.
					_mcData = _parseMCPuzzle( _lexer );
				}
				else if( strToken.equals( "ShortestPaths" ) ) {
					// Push the current token back onto the stack.
					_lexer.pushBack( token );
					// Parse the ShortestPaths puzzle portion from the input
					// file and store the resultant data off.
					_spData = _parseSPPuzzle( _lexer );
					// Setup the city table.
					_spData.fillTable();
				}
				else if( strToken.equals( "Grid" ) ) {
					// Push the current token back onto the stack.
					_lexer.pushBack( token );
					// Parse the Grid puzzle portion from the input
					// file and store the resultant data off.
					_gridData = _parseGridPuzzle( _lexer );
				}
				else if( strToken.equals( "NToTheKPuzzle" ) ) {
					// Push the current token back onto the stack. 
					_lexer.pushBack( token );
					// Parse the NToTheKPuzzle puzzle portion from the input
					// file and store the resultant data off.
					_parseN2KPuzzle( _lexer );
				}
			}
			
			// The beginning of a control sequence definition, which define
			// statistics and results to be printed, whether puzzle data
			// is reset, and eventually to begin the puzzle excecution.
			
			else {
				
				// Handle Reset
				if( strToken.equals( "Reset" ) ) {
					_reset();
				}
				
				// Handle Run
				else if( strToken.equals( "Run" ) ) {
					
					// Output the heuristic name.
					Global.output( "\nHeuristic: " + _hName );
					
					// Execute puzzle: Missionaries and Cannibals
					
					if( _hName.equals( "MandCTrips" ) ) {
						
						// Set the start state and goal state.
						MandCTrips start = (MandCTrips)_mcData.getStart();
						MandCTrips goal = (MandCTrips)_mcData.getGoal();
						
						// Set some initial start state values.
						start.setGoal( goal );
						start.setTotalC( _mcData.getTotalC() );
						start.setTotalM( _mcData.getTotalM() );
						
						// Set some initial goal state values.
						goal.setTotalC( _mcData.getTotalC() );
						goal.setTotalM( _mcData.getTotalM() );
						
						// Find a solution!
						AStar astar = new AStar( start, goal );
						_mcData.print( astar.path() );
						
					}
					
					// Execute puzzle: Missionaries and Cannibals
					
					else if( _hName.equals( "MandCCount" ) ) {

						// Set the start state and goal state.
						MandCCount start = (MandCCount)_mcData.getStart();
						MandCCount goal = (MandCCount)_mcData.getGoal();
						
						// Set some initial start state values.
						start.setGoal( goal );
						start.setTotalC( _mcData.getTotalC() );
						start.setTotalM( _mcData.getTotalM() );
						
						// Set some initial goal state values.
						goal.setTotalC( _mcData.getTotalC() );
						goal.setTotalM( _mcData.getTotalM() );
						
						// Find a solution!
						AStar astar = new AStar( start, goal );
						_mcData.print( astar.path() );
						
					}
					
					// Execute puzzle: ShortestPaths
					
					else if( _hName.equals( "ShortestPathsNonMono" ) ) {
						
						// Set the start state and goal state.
						ShortestPathsNonMono start = (ShortestPathsNonMono)_spData.getMap().get( _spData.getStart() );
						ShortestPathsNonMono goal = (ShortestPathsNonMono)_spData.getMap().get( _spData.getGoal() );
						start.setGoal( goal );
						
						// Find a solution!
						AStar astar = new AStar( start, goal );
						_spData.print( astar.path() );
						
					}

					// Execute puzzle: ShortestPaths
					
					else if( _hName.equals( "ShortestPathsMono" ) ) {

						// Set the start state and goal state.
						ShortestPathsMono start = (ShortestPathsMono)_spData.getMap().get( _spData.getStart() );
						ShortestPathsMono goal = (ShortestPathsMono)_spData.getMap().get( _spData.getGoal() );
						start.setGoal( goal );
						
						// Find a solution!
						AStar astar = new AStar( start, goal );
						_spData.print( astar.path() );
						
					}
					
					// Execute puzzle: Grid
					
					else if( _hName.equals( "GridManhattan" ) ) {

						// Set the start state and goal state.
						GridManhattan start = (GridManhattan)_gridData.getStart();
						GridManhattan goal = (GridManhattan)_gridData.getGoal();
						start.setGoal( goal );
						
						// Find a solution!
						AStar astar = new AStar( start, goal );
						_gridData.print( astar.path() );
						
					}
					
					// Execute puzzle: Grid
					
					else if( _hName.equals( "GridStraight" ) ) {

						// Set the start state and goal state.
						GridStraight start = (GridStraight)_gridData.getStart();
						GridStraight goal = (GridStraight)_gridData.getGoal();
						start.setGoal( goal );
						
						// Find a solution!
						AStar astar = new AStar( start, goal );
						_gridData.print( astar.path() );
						
					}
					
					// Unsupported...
					
					else  {
						throw new UnsupportedOperationException( "PuzzleMuncher.parse " +
								"error: " + _hName + " not supported at this time." );
					}
					
					// Output some statistics, if applicable.

					// If a log file has been specified, output the heuristic
					// name for convenience.
					if( Global.getLogFile() != null ) {
						
						Global.log( "\nHeuristic: " + _hName );
						
					}
					
					// Report the total number of nodes opened.
					if( _nodesOpened ) {
						Global.log( "NodesOpened: " + Global.getNodesOpened());
					}
					
					// Report the number of nodes moved from the closed list
					// back to the open list.
					if( _numReopened ) { 
						Global.log( "NumReopened: " + Global.getNodesReopened());
					}
					
					// Report the number of nodes on the closed list.
					if( _nodesClosed ) {
						Global.log( "NodesClosed: " + Global.getNodesClosed());
					}
					
				}
				
				// Handle the optional OUTFILE.
				else if( strToken.equals( "OutFile" ) ) {

					// Get the output filename.
					_lexer.pushBack( token );
					_outFile = ParseFile.parse( _lexer );

					// Is filename in use by log file already?
					if( _outFile.equals( _logFile ) ) {
						System.err.println( "PuzzleMuncher.parse error: " +
								"Output filename '" + _errFile + "' is already " +
								"in use by the log file. Defaulting to standard output." );
						Global.setOutFile( null );
						_outFile = null;
						continue;
					}
					
					// Is filename in use by error file already?
					if( _outFile.equals( _errFile ) ) {
						System.err.println( "PuzzleMuncher.parse error: " +
								"Output filename '" + _errFile + "' is already " +
						"in use by the error file. Defaulting to standard output." );
						Global.setOutFile( null );
						_outFile = null;
						continue;
					}
					
					// Set the global output file.
					TextFileWriter tfw = Global.getOutFile();
					if( tfw != null ) {
						// An output file is already open. Close it.
						tfw.close();
					}
					
					// Open the new file. If the file already exists,
					// it'll append automatically.
					tfw = new TextFileWriter();
					if( tfw.open(  new File( _outFile ) ) ) {
						Global.setOutFile( tfw );
					}
					else {
						Global.setOutFile( null );
					}
					
				}
				
				// Handle the optional LOGFILE.
				else if( strToken.equals( "LogFile" ) ) {
					
					// Get the log filename.
					_lexer.pushBack( token );
					_logFile = ParseFile.parse( _lexer );

					// Is filename in use by output file already?
					if( _logFile.equals( _outFile ) ) {
						System.err.println( "PuzzleMuncher.parse error: " +
								"Log filename '" + _errFile + "' is already " +
						"in use by the output file. Defaulting to standard output." );
						Global.setLogFile( null );
						_logFile = null;
						continue;
					}
					
					// Is filename in use by error file already?
					if( _logFile.equals( _errFile ) ) {
						System.err.println( "PuzzleMuncher.parse error: " +
								"Log filename '" + _errFile + "' is already " +
						"in use by the error file. Defaulting to standard output." );
						Global.setLogFile( null );
						_logFile = null;
						continue;
					}
					
					// Set the global log file.
					TextFileWriter tfw = Global.getLogFile();
					if( tfw != null ) {
						// A log file is already open. Close it.
						tfw.close();
					}
					
					// Open the new file. If the file already exists,
					// it'll append automatically.
					tfw = new TextFileWriter();
					if( tfw.open(  new File( _logFile ) ) ) {
						Global.setLogFile( tfw );
					}
					else {
						Global.setLogFile( null );
					}
					
				}
				
				// Handle the optional ERRFILE.
				else if( strToken.equals( "ErrFile" ) ) {
					
					// Get the error filename.
					_lexer.pushBack( token );
					_errFile = ParseFile.parse( _lexer );
				
					// Is filename in use by log file already?
					if( _errFile.equals( _logFile ) ) {
						System.err.println( "PuzzleMuncher.parse error: " +
								"Error filename '" + _errFile + "' is already " +
								"in use by the log file. Defaulting to standard output." );
						Global.setErrFile( null );
						_errFile = null;
						continue;
					}
					
					// Is filename in use by output file already?
					if( _errFile.equals( _outFile ) ) {
						System.err.println( "PuzzleMuncher.parse error: " +
								"Error filename '" + _errFile + "' is already " +
						"in use by the output file. Defaulting to standard output." );
						Global.setErrFile( null );
						_errFile = null;
						continue;
					}
					
					// Set the global error file.
					TextFileWriter tfw = Global.getErrFile();
					if( tfw != null ) {
						// An error file is already open. Close it.
						tfw.close();
					}
					
					// Open the new file. If the file already exists,
					// it'll append automatically.
					tfw = new TextFileWriter();
					if( tfw.open(  new File( _errFile ) ) ) {
						Global.setErrFile( tfw );
					}
					else {
						Global.setErrFile( null );
					}
					
				}
				
				// Handle SEARCH-CTRL / OPENLIST-BOUND.
				else if( strToken.equals( "OpenListBound" ) ) {
					
					// Push token back onto stream before parsing value.
					_lexer.pushBack( token );
					Global.setOpenListBound( ParseSearchCtrl.parse( _lexer ) );
					
				}
				
				// Handle SEARCH-CTRL / TOTALNODES-BOUND.
				else if( strToken.equals( "TotalNodesBound" ) ) {

					// Push token back onto stream before parsing value.
					_lexer.pushBack( token );
					Global.setTotalNodesBound( ParseSearchCtrl.parse( _lexer ) );
					
				}
				
				// Handle SEARCH-CTRL / TIME-BOUND
				else if( strToken.equals( "TimeBound" ) ) {

					// Push token back onto stream before parsing value.
					_lexer.pushBack( token );
					Global.setTimeBound( ParseSearchCtrl.parse( _lexer ) );
					
				}
				
				// Handle RESULTS / SolnPathLen
				else if( strToken.equals( "SolnPathLen" ) ) {
					
					_solnPathLen = true;
					
				}
				
				// Handle RESULTS / StatePath
				else if( strToken.equals( "StatePath" ) ) {
					
					Global.setStatePath( true );
					
				}
				
				// Handle RESULTS / MoveSeq
				else if( strToken.equals( "MoveSeq" ) ) {
					
					_moveSeq = true;
					
				}
				
				// Handle RESULTS / Debug
				else if( strToken.equals( "Debug" ) ) {
					
					Global.setDebug( true );
					
				}
				
				// Handle STATS / NodesOpened
				else if( strToken.equals( "NodesOpened" ) ) {
					
					_nodesOpened = true;
					
				}
				
				// Handle STATS / OpenListMaxLen
				else if( strToken.equals( "OpenListMaxLen" ) ) {
					
					// Can be called at any time.
					Global.log( "OpenListMaxLen: " + Global.getOpenListMaxLen() );
				}
				
				// Handle STATS / NodesClosed
				else if( strToken.equals( "NodesClosed" ) ) {
					
					_nodesClosed = true;
					
				}
				
				// Handle STATS / NumReopened
				else if( strToken.equals( "NumReopened" ) ) {
					
					_numReopened = true;
					
				}
				
				// Handle STATS / OpenClosedRatio
				else if( strToken.equals( "OpenClosedRatio" ) ) {
					
					// Can be called at any time.
					Global.log( "OpenClosedRatio (min): " + Global.getMinRatio() );
					Global.log( "OpenClosedRatio (max): " + Global.getMaxRatio() );
					
				}
				
				// Handle other. 
				else {
					
					// Unknown grammar has been found.
					throw new ParsingException( "PuzzleMuncher.parse error: " +
							"Unknown grammar encountered in input file: '" + strToken + "'");
					
				}
				
			}
			
		}
	}
	
	/**
	 * This inner class holds input file data specific to the 
	 * Missionaries and Cannibals puzzle.
	 * 
	 * @author <a href="mailto:zanussi@cs.unm.edu">Michael Zanussi</a>
	 * @version 1.0 (29 Mar 2004) 
	 */
	private class _MCPuzzleData {
		
		// The heuristic name.
		private String _hName;
		
		// Holds the start and goal state array.
		//  Row 0 cols 0 and 1 hold the west bank cannibal and missionary count.
		//  Row 0 cols 0 and 1 hold the east bank cannibal and missionary count.
		//  Row 0 col 1 holds the boat location (1=west,0=east)
		private int[][] _startStateArray;
		private int[][] _goalStateArray;
		
		// The capacity of the boat.
		private int _boatCapacity;
		
		// The total number of cannibals and missionaries.
		private int _totalC;
		private int _totalM;
		
		// The start state.
		private MandC _start;
		
		// The goal state.
		private MandC _goal;
		
		/**
		 * No-arg constructor.
		 */
		public _MCPuzzleData() {
			
			_boatCapacity = 0;
			_goal = null;
			_goalStateArray = null;
			_start = null;
			_startStateArray = null;
			_totalC = 0;
			_totalM = 0;
			
		}
		
		/**
		 * Calculates the total number of missionaries and cannibals. 
		 */
		public void calcTotalMandC() {
			
			_totalC = _startStateArray[0][0] + _startStateArray[1][0];
			_totalM = _startStateArray[0][1] + _startStateArray[1][1];
			
		}
		
		/**
		 * Returns the goal state.
		 * 
		 * @return the goal state.
		 */
		public MandC getGoal() {
			
			return _goal;
			
		}
		
		/**
		 * Returns the start state.
		 * 
		 * @return the start state.
		 */
		public MandC getStart() {
			
			return _start;
			
		}

		/**
		 * Returns the total number of cannibals.
		 * 
		 * @return the total number of cannibals.
		 */
		public int getTotalC() {
			
			return _totalC;
			
		}
		
		/**
		 * Returns the total number of missionaries.
		 * 
		 * @return the total number of missionaries.
		 */
		public int getTotalM() {
			
			return _totalM;
		}
		
		/**
		 * Prints the completed path and other useful statistics.
		 */
		public void print( LinkedList path ) {
			
			// No solution.
			if( path == null ) {
				
				Global.output( "No solution." );
				
			}
			
			// Solution found!
			else {
				
				// Have we just started printing the path?
				boolean justStarted = true;

				// The previous cannibals, missionaries, and bank.
				String prevBank = null;
				int prevC = -1;
				int prevM = -1;

				// *DEBUG*
				String prevState = null;

				// The move counter.
				int move = 0;
				
				// Reverse the contents of the list then grab an iterator.
				Collections.reverse(path);
				Iterator it = path.iterator();
				
				while( it.hasNext() ) {
					
					// Get next move.
					MandC s = (MandC)it.next();
					
					// This shouldn't happen, but check anyhow.
					if( s == null) {
						break;
					}
					
					// Get the start state off the list and
					// store it in prev.
					if( justStarted ) {
						justStarted = false;
						prevBank = ( s.getBank() == MandC.WEST ? "West" : "East" );
						prevC = s.getC();
						prevM = s.getM();
						prevState = s.getLabel();
						continue;
					}

					// Increment the move counter.
					move++;
					
					// Calculate the current cannibal and missionary numbers.
					int currentC = Math.abs( prevC - s.getC() );
					int currentM = Math.abs( prevM - s.getM() );

					// Print the move sequence, if applicable.
					if( _moveSeq ) {
						Global.output( "MOVE " + move + ": " +
								"FERRY " + currentC + " Cannibals and " +
								currentM + " Missionaries FROM " + prevBank + " TO " + 
								( s.getBank() == MandC.WEST ? "West" : "East" ) );
					}
					
					// *DEBUG*
					if( Global.getDebug() ) {
						System.out.println( "*DEBUG* " + prevState + " -> " + s.getLabel() );
					}

					// Save the current state.
					prevBank = ( s.getBank() == MandC.WEST ? "West" : "East" );
					prevC = s.getC();
					prevM = s.getM();
					prevState = s.getLabel();
					
				}
				
				// Report the number of moves, if applicable.
				if( _solnPathLen ) {
					Global.log( "SolnPathLen: " + move );
				}
				
			}
			
		}
		
		/**
		 * Sets the boat capacity.
		 * 
		 * @param capacity the boat capacity.
		 */
		public void setBoatCapacity( int capacity ) { 
			
			_boatCapacity = capacity; 
			
		}

		/**
		 * Sets the goal state.
		 * 
		 * @param state the goal state.
		 */
		public void setGoal( int[][] state ) { 
			
			_goalStateArray = state; 
			
			if( _hName.equals( "MandCTrips" ) ) {
				_goal = new MandCTrips( state[0][0], state[0][1], state[2][0], null );
			}
			else if( _hName.equals( "MandCCount" ) ) {				
				_goal = new MandCCount( state[0][0], state[0][1], state[2][0], null );
			}
			
			_goal.setBoatCapacity( _boatCapacity );
			
		}
		
		/**
		 * Sets the heuristic name.
		 * 
		 * @param hName the heuristic name.
		 */
		public void setHName( String hName ) { 
			
			_hName = hName; 
			
		}
		
		/**
		 * Sets the start state.
		 * 
		 * @param state the start state.
		 */
		public void setStart( int[][] state ) { 
			
			_startStateArray = state; 

			if( _hName.equals( "MandCTrips" ) ) {
				_start = new MandCTrips( state[0][0], state[0][1], state[2][0], null );
			}
			else if( _hName.equals( "MandCCount" ) ) {				
				_start = new MandCCount( state[0][0], state[0][1], state[2][0], null );
			}
			
			_start.setBoatCapacity( _boatCapacity );
			
		}
				
	}
	
	/**
	 * This class holds input file data specific to the <code>GridManhattan</code> 
	 * puzzle.
	 * 
	 * @author <a href="mailto:zanussi@cs.unm.edu">Michael Zanussi</a>
	 * @version 1.0 (29 Mar 2004) 
	 */
	private class _GridPuzzleData {
		
		// The heuristic name.
		private String _hName;

		// The grid size (square).
		private int _gridSize;
		
		// The start and goal states.
		private Grid _start;
		private Grid _goal;
		
		// The obstacles table.
		private int[][] _obstacles;
		
		/**
		 * No-arg constructor.
		 */
		public _GridPuzzleData() {
			
			_gridSize = 0;
			_start = null;
			_goal = null;
			
		}
		
		/**
		 * Returns the goal state.
		 * 
		 * @return the goal state.
		 */
		public Grid getGoal() { 
			
			return _goal; 
			
		}
		
		/**
		 * Returns the obstacle table.
		 * 
		 * @return the obstacle table.
		 */
		public int[][] getObstacles() { 
			
			return _obstacles; 
			
		}
		
		/**
		 * Returns the size of the grid (square).
		 * 
		 * @return the size of the grid.
		 */
		public int getSize() { 
			
			return _gridSize; 
			
		}
		
		/**
		 * Returns the start state.
		 * 
		 * @return the start state.
		 */
		public Grid getStart() { 
			
			return _start; 
			
		}
		
		/**
		 * Prints the completed path.
		 */
		public void print( LinkedList path ) {
			
			// No solution.
			if( path == null ) {
				
				Global.output( "No solution." );
				
			}
			
			// Solution found!
			else {
				
				// Reverse the contents of the list then 
				// grab an iterator.
				Collections.reverse(path);
				Iterator it = path.iterator();
				
				// Just starting?
				boolean justStarted = true;
				
				// The previous cell visited.
				String prevCell = "";
				
				// The move counter.
				int move = 0;
				
				while( it.hasNext() ) {
					
					// Get next move.
					Grid s = (Grid)it.next();
					
					// This shouldn't happen, but check anyway.
					if( s == null) {
						break;
					}
					
					// Get the start state off the list and
					// store it in prev.
					if( justStarted ) {
						justStarted = false;
						prevCell = s.getLabel();
						continue;
					}
					
					// Increment the move counter.
					move++;
					
					// Print the move sequence, if applicable.
					if( _moveSeq ) {
						Global.output( "MOVE " + move + ": TRAVEL FROM " + prevCell + " TO " + s.getLabel() );
					}
					
					prevCell = s.getLabel();
					
				}
				
				// Report the number of moves, if applicable.
				if( _solnPathLen ) {
					Global.log( "SolnPathLen: " + move );
				}
				
			}
			
		}

		/**
		 * Sets the goal state.
		 * 
		 * @param state the start state.
		 * @throws IllegalArgumentException If grid coordinates exceed grid size.
		 */
		public void setGoal( Vector state ) throws IllegalArgumentException { 
			
			int x = ((Integer)state.get( 0 )).intValue();
			int y = ((Integer)state.get( 1 )).intValue();
			
			if( ( x > _gridSize - 1  ) || ( y > _gridSize - 1 ) ) {
				throw new IllegalArgumentException( "_GridPuzzleData.setGoal error: " +
						"X and/or Y coordinates exceed grid size. X = " + x + ", Y = "
						+ y + ", Grid size = " + _gridSize );
			}
			
			if( _hName.equals( "GridManhattan" ) ) {
				_goal = new GridManhattan( x, y, 1.0, null );
			}
			else if( _hName.equals( "GridStraight" ) ) {				
				_goal = new GridStraight( x, y, 1.0, null );
			}
			
			_goal.setTotalX( _gridSize );
			_goal.setTotalY( _gridSize );
			_goal.setObstacles( _obstacles );
			
		}
		
		/**
		 * Sets the heuristic name.
		 * 
		 * @param hname the heuristic name.
		 */
		public void setHName( String hname ) { 
			
			_hName = hname; 
			
		}
		
		/**
		 * Sets the obstacle table.
		 * 
		 * @param obstacles the obstacle table.
		 */
		public void setObstacles( int[][] obstacles ) { 
			
			_obstacles = obstacles; 
			
		}
		
		/**
		 * Sets the size of the grid (square).
		 * 
		 * @param size the size of the grid.
		 */
		public void setSize( int size ) {
			
			_gridSize = size;
			
		}
		
		/**
		 * Sets the start state.
		 * 
		 * @param state the start state.
		 * @throws IllegalArgumentException If grid coordinates exceed grid size.
		 */
		public void setStart( Vector state ) throws IllegalArgumentException { 
			
			int x = ((Integer)state.get( 0 )).intValue();
			int y = ((Integer)state.get( 1 )).intValue();
			
			if( ( x > _gridSize - 1  ) || ( y > _gridSize - 1 ) ) {
				throw new IllegalArgumentException( "_GridPuzzleData.setStart error: " +
						"X and/or Y coordinates exceed grid size. X = " + x + ", Y = "
						+ y + ", Grid size = " + _gridSize );
			}
			
			if( _hName.equals( "GridManhattan" ) ) {
				_start = new GridManhattan( x, y, 1.0, null );
			}
			else if( _hName.equals( "GridStraight" ) ) {				
				_start = new GridStraight( x, y, 1.0, null );
			}
			
			_start.setTotalX( _gridSize );
			_start.setTotalY( _gridSize );
			_start.setObstacles( _obstacles );
			
		}
		
				
	}
	
	/**
	 * This class holds input file data specific to the 
	 * <code>ShortestPaths</code> puzzle.
	 * 
	 * @author <a href="mailto:zanussi@cs.unm.edu">Michael Zanussi</a>
	 * @version 1.0 (29 Mar 2004) 
	 */
	private class _SPPuzzleData {
		
		// Given puzzle data, indexes to start state (BEGIN),
		// goal state (END), and cost (DISTANCE).
		private static final int BEGIN = 0;
		private static final int END = 1;
		private static final int DISTANCE = 2;
		
		// HashMap to hold puzzle data.
		private Map map;
		
		// The heuristic name.
		private String _hName;
		
		// The start and goal states.
		private String _start;
		private String _goal;
		
		// The city list.
		private Vector _cityList;
		
		// The city/distance list.
		private Vector _distList;
		
		/**
		 * No-arg constructor.
		 */
		public _SPPuzzleData() {
			
			map = new HashMap();
			
			_cityList = null;
			_distList = null;
			_hName = null;
			_goal = null;
			_start = null;
			
		}
		
		/**
		 * Loads data into master city map and sets each
		 * city with its appropriate child list.
		 */
		public void fillTable() {
			
			if( _hName.equals( "ShortestPathsNonMono" ) ) {
				
				for( int i = 0; i < _distList.size(); i++ ) {
					
					// Retrieve the city/distance pair.
					String[] data = (String[])_distList.get( i );
					// Retrieve the beginning city's list of destination cities.
					ShortestPathsNonMono bcl = (ShortestPathsNonMono)_getCityList( data[BEGIN] );
					// Retrieve the ending city's list of destination cities.
					ShortestPathsNonMono ecl = (ShortestPathsNonMono)_getCityList( data[END] );
					ecl.parent = bcl;
					
					// Setup link to end state.
					ShortestPathsNonMono endCity = new ShortestPathsNonMono( 
							data[END], 
							Double.parseDouble( data[DISTANCE] ), 
							bcl );
					bcl.children.add( endCity );
					
					// Setup link to start state.
					ShortestPathsNonMono beginCity = new ShortestPathsNonMono(
							data[BEGIN],
							Double.parseDouble( data[DISTANCE] ),
							ecl );
					ecl.children.add( beginCity );
					
				}
				
				// Now that table has been filled out, we can attach the
				// correct children lists to each city.
				Iterator it = map.entrySet().iterator();
				while( it.hasNext() ) {
					
					Map.Entry entry = (Map.Entry)it.next();
					ShortestPathsNonMono ll = (ShortestPathsNonMono)entry.getValue();
					Iterator llit = ll.children();
					while( llit.hasNext() ) {
						
						ShortestPathsNonMono sp = (ShortestPathsNonMono)llit.next();
						List cityList = ((ShortestPathsNonMono)map.get( sp.getLabel() )).children;
						sp.children = cityList;
						
					}
					
				}
			}
			
			else if( _hName.equals( "ShortestPathsMono" ) ) {
				
				for( int i = 0; i < _distList.size(); i++ ) {
					
					// Retrieve the city/distance pair.
					String[] data = (String[])_distList.get( i );
					// Retrieve the beginning city's list of destination cities.
					ShortestPathsMono bcl = (ShortestPathsMono)_getCityList( data[BEGIN] );
					// Retrieve the ending city's list of destination cities.
					ShortestPathsMono ecl = (ShortestPathsMono)_getCityList( data[END] );
					ecl.parent = bcl;
					
					// Setup link to end state.
					ShortestPathsMono endCity = new ShortestPathsMono( 
							data[END], 
							Double.parseDouble( data[DISTANCE] ), 
							bcl );
					bcl.children.add( endCity );
					
					// Setup link to start state.
					ShortestPathsMono beginCity = new ShortestPathsMono(
							data[BEGIN],
							Double.parseDouble( data[DISTANCE] ),
							ecl );
					ecl.children.add( beginCity );
					
				}

				
				// Now that table has been filled out, we can attach the
				// correct children lists to each city.
				Iterator it = map.entrySet().iterator();
				while( it.hasNext() ) {
					
					Map.Entry entry = (Map.Entry)it.next();
					ShortestPathsMono ll = (ShortestPathsMono)entry.getValue();
					Iterator llit = ll.children();
					while( llit.hasNext() ) {
						
						ShortestPathsMono sp = (ShortestPathsMono)llit.next();
						List cityList = ((ShortestPathsMono)map.get( sp.getLabel() )).children;
						sp.children = cityList;
						
					}
					
				}
			}
		}

		/**
		 * Returns the goal state.
		 * 
		 * @return the goal state.
		 */
		public String getGoal() { 
			
			return _goal; 
			
		}
		
		/**
		 * Returns the master city map.
		 * 
		 * @return the master city map.
		 */
		public Map getMap() { 
			
			return map; 
			
		}
		
		/**
		 * Returns the start state.
		 * 
		 * @return the start state.
		 */
		public String getStart() { 
			
			return _start; 
			
		}
		
		/**
		 * Prints the completed path and other useful statistics.
		 */
		public void print( LinkedList path ) {

			// No solution.
			if( path == null ) {
				
				Global.output( "No solution." );
				
			}
			
			// Solution found!
			else {
				
				// Is this the first city we've seen?
				boolean firstCity = true;
				
				// The name of the previous city we've visited.
				String prevCity = null;
				
				// *DEBUG* 
				double distance = 0.0;
				
				// The move counter.
				int move = 0;
				
				// Reverse the contents of the list then grab an iterator.
				Collections.reverse(path);
				Iterator it = path.iterator();
				
				while( it.hasNext() ) {
					
					// Get next move.
					ShortestPaths s = (ShortestPaths)it.next();
					
					// This shouldn't happen, but check anyhow.
					if( s == null) {
						break;
					}
					
					// Get the start state off the list and
					// store it in prev.
					if( firstCity ) {
						firstCity = false;
						prevCity = s.getLabel();
						continue;
					}
					
					// Increment the move number.
					move++;
					
					// Print the move sequence, if applicable.
					if( _moveSeq ) {
						Global.output( "MOVE " + move + ": TRAVEL FROM " + prevCity + 
								" TO " + s.getLabel() );
					}
					
					// *DEBUG*
					distance += s.getDistance();
					if( Global.getDebug() ) {
						System.out.println( "*DEBUG* " + prevCity + " -> " + s.getLabel() + 
								" (distance = " + s.getDistance() + ")" );
					}

					// Save this city name.
					prevCity = s.getLabel();

				}
				
				// *DEBUG*
				if( Global.getDebug() ) {
					System.out.println( "*DEBUG* Total distance traveled: " + distance );
				}
				
				// Report the number of moves, if applicable.
				if( _solnPathLen ) {
					Global.log( "SolnPathLen: " + move );
				}
				
			}
			
		}
		
		/**
		 * Set the city list.
		 * 
		 * @param cityList the city list.
		 */
		public void setCityList( Vector cityList ) { 
			
			_cityList = cityList; 
			
		}
		
		/**
		 * Set the city/distance pair list.
		 * 
		 * @param distList the city/distance pair list.
		 */
		public void setDistList( Vector distList ) { 
			
			_distList = distList; 
			
		}
		
		/**
		 * Set the goal state.
		 * 
		 * @param goal the goal state.
		 */
		public void setGoal( String goal ) { 
			
			_goal = goal; 
			
		}
		
		/**
		 * Set the heuristic name.
		 * 
		 * @param hname the heuristic name.
		 */
		public void setHName( String hname ) { 
			
			_hName = hname; 
			
		}

		/**
		 * Set the start state.
		 * 
		 * @param start the start state.
		 */
		public void setStart( String start ) { 
			
			_start = start; 
			
		}
		
		/**
		 * Helper function. Retrieves the children list for the
		 * requested city.
		 * 
		 * @param the city whose children list we desire.
		 * @return the children list.
		 */
		private PuzState _getCityList( String city ) {
			
			PuzState ps = (PuzState)map.get( city );
			
			if( ps == null ) {
				
				// City does not exist in hash table yet. Add it, 
				// then and a new linked list.
				
				if( _hName.equals( "ShortestPathsNonMono" ) ) {
					
					ShortestPathsNonMono sp = new ShortestPathsNonMono( city, 0.0, null );
					sp.children = new LinkedList();
					map.put( city, sp );
					
				}
				
				else if( _hName.equals( "ShortestPathsMono" ) ) {
					
					ShortestPathsMono sp = new ShortestPathsMono( city, 0.0, null );
					sp.children = new LinkedList();
					map.put( city, sp );
					
				}
				
				ps = (PuzState)map.get( city );
				
			}
			
			return ps;
			
		}
		
	}
	
	private _GridPuzzleData _parseGridPuzzle( Lexer lexer ) throws ParsingException {
		
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "Grid" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected 'Grid' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "(" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '(' but received '" + token.getToken() + "'." );
		}

		_hName = ParseHName.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( ")" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected ')' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "{" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '{' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "GridSize" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected 'Size' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		int gridSize = ParseInteger.parsePosInteger( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "StartCoordinates" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected 'StartCoordinates' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		Vector startCoord = ParseNumList.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "GoalCoordinates" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected 'GoalCoordinates' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		Vector goalCoord = ParseNumList.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "Obstacles" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected 'Obstacles' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		int[][] obstacles = ParseObstacles.parse( lexer, gridSize );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "}" ) ) {
			throw new ParsingException( "_parseGridPuzzle error: " +
					"Expected '}' but received '" + token.getToken() + "'." );
		}

		_GridPuzzleData data = new _GridPuzzleData();
		data.setHName( _hName );
		data.setSize( gridSize );
		data.setObstacles( obstacles );
		data.setStart( startCoord );
		data.setGoal( goalCoord );
		
		return data;
		
	}

	private _MCPuzzleData _parseMCPuzzle( Lexer lexer ) throws ParsingException {
		
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "MissionariesAndCannibals" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected 'MissionariesAndCannibals' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "(" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected '(' but received '" + token.getToken() + "'." );
		}

		_hName = ParseHName.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( ")" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected ')' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "{" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected '{' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "InitialState" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected 'InitialState' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		int[][] initialState = ParseMCState.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "GoalState" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected 'GoalState' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		int[][] goalState = ParseMCState.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "BoatCapacity" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected 'BoatCapacity' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		int capacity = ParseInteger.parsePosInteger( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "}" ) ) {
			throw new ParsingException( "_parseMCPuzzle error: " +
					"Expected '}' but received '" + token.getToken() + "'." );
		}

		_MCPuzzleData data = new _MCPuzzleData();
		data.setHName( _hName );
		data.setBoatCapacity( capacity );
		data.setStart( initialState );
		data.setGoal( goalState );
		data.calcTotalMandC();
		
		return data;
		
	}

	private void _parseN2KPuzzle( Lexer lexer ) throws ParsingException {
		
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "NToTheKPuzzle" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected 'NToTheKPuzzle' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "(" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected '(' but received '" + token.getToken() + "'." );
		}

		_hName = ParseHName.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( ")" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected ')' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "{" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected '{' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "StartState" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected 'StartState' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		Vector initialState = ParseNKPuzState.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "GoalState" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected 'GoalState' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		Vector goalState = ParseNKPuzState.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "}" ) ) {
			throw new ParsingException( "_parseN2KPuzzle error: " +
					"Expected '}' but received '" + token.getToken() + "'." );
		}

	}

	private _SPPuzzleData _parseSPPuzzle( Lexer lexer ) throws ParsingException {
		
		Token token = lexer.nextToken();
		if( !token.getToken().equals( "ShortestPaths" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected 'ShortestPaths' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "(" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '(' but received '" + token.getToken() + "'." );
		}

		_hName = ParseHName.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( ")" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected ')' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "{" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '{' but received '" + token.getToken() + "'." );
		}

		token = lexer.nextToken();
		if( !token.getToken().equals( "Cities" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected 'Cities' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		Vector cityList = ParseCityList.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "Distances" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected 'Distances' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		Vector distList = ParseDistList.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "StartCity" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected 'StartCity' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		String startState = ParseCityName.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "GoalCity" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected 'GoalCity' but received '" + token.getToken() + "'." );
		}
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "=" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '=' but received '" + token.getToken() + "'." );
		}

		String goalState = ParseCityName.parse( lexer );
		
		token = lexer.nextToken();
		if( !token.getToken().equals( "}" ) ) {
			throw new ParsingException( "_parseSPPuzzle error: " +
					"Expected '}' but received '" + token.getToken() + "'." );
		}

		_SPPuzzleData data = new _SPPuzzleData();
		data.setHName( _hName );
		data.setStart( startState );
		data.setGoal( goalState );
		data.setCityList( cityList );
		data.setDistList( distList );
		
		return data;
		
	}
	
	/**
	 * Reset puzzle data for another run.
	 */
	private void _reset() {
		
		_solnPathLen = false;
		_moveSeq = false;
		
		_nodesOpened = false;
		_openListMaxLen = false;
		_nodesClosed = false;
		_numReopened = false;
		
		Global.reset();
		
	}
	
	/**
	 * The puzzle driver.
	 * 
	 * @param args the single input file to the app.
	 */
	public static void main( String[] args ) {
		
		// Only one command line argument allowed (and required).
		if( args.length != 1 ) {
			System.out.println( "Usage: java PuzzleMuncher inputFile" );
			System.exit( 1 );
		}
		
		// The input file.
		File input = new File( args[0] );

		// Create and instantiate a PushbackReader object
		// and open up the input file.
		PushbackReader pr = new PushbackReader();
		pr.open( input );

		// Create a global object.
		Global g = new Global();

		// Create the lexer.
		Lexer lexer = new PuzzleLexer( pr );
		
		// Create a PuzzleMuncher object.
		PuzzleMuncher pm = new PuzzleMuncher( lexer );

		// Start the parse! (or try to)
		try {
long b = System.currentTimeMillis();
			pm.parse(); 
long e = System.currentTimeMillis();
System.out.println( e - b );
		}
		catch( ParsingException e ) {
			Global.error( e.getMessage() );
		}
		catch( UnsupportedOperationException e) {
			Global.error( e.getMessage() );
		}
		catch( IllegalArgumentException e ) {
			Global.error( e.getMessage() );
		}
		catch( NullPointerException e ) {
			Global.error( e.getMessage() );
		}
		catch( IndexOutOfBoundsException e ) {
			Global.error( e.getMessage() );
		}
		catch( IllegalStateException e ) {
			Global.error( e.getMessage() );
		}
		finally {
			// Close the reader.
			pr.close();
			// Close the error file.
			if( Global.getErrFile() != null ) {
				Global.getErrFile().close();
			}
			// Close the log file.
			if( Global.getLogFile() != null ) {
				Global.getLogFile().close();
			}
			// Close the output file.
			if( Global.getOutFile() != null ) {
				Global.getOutFile().close();
			}
		}

	}
	
}
