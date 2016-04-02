package com.michaelzanussi.astar;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TestPuzzleMuncher {

	@Test
	public void testGridManhattan() {
		File input = new File("test/GridManhattan");
		PushbackReader pr = new PushbackReader();
		pr.open(input);
		new Global();
		Lexer lexer = new PuzzleLexer( pr );
		PuzzleMuncher pm = new PuzzleMuncher( lexer );
		try {
			pm.parse(); 
			String foo = Global.getData();
			assertEquals(foo,"\nHeuristic: GridManhattanMOVE 1: TRAVEL FROM 0,0 TO 1,0MOVE 2: TRAVEL FROM 1,0 TO 2,0MOVE 3: TRAVEL FROM 2,0 TO 2,1MOVE 4: TRAVEL FROM 2,1 TO 3,1MOVE 5: TRAVEL FROM 3,1 TO 4,1MOVE 6: TRAVEL FROM 4,1 TO 4,2MOVE 7: TRAVEL FROM 4,2 TO 4,3MOVE 8: TRAVEL FROM 4,3 TO 4,4SolnPathLen: 8NodesOpened: 19NumReopened: 0NodesClosed: 18OpenListMaxLen: 9OpenClosedRatio (min): 0.0OpenClosedRatio (max): 2.0");
			
		} catch (Exception e) {
			fail("exception thrown");
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

	@Test
	public void testMandCCount() {
		File input = new File("test/MandCCount");
		PushbackReader pr = new PushbackReader();
		pr.open(input);
		new Global();
		Lexer lexer = new PuzzleLexer( pr );
		PuzzleMuncher pm = new PuzzleMuncher( lexer );
		try {
			pm.parse(); 
			String foo = Global.getData();
			assertEquals(foo,"\nHeuristic: MandCCountMOVE 1: FERRY 0 Cannibals and 2 Missionaries FROM West TO EastMOVE 2: FERRY 0 Cannibals and 1 Missionaries FROM East TO WestMOVE 3: FERRY 0 Cannibals and 2 Missionaries FROM West TO EastMOVE 4: FERRY 0 Cannibals and 1 Missionaries FROM East TO WestMOVE 5: FERRY 2 Cannibals and 0 Missionaries FROM West TO EastMOVE 6: FERRY 1 Cannibals and 1 Missionaries FROM East TO WestMOVE 7: FERRY 2 Cannibals and 0 Missionaries FROM West TO EastMOVE 8: FERRY 0 Cannibals and 1 Missionaries FROM East TO WestMOVE 9: FERRY 0 Cannibals and 2 Missionaries FROM West TO EastMOVE 10: FERRY 0 Cannibals and 1 Missionaries FROM East TO WestMOVE 11: FERRY 0 Cannibals and 2 Missionaries FROM West TO EastSolnPathLen: 11NodesOpened: 15NumReopened: 0NodesClosed: 14OpenListMaxLen: 3OpenClosedRatio (min): 0.0OpenClosedRatio (max): 3.0");
			
		} catch (Exception e) {
			fail("exception thrown");
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
	
	@Test
	public void testMandCTrips() {
		File input = new File("test/MandCTrips");
		PushbackReader pr = new PushbackReader();
		pr.open(input);
		new Global();
		Lexer lexer = new PuzzleLexer( pr );
		PuzzleMuncher pm = new PuzzleMuncher( lexer );
		try {
			pm.parse(); 
			String foo = Global.getData();
			assertEquals(foo,"\nHeuristic: MandCTripsMOVE 1: FERRY 1 Cannibals and 1 Missionaries FROM West TO EastMOVE 2: FERRY 1 Cannibals and 0 Missionaries FROM East TO WestMOVE 3: FERRY 0 Cannibals and 2 Missionaries FROM West TO EastMOVE 4: FERRY 0 Cannibals and 1 Missionaries FROM East TO WestMOVE 5: FERRY 2 Cannibals and 0 Missionaries FROM West TO EastMOVE 6: FERRY 1 Cannibals and 1 Missionaries FROM East TO WestMOVE 7: FERRY 2 Cannibals and 0 Missionaries FROM West TO EastMOVE 8: FERRY 0 Cannibals and 1 Missionaries FROM East TO WestMOVE 9: FERRY 0 Cannibals and 2 Missionaries FROM West TO EastMOVE 10: FERRY 0 Cannibals and 1 Missionaries FROM East TO WestMOVE 11: FERRY 0 Cannibals and 2 Missionaries FROM West TO EastSolnPathLen: 11NodesOpened: 15NumReopened: 0NodesClosed: 14OpenListMaxLen: 3OpenClosedRatio (min): 0.0OpenClosedRatio (max): 3.0");
			
		} catch (Exception e) {
			fail("exception thrown");
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

	@Test
	public void testShortestPathsNonMono() {
		File input = new File("test/ShortestPathsNonMono");
		PushbackReader pr = new PushbackReader();
		pr.open(input);
		new Global();
		Lexer lexer = new PuzzleLexer( pr );
		PuzzleMuncher pm = new PuzzleMuncher( lexer );
		try {
			pm.parse(); 
			String foo = Global.getData();
			assertEquals(foo,"\nHeuristic: ShortestPathsNonMonoCurrentCity = AlbuquerqueCurrentCity = TijerasCurrentCity = BernalilloCurrentCity = TijerasCurrentCity = AlbuquerqueCurrentCity = BernalilloCurrentCity = MoriarityCurrentCity = BernalilloCurrentCity = AlbuquerqueCurrentCity = SantaFeCurrentCity = TijerasCurrentCity = MoriarityCurrentCity = TijerasCurrentCity = SantaFeMOVE 1: TRAVEL FROM Albuquerque TO BernalilloMOVE 2: TRAVEL FROM Bernalillo TO SantaFeSolnPathLen: 2NodesOpened: 5NumReopened: 0NodesClosed: 4OpenListMaxLen: 2OpenClosedRatio (min): 0.0OpenClosedRatio (max): 2.0");
			
		} catch (Exception e) {
			fail("exception thrown");
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

	@Test
	public void testShortestPathsMono() {
		File input = new File("test/ShortestPathsMono");
		PushbackReader pr = new PushbackReader();
		pr.open(input);
		new Global();
		Lexer lexer = new PuzzleLexer( pr );
		PuzzleMuncher pm = new PuzzleMuncher( lexer );
		try {
			pm.parse(); 
			String foo = Global.getData();
			assertEquals(foo,"\nHeuristic: ShortestPathsMonoCurrentCity = AlbuquerqueCurrentCity = TijerasCurrentCity = BernalilloCurrentCity = BernalilloCurrentCity = AlbuquerqueCurrentCity = SantaFeCurrentCity = TijerasCurrentCity = TijerasCurrentCity = AlbuquerqueCurrentCity = BernalilloCurrentCity = MoriarityCurrentCity = MoriarityCurrentCity = TijerasCurrentCity = SantaFeMOVE 1: TRAVEL FROM Albuquerque TO BernalilloMOVE 2: TRAVEL FROM Bernalillo TO SantaFeSolnPathLen: 2NodesOpened: 5NumReopened: 0NodesClosed: 4OpenListMaxLen: 2OpenClosedRatio (min): 0.0OpenClosedRatio (max): 2.0");
			
		} catch (Exception e) {
			fail("exception thrown");
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

	@Test
	public void testGridStraight() {
		File input = new File("test/GridStraight");
		PushbackReader pr = new PushbackReader();
		pr.open(input);
		new Global();
		Lexer lexer = new PuzzleLexer( pr );
		PuzzleMuncher pm = new PuzzleMuncher( lexer );
		try {
			pm.parse(); 
			String foo = Global.getData();
			assertEquals(foo,"\nHeuristic: GridStraightMOVE 1: TRAVEL FROM 0,0 TO 1,0MOVE 2: TRAVEL FROM 1,0 TO 2,0MOVE 3: TRAVEL FROM 2,0 TO 2,1MOVE 4: TRAVEL FROM 2,1 TO 3,1MOVE 5: TRAVEL FROM 3,1 TO 4,1MOVE 6: TRAVEL FROM 4,1 TO 4,2MOVE 7: TRAVEL FROM 4,2 TO 4,3MOVE 8: TRAVEL FROM 4,3 TO 4,4SolnPathLen: 8NodesOpened: 21NumReopened: 1NodesClosed: 19OpenListMaxLen: 8OpenClosedRatio (min): 0.0OpenClosedRatio (max): 2.0");
			
		} catch (Exception e) {
			fail("exception thrown");
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
