package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cube.Cube;


/**
 * Class to test NxNxN cube class
 * @author Brad Hodkinson
 */
class CubeTestSuite {
	
	Cube cube;
	Cube smallCube ;

	@BeforeEach
	void setUp() throws Exception {
		cube = new Cube(3);
		smallCube = new Cube(2);

	}

	@Test
	void preliminaryIsSolvedTest() {
		//cube should in solved position by default
		assertTrue(cube.isSolved());
		assertTrue(smallCube.isSolved());
	}
	
	@Test
	void isSolvedTest() {
		//test cube
		
//		cube.rotate();
		assertFalse(cube.isSolved());
//		cube.rotate(Move.LEFT_PRIME);
		assertTrue(cube.isSolved());
		
		//test small cube
//		smallCube.rotate(Move.RIGHT);
		assertFalse(cube.isSolved());
//		smallCube.rotate(Move.RIGHT_PRIME);
		assertTrue(smallCube.isSolved());

	}
	
	@Test
	void preliminaryMoveTest() {
		fail("Test not yet implemented");
	}
	
	@Test
	void moveTest() {
		fail("Test not yet implemented");
	}
	
	@Test
	void moveLeftTest() {
//		smallCube.rotate(Move.LEFT);
//		assertEquals();
		fail("Test not yet implemented");
//		cube.rotate(Move.LEFT);

		
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