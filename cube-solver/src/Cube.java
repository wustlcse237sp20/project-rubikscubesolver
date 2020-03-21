
/**
 * Class to a NxNxN cube
 * @author Brad Hodkinson
 */
public class Cube {
	
	//6 faces of the cube: Up, Right, Front, Down, Back, Left
	final byte[] FACES = new byte[] {'U','R', 'F', 'D', 'L', 'B'};
	//6 colors of the cube
	Color[] colors = new Color[] {Color.WHITE, Color.BLUE, Color.RED, Color.YELLOW, Color.ORANGE, Color.GREEN};
	
		
	int size;
	private Facelet[][] cube;
	private Cuboid[][][] cubeMatrix;

	/**
	 * public constructor for creating a cube
	 * @param size the size of the cube to create
	 */
	public Cube(int size) {
		if(size<1) {
			//TODO change to be a custom exception
			System.out.println("Invalid Cube Size. Must be size greater than or equal to 1.");
		}
		this.size = size;
		//init a size by size cube
		cube = new Facelet[FACES.length][size*size];
		for(byte face =0; face<FACES.length; face++) {
			for(int i=0; i<size*size; i++) {
				String location = (char)FACES[face] + "" + (i+1);
				cube[face][i] = new Facelet(colors[face], location);
			}
		}
		int count =0;
		for(Facelet f: rotateFaceCounterClockwise(0)) {
			count++;
			System.out.print(f);
			System.out.print(" ");
			if(count%3 == 0) {
				System.out.println();

			}
		}
		System.out.println();
		System.out.println(isSolved());
		int testFace = 5;
		System.out.println((char)FACES[testFace]);
		System.out.println((char)FACES[this.getOppositeFace(testFace)]);
		
		System.out.println("Adjacent Faces:");
		for(int face : getAdjacentFaces(testFace)){
			System.out.print((char)FACES[face]);
			System.out.print(" ");
		}
		System.out.println();
	}
	
	/*
	 * Method to check if the cube is solved
	 * @return true if the cube is solved, else false
	 */
	public boolean isSolved() {
		//check if all the colors on the cube are the same
		for(Facelet[] face : this.cube) {
			Color firstColor = face[0].getColor();
			for(int i=1; i<face.length; i++) {
				if(face[i].getColor() != firstColor) {
					return false;
				}
			}
			
		}
		return true;
	}
	
	public void applyAlgorithm(Algorithm algorithm) {
		//TODO
		
	}
	
	/*
	 * rotate the cube given a move
	 * @param move to rotate move
	 */
	public void rotate(Move move) {
		//TODO
		
	}
	
	/*
	 * 	Rotate the face clockwise. The given face:
	 *		+----------+
	 *     	| U1 U2 U3 |
	 *      | U4 U5 U6 |
	 *      | U7 U8 U9 |
	 *      +----------+
	 * After a clockwise rotation would return
	 * 	 	+----------+
	 *     	| U7 U4 U1 |
	 *      | U8 U5 U2 |
	 *      | U9 U6 U3 |
	 *      +----------+
	 * 
	 * @param face
	 * 
	 */
	private Facelet[] rotateFaceClockwise(int face) {
		Facelet[] result = new Facelet[this.size*this.size];
		//to rotate clockwise, loop through rows and replace with reversed col 
		for(int row=0; row<this.size; row++) {
			Facelet[] faceletCol = getCol(face, row);
			for(int col=0; col<this.size; col++) {
				result[row*this.size+col] = faceletCol[this.size-col-1];
			}
		}
		
		return result;
	}
	
	/*
	 * 	Rotate the face counter clockwise. The given 3x3 face:
	 *		+----------+
	 *     	| U1 U2 U3 |
	 *      | U4 U5 U6 |
	 *      | U7 U8 U9 |
	 *      +----------+
	 * After a counter clockwise rotation would return
	 * 	 	+----------+
	 *     	| U3 U6 U9 |
	 *      | U2 U5 U8 |
	 *      | U1 U4 U7 |
	 *      +----------+
	 * 
	 * @param face
	 * @return array of Facelets
	 */
	private Facelet[] rotateFaceCounterClockwise(int face) {
		Facelet[] result = new Facelet[this.size*this.size];
		//to rotate clockwise, loop through rows and replace with reversed col 
		for(int row=0; row<this.size; row++) {
			Facelet[] faceletCol = getCol(face, this.size-row-1);
			for(int col=0; col<this.size; col++) {
				result[row*this.size+col] = faceletCol[col];
			}
		}
		
		return result;
	}
	
	/*
	 * Get a list of Facelets for a face
	 * @param face
	 * @return array of Facelets
	 */
	public Facelet[] getFace(int face) {
		return this.cube[face];
	}
	
	/*
	 * Get a list of Facelets for a row of a face
	 * @param face
	 * @param row
	 * @return array of Facelets
	 */
	private Facelet[] getRow(int face, int row) {
		Facelet[] result = new Facelet[this.size];
		for(int i=0; i<this.size; i++) {
			result[i] = this.cube[face][(row*this.size)+i];
		}
		return result;
	}
	
	/*
	 * Get a list of Facelets for a column of a face
	 * @param face
	 * @param col
	 * @return array of Facelets
	 */
	private Facelet[] getCol(int face, int col) {
		Facelet[] result = new Facelet[this.size];
		for(int i=0; i<this.size; i++) {
			result[i] = this.cube[face][(i*this.size)+col];
		}
		return result;
	}
	
