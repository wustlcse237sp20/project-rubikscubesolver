package cube;

/**
 * Class to a NxNxN cube
 * @author Brad Hodkinson
 */
public class Cube {
	
	//6 faces of the cube: Up, Right, Front, Down, Back, Left
	final char[] FACES = new char[] {'U','R', 'F', 'D', 'L', 'B'};
	//6 colors of the cube
	CubeColor[] colors = new CubeColor[] {CubeColor.WHITE, CubeColor.BLUE, CubeColor.RED, CubeColor.YELLOW, CubeColor.GREEN, CubeColor.ORANGE};
	
		
	int size;
	private Facelet[][] cube;
	
	boolean displayLocation = false;


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
				String location = FACES[face] + "" + (i+1);
				cube[face][i] = new Facelet(colors[face], location, displayLocation);
			}
		}
		
	}
	
	/**
	 * Public deep copy constructor
	 * @param a cube to copy
	 */
	public Cube(Cube toCopy) {
		this.size = toCopy.size;
		this.displayLocation = toCopy.displayLocation;	
		this.cube = new Facelet[6][9];
		for(int faceInt = 0; faceInt < 6; ++faceInt) {
			for(int faceletInt = 0; faceletInt < 9; ++faceletInt) {
				this.cube[faceInt][faceletInt] = new Facelet(toCopy.cube[faceInt][faceletInt]);
			}
		}
	}
	
