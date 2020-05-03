package solver;

import java.util.Random;

import cube.*;

/**
 * Class defines helper methods for the Min2Phase Algorithm
 * @author Joe Frazier
 */
public class Min2PhaseUtil {
	
	
	/**
	 * Finds the face that meets the G1 phase criteria
	 * @param cube current cube
	 * @return integer representing the face meeting the G1 criteria or a 6 to indicate no face meets the G1 criteria
	 */
	public static int findG1Face(Cube cube) {
		int cubeSize = cube.getSize();
		int faceletsInFace = cubeSize * cubeSize;
		int staticFacelet = 4;
		int notG1Cube = 6;
		Facelet[][] facelets = cube.getFacelets();
		for(int faceInt = 0; faceInt < cubeSize; ++faceInt) {
			CubeColor thisFace = facelets[faceInt][staticFacelet].getColor();
			CubeColor oppositeFace = facelets[faceInt + 3][staticFacelet].getColor();
			for(int faceletInt = 0; faceletInt < faceletsInFace; ++faceletInt) {
				CubeColor facelet = facelets[faceInt][faceletInt].getColor();
				CubeColor oppositeFacelet = facelets[faceInt+3][faceletInt].getColor();
				if(facelet.equals(thisFace) ||  facelet.equals(oppositeFace)) {
					if(oppositeFacelet.equals(thisFace) || oppositeFacelet.equals(oppositeFace)) {
						if(faceletInt == faceletsInFace - 1) {
							return faceInt;
						}
					}
					else break;
				}
				else break;
			}
		}
		return notG1Cube;
	}
	
	
	/**
	 * Applies a random set of rotations to a cube
	 * @param numMoves to perform
	 * @param cube to perform moves on
	 */
	public static Algorithm simpleScramble(int numMoves, Cube cube) {
		Algorithm alg = new Algorithm();
		for(int x = 0; x < numMoves; ++x) {
			alg.addToMoveList(getSimpleMove());
		}
		cube.applyAlgorithm(alg);
		return alg;
	}
	

	public static Move getSimpleMove() {
		Random random = new Random();
		String[] moves = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};
		int randomInt = random.nextInt(moves.length);
		return new Move(moves[randomInt]);
	}
	
	
	/**
	 * @return an array of moves to be used in the g0 phase
	 */
	public static Move[] getG0Moves() {
		String[] g0MovesString = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};
		return buildMoveArray(g0MovesString);
	}
	
	
	/**
	 * 
	 * @param g1Face the integer representing the a face meeting the g1 criteria
	 * @return 
	 */
	public static Move[] getG1Moves(int g1Face) {
		String[] g1MovesString = new String[8];
		if(g1Face == 0) {
			g1MovesString = new String[] {"F2 ", "B2 ", "L2 ", "R2 ", "U ", "D ", "U\' ", "D\' "};
			return buildMoveArray(g1MovesString);
		}
		else if(g1Face ==1) {
			g1MovesString = new String[] {"F2 ", "B2 ", "L ", "R ", "U2 ", "D2 ", "L\' ", "R\' " };
			return buildMoveArray(g1MovesString);
		}
		else {
			g1MovesString = new String[] {"F ", "B ", "L2 ", "R2 ", "U2 ", "D2 ", "F\' ", "B\' "};
			return buildMoveArray(g1MovesString);
		}
	}
	
	
	/**
	 * 
	 * @param movesString array of strings that moves should be created from
	 * @return An array of move objects 
	 */
	public static Move[] buildMoveArray(String[] movesString) {
		Move[] moves = new Move[movesString.length];
		for(int x = 0; x < movesString.length; ++x) {
			moves[x] = new Move(movesString[x]);
		}
		return moves;
	}
}
