package solver;

/**
 * Class to solve a NxNxN cube
 * @author Brad Hodkinson
 */
import java.util.*;

import cube.Algorithm;
import cube.*;

public class Solver {

	public static void main(String[] args) {
		
//		Cube cube = new Cube(2);
//		System.out.println(cube);
		
		Cube testCube = new Cube(3);
		
		System.out.println(testCube);
		
		Move move = new Move("L\'");
		testCube.rotate(move);
//		Move move = new Move("D");
//		testCube.rotate(move);
		System.out.println(testCube);

//		String moves = "U\' L D\' R2 D2 R B D\' U\' F2 R U2 B2 D2 F2 U F\' U\' F2 R\' F2 R L F\' U";
		
//		Algorithm test =  new Algorithm();
//		test.generateScramble(2, 20);
//		System.out.println(test);
		
//		Move move = new Move("R'");
//		System.out.println(move);
//		System.out.println(move.counterClockwise);
//		System.out.println(move.doubleRotation);
	}

}
