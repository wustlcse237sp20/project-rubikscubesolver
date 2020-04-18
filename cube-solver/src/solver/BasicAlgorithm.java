package solver;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cube.Algorithm;
import cube.Color;
import cube.Cube;
import cube.Facelet;
import cube.Move;

/*
 * Basic solving algorthm (White cross)
 * 
 */
public class BasicAlgorithm {
	
	//Create hashmap
	
	
	public static void main(String[] args) {
		Cube testCube = new Cube(3);
		
		
		
		System.out.println(testCube);
		
//		simpleScramlbe(testCube);
		for(Facelet[] face: testCube.getFacelets()) {
			for(Facelet f: face) {
				f.setDisplayLocation(true);
			}
		}
		
		Algorithm algorithm = new Algorithm("L L");
		testCube.applyAlgorithm(algorithm);
		
		System.out.println(testCube);
		
		String solvedWith = randomSolver(testCube);
		System.out.println(solvedWith);
		System.out.println(testCube);
		
//		System.out.println(testCube);
//		isWhiteCrossSolved(testCube);
//		solveWhiteCross(testCube);
	
	}
	
	public static Boolean hashMapBuilder(Cube cube) {
		Map<Cube, String> cubeStates = new HashMap<Cube, String>();

	        
		return true;
	}
	
	public static Boolean hashMapSaver(HashMap<Cube, String> map) {
		try {
	        File file=new File("states");
	        FileOutputStream fos=new FileOutputStream(file);
	        ObjectOutputStream oos=new ObjectOutputStream(fos);
	        oos.writeObject(map);
	        oos.flush();
	        oos.close();
	        fos.close();
	        return true;
	    } catch(Exception e) {
	    	return false;
	    }
	}
	
	public static Boolean hashMapReader(HashMap<Cube, String> map) {
		try {
	        File file =new File("states");
	        FileInputStream fis=new FileInputStream(file);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	        map =(HashMap<Cube, String>) ois.readObject();
	        ois.close();
	        fis.close();
	        return true;
	        
	    } catch(Exception e) {
	    	return false;
	    }
	}
	
	
	
	public static String randomSolver(Cube cube) {
		String[] moves = {"U ", "U\' ", "F ", "F\' ", "R ", "R\' ", "L ", "L\' ", "B ", "B\' ", "D ", "D\' "};	
		String strAlg = "";
		Cube temp = cube;
		int blah;
		while(!temp.isSolved()) {
			temp = cube;
			strAlg = "L\' L\'";
			
//			for(int x = 0; x <= 11; ++x) {
//				blah = x;
//				strAlg += moves[blah];
//				System.out.println(strAlg);
//			}
			Algorithm alg = new Algorithm(strAlg);
			temp.applyAlgorithm(alg);
		}
		return strAlg;
	}
	
	
	public static int rand(int min, int max)
	{
		if (min > max || (max - min + 1 > Integer.MAX_VALUE)) {
			throw new IllegalArgumentException("Invalid range");
		}

		return new Random().nextInt(max - min + 1) + min;
	}
	
	
	public static void solveWhiteCross(Cube cube) {//Concerned with face 0, facelets 1 3 5 7
		Facelet[][] facelets = cube.getFacelets();
		Facelet[] topFace = facelets[0];
		Boolean topFaceSet = false;
		Boolean leftFaceSet = false;
		Boolean rightFaceSet = false;
		Boolean bottomFaceSet = false;
		int numSolved = 0;
		if(isWhiteCrossSolved(cube)) return;
		int[] coordinates = findFacelet("U2", cube);
		System.out.println(coordinates[0] + "," + coordinates[1]);
		if(coordinates[0] == 0) { // on top
				rotateTop(cube, coordinates);
		}
		else if(coordinates[0]>0 && coordinates[1] == 3) { // not on top and in position 3
			//Turn left adjacent face 
			System.out.println(cube);
			String move = leftAdjacentFace(coordinates[0], false);
			if(coordinates[0]==3) {
				move += move;
			}
			
			Algorithm alg = new Algorithm(move);
			cube.applyAlgorithm(alg);
			System.out.println(cube);
			coordinates = findFacelet("U2", cube);
			rotateTop(cube, coordinates);
			
		}
				
			
		
		//Keep Solve clockwise start with u2 
		//Case 1 -> 0,blah (on top) -> Rotate until in position U
		//Case 2 -> blah,4 (can rotate up (up again if 3) -> case 1
		//Case 3 -> 3,blah -> Rotate until 3,4 then up
		//Case 4 -> Any other rotate current face until blah,7 then U Current, back
		
	}
	