//	protected Object clone() throws CloneNotSupportedException {
//	    Cube cloned = (Cube)super.clone();
//	    cloned.setFacelets((Facelet)cloned.getFacelets().clone());   
//	    return cloned;
//	}
	
	
	
	
	
	/*
	 * Method to check if the cube is solved
	 * @return true if the cube is solved, else false
	 */
	public boolean isSolved() {
		//check if all the colors on the cube are the same
		for(Facelet[] face : this.cube) {
			CubeColor firstColor = face[0].getColor();
			for(int i=1; i<face.length; i++) {
				if(face[i].getColor() != firstColor) {
					return false;
				}
			}
			
		}
		return true;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void applyAlgorithm(Algorithm algorithm) {
		//for each move in the algorithm, rotate the cube accordingly
		for(Move move: algorithm.moveList) {
			this.rotate(move);
		}
	}
	
	/*
	 * rotate the cube given a move
	 * @param move to rotate move
	 */
	public void rotate(Move move) {
		
		if(move.isCubeRotation()) {
			//TODO perform cube rotation
			if(move.counterClockwise) {
				rotateCubeCounterClockwise(move.getFace());
				if(move.isDoubleRotation()) {
					rotateCubeCounterClockwise(move.getFace());

				}
			} else {
				rotateCubeClockwise(move.getFace());

				if(move.isDoubleRotation()) {
					rotateCubeClockwise(move.getFace());

				}
			}
		} else if(move.isInnerRotation()) {
			if(move.isMiddleRotation()) {
				int middleLayer = this.size/2;
				if(move.counterClockwise) {
					rotateLayersCounterClockwise(move.getFace(), move.getLayerCount(), middleLayer);
					if(move.isDoubleRotation()) {
						rotateLayersCounterClockwise(move.getFace(), move.getLayerCount(), middleLayer);
					}
				} else {
					rotateLayersClockwise(move.getFace(), move.getLayerCount(), middleLayer);
					if(move.isDoubleRotation()) {
						rotateLayersClockwise(move.getFace(), move.getLayerCount(), middleLayer);

					}
					
				}
			} else {
				if(move.counterClockwise) {
					rotateLayersCounterClockwise(move.getFace(), move.getLayerCount(), 1);
					if(move.isDoubleRotation()) {
						rotateLayersCounterClockwise(move.getFace(), move.getLayerCount(), 1);
					}
				} else {
					rotateLayersClockwise(move.getFace(), move.getLayerCount(), 1);
					if(move.isDoubleRotation()) {
						rotateLayersClockwise(move.getFace(), move.getLayerCount(), 1);
	
					}
					
				}
			}
		

		} else {
		
			if(move.counterClockwise) {
				rotateFaceCounterClockwise(move.getFace());
				rotateLayersCounterClockwise(move.getFace(), move.getLayerCount(), 0);
				if(move.isDoubleRotation()) {
					rotateFaceCounterClockwise(move.getFace());
					rotateLayersCounterClockwise(move.getFace(), move.getLayerCount(), 0);
				}
				
			} else {
				rotateFaceClockwise(move.getFace());
				rotateLayersClockwise(move.getFace(), move.getLayerCount(), 0);
				if(move.isDoubleRotation()) {
					rotateFaceClockwise(move.getFace());
					rotateLayersClockwise(move.getFace(), move.getLayerCount(), 0);
				}
				
			}
			

			
		}

		
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
	private void rotateFaceClockwise(int face) {
		Facelet[] result = new Facelet[this.size*this.size];
		//to rotate clockwise, loop through rows and replace with reversed col 
		for(int row=0; row<this.size; row++) {
			Facelet[] faceletCol = getCol(face, row);
			for(int col=0; col<this.size; col++) {
				result[row*this.size+col] = faceletCol[this.size-col-1];
//				cube[face][row*this.size+col] = faceletCol[this.size-col-1];
			}
		}
		cube[face] = result;
		
//		return result;
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
	private void rotateFaceCounterClockwise(int face) {
		Facelet[] result = new Facelet[this.size*this.size];
		//to rotate clockwise, loop through rows and replace with reversed col 
		for(int row=0; row<this.size; row++) {
			Facelet[] faceletCol = getCol(face, this.size-row-1);
			for(int col=0; col<this.size; col++) {
				result[row*this.size+col] = faceletCol[col];
			}
		}
		cube[face] = result;
//		return result;
	}
	
	public Facelet[][] getFacelets() {
		return this.cube;
	}
	
	/*
	 * Get a list of Facelets for a face
	 * @param face
	 * @return array of Facelets
	 */
	public Facelet[] getFace(int face) {
		return this.cube[face];
	}
	
	private void rotateLayersClockwise(int face, int layerCount, int startLayer) {
		
		int[] adjacentFaces = getAdjacentFaces(face);

//		System.out.print("Move: "+FACES[face]);
//		System.out.println();
//		for(int f: adjacentFaces) {
//			System.out.print(FACES[f] + " ");
//		}
//		System.out.println();
		switch(FACES[face]){
			case 'U':
				//[012]
				//R=row0, F=row0, L=row0, B=row0

				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					int row = 0;
					Facelet[] nextRow = getRow(adjacentFaces[0], row+layer);

					for(int i=0; i<adjacentFaces.length; i++){
						//each face replace proper row or col
						for(int j=0; j<nextRow.length; j++) {
							Facelet temp = this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][((row+layer)*this.size)+j];
							this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][((row+layer)*this.size)+j] = nextRow[j];
							nextRow[j] = temp;
						}
					}
				}
				
				break;
			case 'R':
				//[258]		r[630]
				//U=last col, F=last col, D=last col, B=col0
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					int col = this.size-1;
					Facelet[] line = getCol(adjacentFaces[0], col-layer);

					for(int i=adjacentFaces.length; i>0; i--){
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							col = i == adjacentFaces.length ? 0+layer : this.size-1-layer;
							int index = i == adjacentFaces.length ? ((line.length-j-1)*this.size)+col : (j*this.size)+col;
							
							Facelet temp = this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}
				
				break;
			case 'F':
				//[678]		[036]	[012]	[258]
				//U=last row, R=col0, D=row0, L=last col
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					int lineIndex = this.size-1;
					Facelet[] line = getRow(adjacentFaces[0], lineIndex-layer);

					for(int i=0; i<adjacentFaces.length; i++){
						
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							//get last row/col of the last two layers (L U)
							lineIndex = i >= 2 ? this.size-1-layer : 0+layer;
							//reverse the order of  the last two layers (D L)
							int k = (i == 1 || i == 2) ? (this.size-j-1) : j;
							int index = i%2 == 0 ? (k*this.size)+lineIndex : (lineIndex*this.size)+k;

							Facelet temp = this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}
				break;
			case 'L':
				//r[630]		[258]
				//U=col0, F=col0, D=col0, B=last col
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					int col = 0;
					Facelet[] line = getCol(adjacentFaces[0], col+layer);

					for(int i=0; i<adjacentFaces.length; i++){
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							//Note order: U, F, D, B
							//-2 because the BACK layer is the special case that happens D -> B
							col = i == adjacentFaces.length-2 ? this.size-1-layer :  0+layer;
							int index = i == adjacentFaces.length-2 ? ((line.length-j-1)*this.size)+col : (j*this.size)+col;
							Facelet temp = this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}

				
				break;
			case 'D':
				//[012]
				//R=last row, F=last row, L=last row, B=last row
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					int row = this.size-1;
					Facelet[] nextRow = getRow(adjacentFaces[0], row-layer);

					for(int i=adjacentFaces.length; i>0; i--){

						//each face replace proper row or col
						for(int j=0; j<nextRow.length; j++) {
							Facelet temp = this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][((row-layer)*this.size)+j];
							this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][((row-layer)*this.size)+j] = nextRow[j];
							nextRow[j] = temp;
						}
					}
				}

				break;
			case 'B':
				//[012]		[258]		r[876]	r[630]
				//U=row0, R=last col, D=last row, L=col 0
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					//[R9 R6 R3] [D9 D8 D7] [L7 L4 L1] [U1 U2 U3]
					int lineIndex = 0;
					Facelet[] line = getRow(adjacentFaces[0], lineIndex+layer);

					for(int i=adjacentFaces.length; i>0; i--){
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							//get last row/col of the last two layers
							lineIndex = (i == 2 || i == 3) ? this.size-1-layer : 0+layer;
							//reverse the order of  the last two layers
							int k = (i == 1 || i == 2) ? j : (this.size-j-1);
							int index = i%2 == 0 ? (k*this.size)+lineIndex : (lineIndex*this.size)+k;

							Facelet temp = this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}
				break;
		}
	}
	
	private void rotateLayersCounterClockwise(int face, int layerCount, int startLayer) {
		
		int[] adjacentFaces = getAdjacentFaces(face);
		
//		System.out.print("Move: "+FACES[face]);
//		System.out.println();
//		for(int f: adjacentFaces) {
//			System.out.print(FACES[f] + " ");
//		}
//		System.out.println();
		
		switch(FACES[face]){
			case 'U':
				//[012]
				//R=row0, F=row0, L=row0, B=row0

				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					int row = 0;
					Facelet[] nextRow = getRow(adjacentFaces[0], row+layer);

					for(int i=adjacentFaces.length; i>0; i--){
						//each face replace proper row or col
						for(int j=0; j<nextRow.length; j++) {
							Facelet temp = this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][((row+layer)*this.size)+j];
							this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][((row+layer)*this.size)+j] = nextRow[j];
							nextRow[j] = temp;
						}
					}
				}
				
				break;
			case 'R':
				//[258]		r[630]
				//U=last col, F=last col, D=last col, B=col0
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					int col = this.size-1;
					Facelet[] line = getCol(adjacentFaces[0], col-layer);

					for(int i=0; i<adjacentFaces.length; i++){
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							col = i == adjacentFaces.length-2 ? 0+layer : this.size-1-layer;
							int index = i == adjacentFaces.length-2 ? ((line.length-j-1)*this.size)+col : (j*this.size)+col;
							
							Facelet temp = this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}
				break;
			case 'F':
				//[678]		[036]	[012]	[258]
				//U=last row, R=col0, D=row0, L=last col
				//TODO Front Counter-Clockwise
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					//[R9 R6 R3] [D9 D8 D7] [L7 L4 L1] [U1 U2 U3]
					int lineIndex =  this.size-1;
					Facelet[] line = getRow(adjacentFaces[0], lineIndex-layer);

					for(int i=adjacentFaces.length; i>0; i--){
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							//get last row/col of the last two layers
							lineIndex = (i == 2 || i == 3) ? 0+layer : this.size-1-layer;
							//reverse the order of  the last two layers
							int k = (i == 1 || i == 2) ? j : (this.size-j-1);
							int index = i%2 == 0 ? (k*this.size)+lineIndex : (lineIndex*this.size)+k;

							Facelet temp = this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}
				
				break;
			case 'L':
				//r[630]		[258]
				//U=col0, F=col0, D=col0, B=last col
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					int col = 0;
					Facelet[] line = getCol(adjacentFaces[0], col+layer);

					for(int i=adjacentFaces.length; i>0; i--){
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							//Note order: U, F, D, B
							//-2 because the BACK layer is the special case that happens D -> B
							col = i == adjacentFaces.length ? this.size-1-layer :  0+layer;
							int index = i == adjacentFaces.length ? ((line.length-j-1)*this.size)+col : (j*this.size)+col;
							Facelet temp = this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+3)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}
				
				break;
			case 'D':
				//[012]
				//R=last row, F=last row, L=last row, B=last row
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					int row = this.size-1;
					Facelet[] nextRow = getRow(adjacentFaces[0], row-layer);

					for(int i=0; i<adjacentFaces.length; i++){

						//each face replace proper row or col
						for(int j=0; j<nextRow.length; j++) {
							Facelet temp = this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][((row-layer)*this.size)+j];
							this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][((row-layer)*this.size)+j] = nextRow[j];
							nextRow[j] = temp;
						}
					}
				}

				break;
			case 'B':
				//[012]		[258]		r[876]	r[630]
				//U=row0, R=last col, D=last row, L=col 0
				for(int layer=startLayer; layer<startLayer+layerCount; layer++) {
					
					int lineIndex = 0;
					Facelet[] line = getRow(adjacentFaces[0], lineIndex+layer);

					for(int i=0; i<adjacentFaces.length; i++){
						
						//each face replace proper row or col
						for(int j=0; j<line.length; j++) {
							//get last row/col of the last two layers (L U)
							lineIndex = i >= 2 ? 0+layer : this.size-1-layer;
							//reverse the order of  the last two layers (D L)
							int k = (i == 1 || i == 2) ? (this.size-j-1) : j;
							int index = i%2 == 0 ? (k*this.size)+lineIndex : (lineIndex*this.size)+k;

							Facelet temp = this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index];
							this.cube[adjacentFaces[(i+1)%adjacentFaces.length]][index] = line[j];
							line[j] = temp;
						}
					}
				}
				break;
		}
	}
	
	private void rotateCubeClockwise(int face) {
		rotateFaceClockwise(face);
		rotateFaceCounterClockwise(getOppositeFace(face));
		rotateLayersClockwise(face, this.size, 0);

	}
	
	private void rotateCubeCounterClockwise(int face) {
		rotateFaceCounterClockwise(face);
		rotateFaceClockwise(getOppositeFace(face));
		rotateLayersCounterClockwise(face, this.size, 0);

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
		
		int maxDisplaySize = displayLocation ? (this.size * this.size + "").length() + 2 : 2;

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