package solver;



import java.util.Random;

import cube.Algorithm;
import cube.Color;
import cube.Cube;
import cube.Facelet;
import cube.Move;

public class Min2Phase {
	String[] g0Moves = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		long startTime = System.nanoTime();
		
		for(int x = 1; x <= 100; ++x) {
			//Scramble Cube
			Cube cube = new Cube(3);
			Algorithm scramble = generateScramble(5);
			cube.applyAlgorithm(scramble);
			System.out.println(cube);
			Min2PhaseSolver(cube);
			System.out.println(x);
			long endTime = System.nanoTime();
			
			long elapsedTime = (endTime - startTime)/1000000000;
			
			System.out.println("TOTAL TIME FOR "+x+" 5 SCRAMBLE SOLVES: " + elapsedTime +" seconds \n" +"TIME PER SOLVE: " + elapsedTime/x + " seconds");
		}
		
		
		
		
		
	}
	
	public static void Min2PhaseSolver(Cube cube) {
//		System.out.println("BEFORE G1 \n" + cube);
		Cube temp = new Cube(cube);
	
		//Find G1 alg
		Algorithm toG1Alg = new Algorithm();
		int g1Face = isG1Cube(temp);
		if(g1Face == 6 ) {
			toG1Alg = permuteCube(temp);
		}
		
		
		//Print results
		temp.applyAlgorithm(toG1Alg);
//		System.out.println("After G1 \n"+  temp);
		g1Face = isG1Cube(temp);
//		System.out.println("Cube Is G1 On Face " + g1Face );
//		System.out.println(toG1Alg);
		
		Algorithm g1ToSolved= new Algorithm();
		if(!temp.isSolved()) {
			g1ToSolved = solveG1Cube(temp);
			temp.applyAlgorithm(g1ToSolved);
		}

//		System.out.println(temp);
//		System.out.println("G1 to Solved with: " + g1ToSolved);
		Algorithm whole = new Algorithm(toG1Alg);
		for (Move m : g1ToSolved.getMoveList()) {
			whole.addToMoveList(m);
		}
		System.out.println("Moves "+ whole);
		
	}
	
	public static Algorithm solveG1Cube(Cube cube) {
		Algorithm alg  = new Algorithm();
		Cube tempCube = new Cube(cube);
		Algorithm tempAlg = new Algorithm(g1Helper(tempCube, alg));
		tempCube.applyAlgorithm(tempAlg);
		boolean isSolved = tempCube.isSolved();
		while(tempAlg.getMoveList().size() > 12) {//We did not find a solution, reset and try again
			tempCube = new Cube(cube);
			alg = new Algorithm();
			tempAlg = new Algorithm(g1Helper(tempCube, alg));
			tempCube.applyAlgorithm(tempAlg);
			isSolved = tempCube.isSolved();
		}
		
		return tempAlg;
	}
	
	public static Algorithm g1Helper(Cube cube, Algorithm alg) {
		Algorithm tempAlg = new Algorithm(alg);
		Cube tempCube = new Cube(cube);

		Move randMove = generateG1Move(); //TODO
		tempAlg.addToMoveList(randMove);
		tempCube.rotate(randMove);
		if(tempAlg.getMoveList().size() > 12) { //If we are too deep, return
			return tempAlg;
		}
		else if(tempCube.isSolved()) { //If we found a solution, return the algorithm to get to G1
			return tempAlg;
		}
		else { //Call permute helper with the new algorithm and cube
			return g1Helper(tempCube, tempAlg);
		}
	}
	
//	public static Algorithm permuteMoves(Cube cube) {
//		String[] g0Moves = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};
//		
//		
//	}
	
	
	public static Algorithm permuteCube(Cube cube) {//
		Algorithm alg  = new Algorithm();
		Cube tempCube = new Cube(cube);
		
		
		Algorithm tempAlg = new Algorithm(permuteCubeHelper(tempCube, alg));
		tempCube.applyAlgorithm(tempAlg);
		int isG1 = isG1Cube(tempCube);
		int count = 0;
		while(tempAlg.getMoveList().size() > 18 || isG1 == 6 ) {//We did not find a solution, reset and try again
			if(count%1000000 == 0) {
				System.out.println(count);
			}
			tempCube = new Cube(cube);
			alg = new Algorithm();
			tempAlg = new Algorithm(permuteCubeHelper(tempCube, alg));
			tempCube.applyAlgorithm(tempAlg);
			isG1 = isG1Cube(tempCube);
			++count;
		} 
//		System.out.println("ALG SIZE " + tempAlg.getMoveList().size());
		//We found a solution (tempAlg <13)
		
		return tempAlg;
	}
	
	public static Algorithm permuteCubeHelper(Cube cube, Algorithm alg) {//take curret cube an alg
		Algorithm tempAlg = new Algorithm(alg);
		Cube tempCube = new Cube(cube);
	
		
		Move randMove = generateMove();
		tempAlg.addToMoveList(randMove);
		tempCube.rotate(randMove);
		if(tempAlg.getMoveList().size() > 18) { //If we are too deep, return
			return tempAlg;
		}
		else if(isG1Cube(tempCube) < 6 ) { //If we found a solution, return the algorithm to get to G1
			return tempAlg;
		}
		else { //Call permute helper with the new algorithm and cube
			return permuteCubeHelper(tempCube, tempAlg);
		}
	}
	
	
	public static Move generateMove() {
		Random random = new Random();
		int ran = random.nextInt(18);
		String[] g0Moves = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};
		return new Move(g0Moves[ran]);
	}
	
	public static Move generateG1Move() {
		Random random = new Random();
		int ran = random.nextInt(10);
		String[] g1Moves = {"F2 ", "B2 ", "L2 ", "R2 ", "U ", "U2 ", "D ", "D2 ", "U\' ", "D\' "};
		return new Move(g1Moves[ran]);
		
	}
	
	public static Algorithm generateScramble(int numMoves) {
		Algorithm alg = new Algorithm();
		for(int x =0; x < numMoves; ++x) {
			alg.addToMoveList(generateMove());
		}
		return alg;
	}
	
	/**
	 * Tests whether the copy constructor makes a deep or shallow copy
	 * @param alg
	 */
	public static void algTest(Algorithm alg) {
		System.out.println("----------------ALG TEST_-------------");
		
		Algorithm temp = new Algorithm(alg);
		System.out.println("Before "+ alg + " | " + temp);
		temp.addToMoveList(new Move("U"));
		System.out.println("After "+ alg + " | " + temp);
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