	public static void rotateTop(Cube cube, int[] coordinates) {
		printFace(cube, 0);
		Facelet[][] facelets = cube.getFacelets();
		while(!isFaceletSolved(facelets[0][1], cube)) {
			Algorithm algorithm = new Algorithm("U");
			cube.applyAlgorithm(algorithm);
			printFace(cube, 0);
			coordinates = findFacelet("U2",cube);
		}
	}
	
	
	/*
	 * @Return coordinates of the position of the facelet in the cube
	 */
	public static int[] findFacelet(String toFindLocation, Cube cube) {
		Facelet[][] facelets = cube.getFacelets();
		Cube solved = new Cube(cube.getSize());
		int faceIndex = 0;
		int faceletIndex = 0;
		int[] coordinates = {9,9};
		for(Facelet[] face : facelets) {
//			printFace(cube, 0);
			faceletIndex= 0;
			for(Facelet facelet: face) {
				if(toFindLocation.equals(facelet.getLocation())) {
//					System.out.println(toFindLocation + " " + faceIndex +","+ faceletIndex);
					coordinates[0] = faceIndex;
					coordinates[1] = faceletIndex;
					return coordinates;
				}
				++faceletIndex;
			}
			++faceIndex;
		}
		return coordinates;
	}
	
	

	/*
	 * Returns true if the white cross has been solved
	 */
	public static boolean isWhiteCrossSolved(Cube cube) {
		Facelet[][] facelets = cube.getFacelets();
		Facelet top = facelets[0][1];
		Facelet left = facelets[0][3];
		Facelet right = facelets[0][5];
		Facelet bottom = facelets[0][7];
		if(isFaceletSolved(top, cube) && isFaceletSolved(left, cube) && isFaceletSolved(right, cube) && isFaceletSolved(bottom, cube)) {
			return true;
		}
		return false;
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
	
	
	/*
	 * Checks if facelet is in correct position in the cube
	 * @Params facelet to check and the cube that facelet is in
	 */
	public static boolean isFaceletSolved(Facelet facelet, Cube cube) {
		int index = getFaceletLocationIndex(facelet);
		Cube solved = new Cube(cube.getSize());
		int faceInt = getFaceletLocationFaceIndex(facelet);
		Facelet[] solvedFace = solved.getFacelets()[faceInt];
		String unsolvedCubeFaceletLocation = cube.getFacelets()[faceInt][index].getLocation();
		String solvedFaceletLocation = solvedFace[index].getLocation();
//		System.out.println("Unsolved: " + unsolvedCubeFaceletLocation + " Solved: "+ solvedFaceletLocation);
		if(solvedFaceletLocation.equals(unsolvedCubeFaceletLocation)){
			return true;
		}
		return false;
	}
	
	/*
	 * @Return the index that can be used to get the facelet through facelet[faceIndex][return]
	 */
	public static int getFaceletLocationIndex(Facelet facelet) {
		String faceletLocation  = facelet.getLocation();
		int index = faceletLocation.charAt(1) - '1';
		return index;
	}
	
	/*
	 * @Return the index that can be used to get the face through facelet[x]
	 */
	public static int getFaceletLocationFaceIndex(Facelet facelet) {
		String faceletLocation  = facelet.getLocation();
		char faceChar = faceletLocation.charAt(0);
		int faceInt = 0;
		switch(faceChar) {
		case 'U': 
			break;
		case 'R':
			faceInt =1;
			break;
		case 'F':
			faceInt =2;
		case 'D':
			faceInt =3;
		case 'L':
			faceInt = 4;
		case 'B':
			faceInt =5;
		}
		return faceInt;
	}
	
	/*
	 * Scrambles the cube by 20 moves
	 */
	public static void simpleScramlbe(Cube testCube) {
		int cubeSize = testCube.getSize();
		int scrambleLength = 20;
		Algorithm scramble = new Algorithm();
		scramble.generateScramble(cubeSize, scrambleLength);
		testCube.applyAlgorithm(scramble);
	}
	
	
//	U - rotate up face clockwise		U' - rotate up face counter-clockwise
//	R - rotate right face clockwise		R' - rotate right face counter-clockwise
//	F - rotate front face clockwise		F' - rotate front face counter-clockwise
//	D - rotate down face clockwise		D' - rotate down face counter-clockwise
//	L - rotate left face clockwise		L' - rotate left face counter-clockwise
//	B - rotate back face clockwise		B' - rotate back face counter-clockwise
	
	public static String leftAdjacentFace(int face, Boolean isCW) { 
		switch(face) {
		case(1)://blue
			if(isCW) return "F";
			return "F\'";
		case(2)://red
			if(isCW) return "L";
			return "L\'";
		case(3)://yellow
			if(isCW) return "L";
			return "L\'";
		case(4):
			if(isCW) return "B";
			return "B\'";
		case(5):
			if(isCW) return "R";
			return "R\'";
		}
		return "";
		
	}

	
	public static String rightAdjacnetFace(int face, Boolean isCW) { // down
		switch(face) {
		case(1)://blue
			if(isCW) return "B";
			return "B\'";
		case(2)://red
			if(isCW) return "R";
			return "R\'";
		case(3)://yellow
			if(isCW) return "R";
			return "R\'";
		case(4):
			if(isCW) return "F";
			return "F\'";
		case(5):
			if(isCW) return "L";
			return "L\'";
		}
		return "";
	}

}
