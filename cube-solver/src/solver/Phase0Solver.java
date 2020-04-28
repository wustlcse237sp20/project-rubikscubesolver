package solver;

import java.util.LinkedList;
import java.util.List;

import cube.Algorithm;
import cube.Cube;

/**
 * Class defining the 0 phase of the modified Min2Phase algorithm
 * @author Joe Frazier
 *
 */
public class Phase0Solver implements PhaseStrategy {
	
	/**
	 * @return Algorithm used to convert the cube from a G0 state to G1 state
	 * @param cubeToPhaseSolve
	 */
	@Override
	public Algorithm solvePhase(Cube cubeToPhaseSolve) {
		Algorithm phase0Solution = new Algorithm();
		
		Cube tempCube = new Cube (cubeToPhaseSolve);
		int maxDepth = 13;
		
		while(!phaseSolutionFound(tempCube)) {
			for(int depth = 1; depth <= maxDepth; ++depth) {
				int numAlgsPerDepth = (int) Math.pow(maxDepth, depth);
				for(int x = 0; x <= numAlgsPerDepth; ++x) {
					phase0Solution = Min2PhaseUtil.simpleScramble(depth, tempCube);
					if(phaseSolutionFound(tempCube)) {
						cubeToPhaseSolve.applyAlgorithm(phase0Solution);
						return phase0Solution;
					}
					else {
						tempCube = new Cube(cubeToPhaseSolve);
					}
				}
			}
		}
		return phase0Solution;
	}
	
	
	/**
	 * 
	 * @param Cube toCheck 
	 * @return boolean indicating if a phase 0 solution has been found
	 */
	private static boolean phaseSolutionFound(Cube toCheck) {
		if(Min2PhaseUtil.findG1Face(toCheck) != 6) {
			return true;
		}
		return false;
	}
}
