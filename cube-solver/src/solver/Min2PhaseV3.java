package solver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import cube.Algorithm;
import cube.CubeColor;
import cube.Cube;
import cube.Facelet;
import cube.Move;

/**
 * Class to solve a cube using a variation of min 2 phase
 * Efficiently solves cubes of a max basic sramble of about 10 moves
 * Searches for solutions with a max length of 30 moves 
 * About 10 min for a 10 move scramble solution
 * 
 * @author Joe Frazier
 */

public class Min2PhaseV3 {
	static List<Algorithm> algList = new LinkedList<Algorithm>();
	static List<Algorithm> lastLevel = new LinkedList<Algorithm>();
	static List<Algorithm> backUpList = new LinkedList<Algorithm>();
	static Move[] g0Moves = new Move[18];
	static Move[] g1Moves = new Move[8];
	static ListIterator<Algorithm> currentAlg = algList.listIterator();
	
	static int currentDepth = 0;
	
	public static void main(String[] args) throws IOException {
		FileWriter myWriter = null;
		try {
			myWriter = new FileWriter("./results.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int scrambleLength = 1; scrambleLength <= 5 ; ++scrambleLength) {
			long startTime = System.nanoTime();
			for(int x =1; x <= 10; ++x) {
				Cube cube = new Cube(3);
				Algorithm scramble = generateSimpleScramble(scrambleLength);
				cube.applyAlgorithm(scramble);
				System.out.println("Scrambled with: "+ scramble); 
				System.out.println(cube);
				myWriter.write("Scrambled with: "+ scramble + "\n");
				myWriter.write("Solved with: "+solver(cube) + "\n");
				long endTime = System.nanoTime();
				long elapsedTime = (endTime - startTime)/1000000000;
				System.out.println("TOTAL TIME FOR "+ x +" solves of length "+scrambleLength+" SCRAMBLE: " + elapsedTime +" seconds \n" +"TIME PER SOLVE: " + elapsedTime/x + " seconds");
				myWriter.write("TOTAL TIME FOR "+ x +" solves of length "+scrambleLength+" SCRAMBLE: " + elapsedTime +" seconds \n" +"TIME PER SOLVE: " + elapsedTime/x + " seconds");
			}
			myWriter.write("-------------------------------------------------------- \n");
		}
		myWriter.close();
	}
	
	/**
	 * Solves cubes by generating random moves until the cube becomes a is <U, D, F2, L2, B2, R2> cube then applies a subset of the total moves to solve the cube
	 * @param cube to solve
	 * @return algorithm solving the cube
	 */
	public static Algorithm solver(Cube cube) {
		//Setup
		buildgG0MoveArray();
		boolean isInPhase0 = (isG1Cube(cube) == 6);
		
		Algorithm phase0Alg = new Algorithm();
		Cube phase0Cube = new Cube (cube);
		
		buildFirstLevel(isInPhase0);
		
		System.out.println("Phase0: "+isInPhase0);
		
		while(isInPhase0) {
			if(!currentAlg.hasNext()) {
				if(currentDepth == 12) {
					buildFirstLevel(isInPhase0);
				}
				buildNextLevel(isInPhase0);
			}
			
			phase0Alg = new Algorithm(currentAlg.next());
			lastLevel.add(phase0Alg);
			phase0Cube = new Cube(cube);
			phase0Cube.applyAlgorithm(phase0Alg);
			isInPhase0 = (isG1Cube(phase0Cube) == 6);
		}
		int g1OnFace = isG1Cube(phase0Cube);
		System.out.println("G1 Cube on face "+ g1OnFace);
		System.out.println("Phase1");
		isInPhase0 = false;
		boolean isInPhase1 = !phase0Cube.isSolved();
		Cube phase1Cube = new Cube(phase0Cube);
		buildG1MoveArray(g1OnFace);
		buildFirstLevel(isInPhase0);
		Algorithm phase1Alg = new Algorithm();
		
		while(isInPhase1) {
			if(!currentAlg.hasNext()) {
				if(currentDepth == 18) {
					buildFirstLevel(isInPhase0);
				}
				buildNextLevel(isInPhase0);
			}
			
			phase1Alg = new Algorithm(currentAlg.next());
			lastLevel.add(phase1Alg);
			
			phase1Cube = new Cube(phase0Cube);
			phase1Cube.applyAlgorithm(phase1Alg);
			isInPhase1 = !phase1Cube.isSolved();
		}
		List<Move> solutionList = new LinkedList<Move>();
		solutionList.addAll(phase0Alg.getMoveList());
		solutionList.addAll(phase1Alg.getMoveList());
		Algorithm solution = new Algorithm(solutionList);
		System.out.println(phase1Cube);
		System.out.println("Solved with " + solution);
		return solution;
	}
	
	
	/**
	 * Adds a layer of random moves to the existing algorithms
	 * @param isInPhase0
	 */
	public static void buildNextLevel(boolean isInPhase0) {
		++currentDepth;
		
		Move[] moveArray = isInPhase0 ? g0Moves : g1Moves;
		System.out.println("Building level: " + currentDepth);
			Random random = new Random();
			int moveArraySize = moveArray.length;
			for (Algorithm a : algList) {
				Move temp = moveArray[random.nextInt(moveArraySize)];
				a.addToMoveList(temp);
		}
		lastLevel = new LinkedList<Algorithm>();
		currentAlg = algList.listIterator();
	}
	
	public static void buildFirstLevel(boolean isInPhase0) {
		algList = new LinkedList<Algorithm>();
		lastLevel = new LinkedList<Algorithm>();
		backUpList = new LinkedList<Algorithm>();
		currentDepth =0;
		Move[] moveArray = isInPhase0 ? g0Moves : g1Moves;
		for(int x = 0; x <= 100000; ++x) {
		for(Move m : moveArray) {
			Algorithm mAlg = new Algorithm();
			mAlg.addToMoveList(m);
			algList.add(mAlg);
		}
		}
		currentAlg = algList.listIterator();
	}
	
	public static void buildgG0MoveArray() {
		g0Moves = new Move[18];
//		String[] g0MovesStr = {"F ", "F\' ", "B ", "B\' ", "L ", "L\' ", "R ", "U ", "D ", "R\' ", "U\' ", "D\' "};
		String[] g0MovesStr = {"F ", "F\' ", "F2 ", "B ", "B\' ", "B2 ", "L ", "L\' ", "L2 ", "R ", "R2 ", "U ", "U2 ", "D ", "D2 ", "R\' ", "U\' ", "D\' "};
		for(int x = 0; x < g0MovesStr.length; ++x) {
			g0Moves[x] = new Move(g0MovesStr[x]);
		}
	}
	
	public static void buildG1MoveArray(int g1Face) {
		g1Moves = new Move[8];
		if(g1Face == 0) {
			String[] g1MovesStr = new String[] {"F2 ", "B2 ", "L2 ", "R2 ", "U ", "D ", "U\' ", "D\' "};
			for(int x = 0; x < g1MovesStr.length; ++x) {
				g1Moves[x] = new Move(g1MovesStr[x]);
			}
		}
		else if(g1Face ==1) {
			String[] g1MovesStr1 = new String[] {"F2 ", "B2 ", "L ", "R ", "U2 ", "D2 ", "L\' ", "R\' " };
			for(int x = 0; x < g1MovesStr1.length; ++x) {
				g1Moves[x] = new Move(g1MovesStr1[x]);
			}
		}
		else {
			String[] g1MovesStr2 = new String[] {"F ", "B ", "L2 ", "R2 ", "U2 ", "D2 ", "F\' ", "B\' "};
			for(int x = 0; x < g1MovesStr2.length; ++x) {
				g1Moves[x] = new Move(g1MovesStr2[x]);
			}
		}
		
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
			CubeColor currentFaceCubeColor = facelets[faceInt][4].getColor();
			CubeColor equivFaceCubeColor = facelets[faceInt + 3][4].getColor();
			for(int faceletInt = 0; faceletInt < 9; ++faceletInt) {
				CubeColor faceletCubeColor = facelets[faceInt][faceletInt].getColor();
				CubeColor oppositeFaceletCubeColor = facelets[faceInt+3][faceletInt].getColor();
				if(faceletCubeColor.equals(currentFaceCubeColor) ||  faceletCubeColor.equals(equivFaceCubeColor)) {
					if(oppositeFaceletCubeColor.equals(currentFaceCubeColor) ||  oppositeFaceletCubeColor.equals(equivFaceCubeColor)) {
						if(faceletInt == 8) {
							return faceInt;
						}
					}
					else {
						break;
					}
				}
				else {
					break;
				}
				
			}
			
			
		}
		return 6;
	}
	
	/*
	 * Prints the face of a cube given the
	 */
	public static void printFace(Cube cube, int faceInt) {
		Facelet[] face = cube.getFace(faceInt);
		for(int x=0; x<cube.getSize(); ++x) {
			String row = "";
			for(int y = 0; y < cube.getSize(); ++y) {
				row += face[(3 * x) + y].getLocation()+ " ";
			}
			System.out.println(row);
		}
		System.out.println("\n");
	}

}
