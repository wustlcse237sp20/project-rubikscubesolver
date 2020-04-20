package solver;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cube.Algorithm;
import cube.Color;
import cube.Cube;
import cube.Facelet;
import cube.Move;

public class Min2PhaseV2 {

	static String[] g0MovesString = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};
	static String[] g1MovesString = {"F2 ", "B2 ", "L2 ", "R2 ", "U ", "U2 ", "D ", "D2 ", "U\' ", "D\' "};

	
	public static void main(String[] args) {
		Cube cube = new Cube(3);
		Algorithm scramble = generateSimpleScramble(2);
		cube.applyAlgorithm(scramble);
		System.out.println("SCRAMBLED WITH: "+ scramble +"\n" + cube); //Print the scrambled cube
		System.out.println("SOLVED WITH: " + solveCube(cube)); //Get and print the solution
		System.out.println("SOLVED CUBE \n" + cube); //Print the solved cube
		System.out.println("G1 FACE: " + isG1Cube(cube) );
		
	}
	
	/**
	 * Solves the cube and returns the moves that solved it
	 * @param cube
	 * @return Algorithm containing moves to solve the cube
	 */
	public static Algorithm solveCube(Cube cube) {
		//Get cube in g1
		Cube tempCube = new Cube(cube);
		Algorithm phase0Alg = new Algorithm();
		if(isG1Cube(tempCube) == 6) { //if not a g1 cube
			phase0Alg = phase0(tempCube);
		}
		tempCube.applyAlgorithm(phase0Alg);
		
		//Solve the g1 cube
//		Algorithm phase1Alg = new Algorithm();
		/*if(!tempCube.isSolved()) { //TODO PHASE 1
			phase1Alg = phase1(tempCube);
		}
		*/
		List<Move> solutionList = new LinkedList<Move>();
		solutionList.addAll(phase0Alg.getMoveList());
//		solutionList.addAll(phase1Alg.getMoveList());
		Algorithm solution = new Algorithm(solutionList);
		cube.applyAlgorithm(solution);
		
		return solution;
//		return phase0Alg;
		
	}
	
	public static Algorithm phase0(Cube cube) { 
		int count = 0;
		Cube tempCube = new Cube(cube);
		Algorithm emptyAlg = new Algorithm();
		
		Algorithm phase0Solution = phase0Helper(tempCube, emptyAlg);
		int size = phase0Solution.getMoveList().size();
		int g1Face = isG1Cube(tempCube);
		while(size == 0 || g1Face == 6 ) {
			tempCube = new Cube(cube);
			emptyAlg = new Algorithm();
			phase0Solution = phase0Helper(tempCube, emptyAlg);
			size = phase0Solution.getMoveList().size();
			g1Face = isG1Cube(tempCube);
		}
		return phase0Solution;
	}
	
	public static Algorithm phase0Helper(Cube cube, Algorithm alg) {
		
		Cube tempCube = new Cube(cube);
		if(alg.getMoveList().size() >= 3) {//Too large, 
			return alg;
		}
		else if(isG1Cube(tempCube) != 6) { //in g1 state -> Return the alg that got it here
			return alg;
		}
		else { // Keep searching for a solution
				Move m = generateMove();
				Algorithm tempAlg = new Algorithm(alg);
				tempAlg.addToMoveList(m);
				tempCube.rotate(m);
				Algorithm result = phase0Helper(tempCube, tempAlg);
				if(!(result.getMoveList().size() >= 18)) {
					return result;
				}
		}
		return new Algorithm();
	}
	
	
	
	public static Move generateMove() {
		Random random = new Random();
		int ran = random.nextInt(18);
		String[] g0Moves = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};
		return new Move(g0Moves[ran]);
	}
	
	
	public static Algorithm generateSimpleScramble(int numMoves) {
		Algorithm alg = new Algorithm();
		for(int x =0; x < numMoves; ++x) {
			alg.addToMoveList(generateMove());
		}
		return alg;
	}
	
	
	/*
	 * Checks if the cube is in G1 = <U,D,R2,L2,F2,B2>.
	 * @Returns the integer of the face where that face and its opposing face only contain their own and the other's facelets
	 * or it returns an out of range index (6) if the cube is not in the G1 state
	 */
	public static int isG1Cube(Cube cube) {
		//is G1 if 2 opposing sides 
		Facelet[][] facelets = cube.getFacelets();
		for(int faceInt = 0; faceInt <=2; ++faceInt) {
			Color currentFaceColor = facelets[faceInt][4].getColor();
			Color equivFaceColor = facelets[faceInt + 3][4].getColor();
			for(int faceletInt = 0; faceletInt < 9; ++faceletInt) {
				Color faceletColor = facelets[faceInt][faceletInt].getColor();
				Color oppositeFaceletColor = facelets[faceInt+3][faceletInt].getColor();
				if(faceletColor.equals(currentFaceColor) ||  faceletColor.equals(equivFaceColor)) {
					if(oppositeFaceletColor.equals(currentFaceColor) ||  oppositeFaceletColor.equals(equivFaceColor)) {
						if(faceletInt == 8) {
							return faceInt;
						}
					}
					else {
						return 6;
					}
				}
				else {
					return 6;
				}
			}
			
			
		}
		
		
		return 6;
		
	}

}
