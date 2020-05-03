package solver;

import cube.Algorithm;
import cube.Cube;

/**
 * Strategy object implemented by Min2Phase phases (Phase0Solver and Phase1Solver)
 * @author Joe Frazier
 *
 */
public interface PhaseStrategy {
	public Algorithm solvePhase(Cube cubeToPhaseSolve);
}
