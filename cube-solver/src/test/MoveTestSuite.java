package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cube.Cube;
import cube.Move;
import solver.Min2PhaseUtil;

class MoveTestSuite {
	
	final char[] FACES = new char[] {'U', 'R', 'F', 'D', 'L', 'B'};


	@Test
	void frontFaceTurnTest() {
		int expectedLayerCount = 1;
		boolean expectedInnerRotation = false;
		//test all faces		
		for(int face=0; face<FACES.length; face++) {
			
			String move = FACES[face]+"";
			
			//test clockwise
			testClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);
			
			//test counter clockwise
			testCounterClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);

			//test double rotation
			testDoubleMove(move, face, expectedLayerCount, expectedInnerRotation);
		}
	}

	@Test
	void testGetInverseMove(){
		Move[] moves = Min2PhaseUtil.getG0Moves();
		Cube testCube = new Cube(3);
		for(Move m : moves){
			testCube.rotate(m);
			testCube.rotate(m.getInverse());
			assertTrue(testCube.isSolved());
		}
	}
	
	
	@Test
	void middleTurnTest() {
		//test all faces
		
		char[] middleMoves = new char[] {'M', 'E', 'S'};
		char[] middleMoveDirectionFaces = new char[] {'L', 'D', 'F'};
		
		int expectedLayerCount = 1;
		boolean expectedInnerRotation = true;
		
		for(int move=0; move<middleMoves.length; move++) {
			//get the directional face of the middle face
			char directionalFace = middleMoveDirectionFaces[move];
			//get the expected face of the directional face
			int face = new String(FACES).indexOf(directionalFace);
			
			String middleMove = middleMoves[move]+"";

					
			//test clockwise
			testClockwiseMove(middleMove, face, expectedLayerCount, expectedInnerRotation);
			
			//test counter clockwise
			testCounterClockwiseMove(middleMove, face, expectedLayerCount, expectedInnerRotation);

			//test double rotation
			testDoubleMove(middleMove, face, expectedLayerCount, expectedInnerRotation);
		}
	}
	
	
	@Test
	void cubeRotationTest() {
		//test all faces
		
		char[] cubeRotations = new char[] {'x', 'y', 'z'};
		char[] cubeRotationDirectionFaces = new char[] {'R', 'U', 'F'};
		
		int expectedLayerCount = 0;
		boolean expectedInnerRotation = false;
		
		for(int move=0; move<cubeRotations.length; move++) {
			//get the directional face of cube rotation
			char directionalFace = cubeRotationDirectionFaces[move];
			//get the expected face of cube directional rotation
			int face = new String(FACES).indexOf(directionalFace);
			
			String cubeRotation = cubeRotations[move] + "";
					
			//test clockwise
			testClockwiseMove(cubeRotation, face, expectedLayerCount, expectedInnerRotation);
			
			//test counter clockwise
			testCounterClockwiseMove(cubeRotation, face, expectedLayerCount, expectedInnerRotation);

			//test double rotation
			testDoubleMove(cubeRotation, face, expectedLayerCount, expectedInnerRotation);
		}
	}
	
	
	@Test
	void innerMoveTest() {
		//test all faces
		
		int expectedLayerCount = 1;
		boolean expectedInnerRotation = true;
		//test all faces		
		for(int face=0; face<FACES.length; face++) {
			
			String move = (FACES[face]+"").toLowerCase();
			
			//test clockwise
			testClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);
			
			//test counter clockwise
			testCounterClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);

			//test double rotation
			testDoubleMove(move, face, expectedLayerCount, expectedInnerRotation);
		}
	}
	
	
	@Test
	void wideMoveTest() {
		defaultWideMoveTest();
		variableLengthWideMoveTest();
		
	}
	
	private void defaultWideMoveTest() {
		//test all faces
		
		int expectedLayerCount = 2;
		boolean expectedInnerRotation = false;
		//test all faces		
		for(int face=0; face<FACES.length; face++) {
			
			String move = FACES[face]+"w";
			
			//test clockwise
			testClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);
			
			//test counter clockwise
			testCounterClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);

			//test double rotation
			testDoubleMove(move, face, expectedLayerCount, expectedInnerRotation);
		}
	}
	

	private void variableLengthWideMoveTest() {
		//test all faces
		
		boolean expectedInnerRotation = false;
		//test all faces		
		for(int face=0; face<FACES.length; face++) {
			for(int expectedLayerCount=2; expectedLayerCount<5; expectedLayerCount++) {
				String move = expectedLayerCount + (FACES[face]+"w");

				//test clockwise
				testClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);
				
				//test counter clockwise
				testCounterClockwiseMove(move, face, expectedLayerCount, expectedInnerRotation);

				//test double rotation
				testDoubleMove(move, face, expectedLayerCount, expectedInnerRotation);
				
			}
		}
	}
	
	private void testClockwiseMove(String move, int face, int expectedLayerCount, boolean expectedInnerRotation) {
		Move clockwiseMove = new Move(move+"");
		System.out.println(move);
		assertEquals(face, clockwiseMove.getFace());
		assertEquals(expectedLayerCount, clockwiseMove.getLayerCount());

		assertFalse(clockwiseMove.isCounterClockwise());
		assertFalse(clockwiseMove.isDoubleRotation());
		assertEquals(expectedInnerRotation, clockwiseMove.isInnerRotation());
		
	}
	
	private void testCounterClockwiseMove(String move, int face, int expectedLayerCount, boolean expectedInnerRotation) {
		Move counterClockwiseMove = new Move(move+"\'");
		assertEquals(face, counterClockwiseMove.getFace());
		assertEquals(expectedLayerCount, counterClockwiseMove.getLayerCount());

		assertTrue(counterClockwiseMove.isCounterClockwise());
		assertFalse(counterClockwiseMove.isDoubleRotation());
		assertEquals(expectedInnerRotation, counterClockwiseMove.isInnerRotation());

	}
	
	private void testDoubleMove(String move, int face, int expectedLayerCount, boolean expectedInnerRotation) {
		Move doubleMove = new Move(move+"2");
		assertEquals(face, doubleMove.getFace());
		assertEquals(expectedLayerCount, doubleMove.getLayerCount());

		assertTrue(doubleMove.isDoubleRotation());
		assertEquals(expectedInnerRotation, doubleMove.isInnerRotation());

	}

}