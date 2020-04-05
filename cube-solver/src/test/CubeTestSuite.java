package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cube.Algorithm;
import cube.Cube;
import cube.Facelet;
import cube.Move;


/**
 * Class to test NxNxN cube class
 * @author Brad Hodkinson
 */
class CubeTestSuite {
	Cube oddCube;
	Cube evenCube;
	Cube normalCube;
	Cube smallCube ;
	String[][] normalCubeExpectedFaces;
	final String FACES = "URFDLB";

	@BeforeEach
	void setUp() throws Exception {
		oddCube = new Cube(5);
		evenCube = new Cube(4);
		normalCube = new Cube(3);
		smallCube = new Cube(2);
		normalCubeExpectedFaces = getExpectedFaceLocations(normalCube.getSize());
		
		
	}
	
	private String[][] getExpectedFaceLocations(int cubeSize){
		String[][] expectedFaceLocations = new String[FACES.length()][cubeSize*cubeSize];
		for(int face=0; face<FACES.length(); face++) {
			for(int index=0; index<cubeSize*cubeSize; index++) {
				expectedFaceLocations[face][index] = FACES.charAt(face)+""+(index+1);
			}
		}
		return expectedFaceLocations;

	}
	
	private String[] getActualFaceLocations(Cube cube, int face) {
		String[] expectedFaceLocations = new String[cube.getSize()*cube.getSize()];
		Facelet[] cubeFace = cube.getFace(face);
		for(int i=0; i<cubeFace.length; i++) {
			expectedFaceLocations[i] = cubeFace[i].getLocation();
		}
		return expectedFaceLocations;
	}

	@Test
	void preliminaryIsSolvedTest() {
		//cube should in solved position by default
		assertTrue(normalCube.isSolved());
		assertTrue(smallCube.isSolved());
	}
	
	@Test
	void isSolvedTest() {
		Algorithm cornerRotation = new Algorithm("R\' D\' R D ");
		
		normalCube.applyAlgorithm(cornerRotation);
		assertFalse(normalCube.isSolved());
		
		for(int i=0; i<5; i++) {
			normalCube.applyAlgorithm(cornerRotation);
		}
		assertTrue(normalCube.isSolved());
		
		smallCube.applyAlgorithm(cornerRotation);
		assertFalse(smallCube.isSolved());
		
		for(int i=0; i<5; i++) {
			smallCube.applyAlgorithm(cornerRotation);
		}

		assertTrue(smallCube.isSolved());

	}
	
	@Test
	void moveLeftTest() {
		Move move = new Move("L");

		normalCube.rotate(move);
	
		//get the opposite face
		int untouchedFace = (FACES.indexOf("L") + 3)%6;
		assertEquals(Arrays.toString(normalCubeExpectedFaces[untouchedFace]), Arrays.toString(normalCube.getFace(untouchedFace)));

		//TODO check left face
		
		//TODO check each row of other faces
		
		
	}
	
	@Test
	void moveLeftPrimeTest() {
		
	}
	@Test
	void moveRightTest() {
		
		
	}
	@Test
	void moveRightPrimeTest() {
		
		
	}
	@Test
	void moveUpTest() {
		
		
	}
	@Test
	void moveUpPrimeTest() {
		
		
	}
	@Test
	void moveDownTest() {
		
		
	}
	@Test
	void moveDownPrimeTest() {
		
		
	}
	@Test
	void moveFrontTest() {
		
		
	}
	@Test
	void moveFrontPrimeTest() {
		
		
	}
	@Test
	void moveBackTest() {
		
		
	}
	@Test
	void moveBackPrimeTest() {
		
		
	}
	@Test
	void moveMiddleTest() {
		
		
	}
	@Test
	void moveMiddlePrimeTest() {
		
		
	}
	@Test
	void moveXTest() {
		
		
	}
	@Test
	void moveXPrimeTest() {
		
		
	}
	@Test
	void moveYTest() {
		
		
	}
	@Test
	void moveYPrimeTest() {
		
		
	}
	@Test
	void moveZTest() {
		
		
	}
	@Test
	void moveZPrimeTest() {
		
		
	}
	


}