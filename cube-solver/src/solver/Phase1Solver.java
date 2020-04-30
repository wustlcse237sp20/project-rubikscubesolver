package solver;

import java.util.Random;

import cube.Algorithm;
import cube.Cube;
import cube.Move;

/**
 * Class defining the 1 phase of the modified Min2Phase algorithm
 * @author Joe Frazier
 *
 */
public class Phase1Solver implements PhaseStrategy {
	private static Move[] moves;
	
	/**
	 * @return Algorithm used to convert the cube from a G1 state to the solved state
	 * @param cubeToPhaseSolve
	 */
	@Override
	public Algorithm solvePhase(Cube cubeToPhaseSolve) {
		Algorithm phase1Solution = new Algorithm();
		int maxDepth = 18;
		
		Cube tempCube = new Cube(cubeToPhaseSolve);
		int g1Face = Min2PhaseUtil.findG1Face(cubeToPhaseSolve);
		moves = Min2PhaseUtil.getG1Moves(g1Face);
		
		while(!tempCube.isSolved()) {
			for(int depth = 1; depth <= maxDepth; ++depth) {
				int numAlgsPerDepth = (int) Math.pow(maxDepth, depth);
				for(int x = 0; x <= numAlgsPerDepth; ++x) {
					phase1Solution = randPhase1Alg(depth, tempCube);
					if(tempCube.isSolved()) {
						cubeToPhaseSolve.applyAlgorithm(phase1Solution);
						return phase1Solution;
					}
					else {
						tempCube = new Cube(cubeToPhaseSolve);
					}
				}
			}
		}
		return phase1Solution;
	};
	
	/**
	 * Creates a Algorithm of random moves
	 * @param numMoves that the algorithm should be
	 * @param cube to apply the algorithm to 
	 * @return Algorithm created
	 */
	public static Algorithm randPhase1Alg(int numMoves, Cube cube) {
		Algorithm alg = new Algorithm();
		for(int x = 0; x < numMoves; ++x) {
			alg.addToMoveList(getG1Move());
		}
		cube.applyAlgorithm(alg);
		return alg;
	}
	
	public static Move getG1Move() {
		Random random = new Random();
		int randomInt = random.nextInt(moves.length);
		return new Move(moves[randomInt].toString());
	}
	
}
