package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cube.Algorithm;
import cube.Cube;
import cube.Move;
import solver.Min2Phase;
import solver.Min2PhaseUtil;
import solver.Phase0Solver;
import solver.Phase1Solver;
import solver.PhaseContext;
import solver.SolverContext;

/**
 * Class to test the Min2PhaseAlgorithm
 * @author Joe Frazier
 *
 */
public class Min2PhaseTestSuite {
	Cube solvedCube;
	Move u = new Move("U");
	Move d = new Move("D");
	Move f = new Move("F");
	Move b = new Move("B");
	Move l = new Move("L");
	Move r = new Move("R");
	List<Move> moves = new LinkedList<Move>();
	
	@BeforeEach
	void setup() {
		moves.add(u);
		moves.add(d);
		moves.add(f);
		moves.add(b);
		moves.add(l);
		moves.add(r);
	}
	
	
	@Test 
	void min2PhaseTest(){
			Cube testCube1 = new Cube(3);
			Cube testCube2 = new Cube(3);
			Cube testCube3 = new Cube(3);
			Cube testCube4 = new Cube(3);
			Cube testCube5 = new Cube(3);
			simpleScrambleMin2PhaseSolve(1, testCube1);
			simpleScrambleMin2PhaseSolve(2, testCube2);
			simpleScrambleMin2PhaseSolve(3, testCube3);
			simpleScrambleMin2PhaseSolve(4, testCube4);
			simpleScrambleMin2PhaseSolve(5, testCube5);
			assertTrue(testCube1.isSolved());
			assertTrue(testCube2.isSolved());
			assertTrue(testCube3.isSolved());
			assertTrue(testCube4.isSolved());
			assertTrue(testCube5.isSolved());
	}
	
	
	/**
	 * Scrambles a cube and solves it with the min 2 phase algorithm
	 * @param scrambleLength length to scramble
	 * @param testCube
	 */
	private void simpleScrambleMin2PhaseSolve(int scrambleLength, Cube testCube) {
		//scramble
		Min2PhaseUtil.simpleScramble(scrambleLength, testCube);
		SolverContext min2Phase = new SolverContext(new Min2Phase());
		//solve
		min2Phase.solveCube(testCube);
	}

	
	@Test
	void findG1FaceReturnsInvalidFace() {
		Cube testCube = new Cube(3);
		//Apply subset of moves not common to any g1 state
		testCube.rotate(u);
		testCube.rotate(l);
		testCube.rotate(b);
		assertTrue(Min2PhaseUtil.findG1Face(testCube) == 6);
	}
	
	
	@Test
	void findG1FaceReturnsCorrectFace(){
		Cube testCubeTop = new Cube(3);
		Cube testCubeSide = new Cube(3);
		Cube testCubeFront = new Cube(3);
		Move[] g1OnTopMoves = Min2PhaseUtil.getG1Moves(0);
		Move[] g1OnSideMoves = Min2PhaseUtil.getG1Moves(1);
		Move[] g1OnFrontMoves = Min2PhaseUtil.getG1Moves(2);
		findG1FaceReturnsCorrectFaceHelper(g1OnTopMoves, testCubeTop);
		findG1FaceReturnsCorrectFaceHelper(g1OnSideMoves, testCubeSide);
		findG1FaceReturnsCorrectFaceHelper(g1OnFrontMoves, testCubeFront);
		assertTrue(Min2PhaseUtil.findG1Face(testCubeTop) == 0);
		assertTrue(Min2PhaseUtil.findG1Face(testCubeSide) == 1);
		assertTrue(Min2PhaseUtil.findG1Face(testCubeFront) == 2);
	}
	
	/**
	 * Iterates array of moves, applies moves to cube and stops when the inverse moves are reached
	 * @param g1Moves to apply a subset of
	 * @param testCube to apply moves to
	 */
	void findG1FaceReturnsCorrectFaceHelper(Move[] g1Moves, Cube testCube) {
		int count =0;
		for(Move m: g1Moves) {
			if(count == 6) {
				break;
			}
			testCube.rotate(m);
			++count;
		}
	}
	
	
	@Test
	void phase0SolverTest(){
		PhaseContext phaseContext = new PhaseContext(new Phase0Solver());
		for(int x = 0; x <= 0; ++x) {
			Cube testCube = new Cube(3);
			Min2PhaseUtil.simpleScramble(5, testCube);
			phaseContext.solvePhase(testCube);
			assertTrue(Min2PhaseUtil.findG1Face(testCube) != 6);
			phase1SolverTest(testCube);
		}	
	}
	
	void phase1SolverTest(Cube testCube) {
		Cube cubeCopy = new Cube(testCube);
		PhaseContext phase1Context = new PhaseContext(new Phase1Solver());
		System.out.println(testCube);
		Algorithm solution = phase1Context.solvePhase(cubeCopy);
		testCube.applyAlgorithm(solution);
		assertTrue(testCube.isSolved()==true);	
	}
	
	
	
}
	
	

