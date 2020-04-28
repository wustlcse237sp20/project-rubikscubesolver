package solver;

import java.util.*;

import cube.*;

/**
 * Class to test algorithms
 * @author Brad Hodkinson
 */
public class Solver {

	public static void main(String[] args) {
		
		Cube cube = new Cube(3);
		SolverContext context = new SolverContext(new Min2Phase());
		
		System.out.println(cube);
		
		Min2PhaseUtil.simpleScramble(5, cube);
		
		
		System.out.println(cube);
		
		Algorithm solution = context.solveCube(cube);
		
		System.out.println(cube);
		System.out.println(solution.getMoveList());
		
	}
	
}
