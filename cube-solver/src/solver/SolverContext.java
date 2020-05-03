package solver;

import cube.Algorithm;
import cube.Cube;

/**
 * Context used to apply algorithms to solve cubes
 * @author Joe Frazier
 *
 */
public class SolverContext {
	private SolverStrategy strategy;
	
	public SolverContext(SolverStrategy strategy) {
		this.strategy = strategy;
	}
	
	public Algorithm solveCube(Cube cube) {
		return strategy.solveCube(cube);
	}
}
