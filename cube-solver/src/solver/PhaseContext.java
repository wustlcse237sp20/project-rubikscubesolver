package solver;

import cube.Algorithm;
import cube.Cube;

/**
 * Phase Context utilized by Min2Phase
 * @author Joe Frazier
 *
 */
public class PhaseContext {
	private PhaseStrategy strategy;
	
	public  PhaseContext(PhaseStrategy strategy) {
		this.strategy = strategy;
	}
	
	public Algorithm solvePhase(Cube cubeToPhaseSolve) {
		return strategy.solvePhase(cubeToPhaseSolve);
	}
}
