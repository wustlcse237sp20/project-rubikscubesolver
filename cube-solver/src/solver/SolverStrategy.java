package solver;

import cube.Algorithm;
import cube.Cube;

/**
 * Strategy object implemented by algorithms such as Min2Phase
 * @author Joe Frazier
 *
 */
public interface SolverStrategy {
	public Algorithm solveCube(Cube cubeToSolve);
}