	/*
	 * Get a list of adjacent faces
	 * @param face
	 * @return integer list of adjacent faces
	 */
	private int[] getAdjacentFaces(int face) {
		int[] result = new int[4];
		int oppositeFace = getOppositeFace(face);
		int count =0;
		for(int i=0; i<6; i++) {
			//adjacent face is every face except current face, and opposite face
			if(i!=face && i!=oppositeFace) {
				result[count] = i;
				count++;
			}
		}
		
		return result;
	}
	
	/*
	 * Gets the opposite face. List of opposite faces:
	 * 
	 * 		Up(0) <-> Down(3)
	 * 		Right(1) <-> Left(4)
	 * 		Front(2) <-> Back(5)
	 * 
	 * @param face
	 * @return integer value of opposite face
	 */
	private int getOppositeFace(int face) {
		return (face+3)%6;
	}
	
	
	/*
	 * Prints out a 2D representation of the cube.
	 * A 3x3 cube in the solved state would look like the following:
	 * 
	 *      	        +----------+
	 *     		       	| U1 U2 U3 |
	 *       	    	| U4 U5 U6 |
	 *        	    	| U7 U8 U9 |
	 *         		   	+----------+
	 *		+----------++----------++----------++----------+
	 *		| L1 L2 L3 || F1 F2 F3 || R1 R2 R3 || B1 B2 B3 |
	 *		| L4 L5 L6 || F4 F5 F6 || R4 R5 R6 || B4 B5 B6 |
	 *		| L7 L8 L9 || F7 F8 F9 || R7 R8 R9 || B7 B8 B9 |
	 *		+----------++----------++----------++----------+
	 *				    +----------+
	 *		            | D1 D2 D3 |
	 *		            | D4 D5 D6 |
	 *     			    | D7 D8 D9 |
	 *		            +----------+
	 * 
	 */
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		int maxDisplaySize = (this.size * this.size + "").length() + 2;
		
		//display face 0
		addPaddedFaceDisplay(stringBuilder, 0, maxDisplaySize);
		
		//display faces 5, 2, 1, and 4 in a row
		addFaceDisplayRow(stringBuilder, new int[]{4,2,1,5}, maxDisplaySize);
		
		//display face 3
		addPaddedFaceDisplay(stringBuilder, 3, maxDisplaySize);
		
		return stringBuilder.toString();
	}
	
	/*
	 * in class method to display an array of faces in a row
	 */
	private void addFaceDisplayRow(StringBuilder stringBuilder, int[] faces, int maxDisplaySize) {
		
		addHorizontalLineRow(stringBuilder, faces.length, maxDisplaySize);

		for(int row=0; row<this.size; row++) {
			for(int face: faces) {
				stringBuilder.append("| ");
				for(int i=0; i<this.size; i++) {
					String faceletDisplay = cube[face][(row*this.size)+i].toString();
					stringBuilder.append(faceletDisplay);
					
					int paddingSize = maxDisplaySize-faceletDisplay.length();
					String paddingDisplay = new String(new char[paddingSize]).replace('\0', ' ');
					stringBuilder.append(paddingDisplay);
					
				}
				stringBuilder.append("|");
			}
			
			stringBuilder.append("\n");
		}	
		
		addHorizontalLineRow(stringBuilder, faces.length, maxDisplaySize);
		
	}
	
	/*
	 * in class method to display a face of the cube with padding
	 */
	private void addPaddedFaceDisplay(StringBuilder stringBuilder, int face, int maxDisplaySize) {
		addHorizontalSpace(stringBuilder, maxDisplaySize);
		addHorizontalLine(stringBuilder, maxDisplaySize);
		stringBuilder.append("\n");
				
		for(int row=0; row<this.size; row++) {
			addHorizontalSpace(stringBuilder, maxDisplaySize);
			stringBuilder.append("| ");
			for(int i=0; i<this.size; i++) {
				String faceletDisplay = cube[face][(row*this.size)+i].toString();
				stringBuilder.append(faceletDisplay);
				
				int paddingSize = maxDisplaySize-faceletDisplay.length();
				String paddingDisplay = new String(new char[paddingSize]).replace('\0', ' ');
				stringBuilder.append(paddingDisplay);

			}
			stringBuilder.append("|\n");
		}	
		addHorizontalSpace(stringBuilder, maxDisplaySize);
		addHorizontalLine(stringBuilder, maxDisplaySize);
		stringBuilder.append("\n");
		
	}
	
	/*
	 * in class method to add horizontal lines in a row
	 */
	private void addHorizontalLineRow(StringBuilder stringBuilder, int rowSize, int maxDisplaySize) {
		for(int i=0; i< rowSize; i++) {
			addHorizontalLine(stringBuilder, maxDisplaySize);
		}
		stringBuilder.append("\n");
	}
	
	/*
	 * in class method to add horizontal lines to separate out faces
	 */
	private void addHorizontalLine(StringBuilder stringBuilder, int maxDisplaySize) {
		stringBuilder.append("+");
		
		String lineDisplay = new String(new char[maxDisplaySize]).replace('\0', '-');

		for(int i=0; i<this.size; i++) {
			stringBuilder.append(lineDisplay);
		}
		stringBuilder.append("-+");
	}
	
	/*
	 * in class helper method to add spacing offset for displaying faces
	 */
	private void addHorizontalSpace(StringBuilder stringBuilder, int maxDisplaySize) {
		
		String spaceDisplay = new String(new char[maxDisplaySize]).replace('\0', ' ');

		for(int i=0; i<this.size; i++) {
			stringBuilder.append(spaceDisplay);
		}
		stringBuilder.append("   ");
	}
	
}
