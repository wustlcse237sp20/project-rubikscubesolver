import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		assertEquals(face, clockwiseMove.face);
		assertEquals(expectedLayerCount, clockwiseMove.layerCount);

		assertFalse(clockwiseMove.counterClockwise);
		assertFalse(clockwiseMove.doubleRotation);
		assertEquals(expectedInnerRotation, clockwiseMove.innerRotation);
		
	}
	
	private void testCounterClockwiseMove(String move, int face, int expectedLayerCount, boolean expectedInnerRotation) {
		Move counterClockwiseMove = new Move(move+"\'");
		assertEquals(face, counterClockwiseMove.face);
		assertEquals(expectedLayerCount, counterClockwiseMove.layerCount);

		assertTrue(counterClockwiseMove.counterClockwise);
		assertFalse(counterClockwiseMove.doubleRotation);
		assertEquals(expectedInnerRotation, counterClockwiseMove.innerRotation);

	}
	
	private void testDoubleMove(String move, int face, int expectedLayerCount, boolean expectedInnerRotation) {
		Move doubleMove = new Move(move+"2");
		assertEquals(face, doubleMove.face);
		assertEquals(expectedLayerCount, doubleMove.layerCount);

		assertTrue(doubleMove.doubleRotation);
		assertEquals(expectedInnerRotation, doubleMove.innerRotation);

	}

}
