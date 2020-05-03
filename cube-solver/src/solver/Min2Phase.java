package solver;

import cube.Algorithm;
import cube.Cube;
import cube.Move;

/**
 * Class Implementing a Modified version of the Min2PhaseAlogrithm 
 * @author Joe Frazier
 *
 */
public class Min2Phase implements SolverStrategy {
	
	
	/**
	 * Solves a cube by converting it from a G0 state into a G1 state and finally into the solved state 
	 * "http://kociemba.org/cube.htm" 
	 * @return The algorithm containing the moves performed on the cube
	 */
	@Override
	public Algorithm solveCube(Cube cubeToSolve) {
		Algorithm solution = new Algorithm();
		PhaseContext context0 = new PhaseContext(new Phase0Solver());
		PhaseContext context1 = new PhaseContext(new Phase1Solver());
		
		Algorithm phase0Solution = context0.solvePhase(cubeToSolve);
		Algorithm phase1Solution = context1.solvePhase(cubeToSolve);
		addAlgorithms(solution, phase0Solution);
		addAlgorithms(solution, phase1Solution);
		return solution;	
	}
	
	
	/**
	 *	Adds toAdd's move list to original's move list
	 */
	private static void addAlgorithms(Algorithm original, Algorithm toAdd) {
		for(Move m : toAdd.getMoveList()) {
			original.addToMoveList(m);
		}
	}
}
