package cube;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class used to represent moves in an algorithm
 * @author Brad Hodkinson
 */
public class Algorithm {
	
	final String FACES = "URFDLB";

	
	List<Move> moveList;
	
	public Algorithm() {
		this.moveList = new LinkedList<Move>();
	}
	
	public Algorithm(String moves) {
		List<Move> moveList = new LinkedList<Move>();
		for(String move : moves.split("\\s+")) {
			moveList.add(new Move(move));
		}
		this.moveList = moveList;
		
	}
	
	public Algorithm(List<Move> moveList) {
		this.moveList = moveList;
	}
	
	public void generateScramble(int cubeSize, int scrambleLength) {
		for(int i=0; i<scrambleLength; i++) {
			//make sure next move is different face then the last move
			if(i >= 1) {
				Move lastMove = this.moveList.get(this.moveList.size()-1);
				boolean foundNewFace = false;
				//keep searching for a new random move if the face is not the same
				while(!foundNewFace) {
					Move nextMove = generateRandomMove(cubeSize);
					if(nextMove.face != lastMove.face) {
						this.moveList.add(nextMove);
						foundNewFace = true;
					}
				}
				
			} else {
				this.moveList.add(generateRandomMove(cubeSize));

			}
		}
	}
	
	// generate random move
	private Move generateRandomMove(int cubeSize) {
		Random random = new Random();
		String move = null;
		if(cubeSize<1) {
			System.out.println("Invalid Cube Size");
		}
		else if(cubeSize == 1) {
			move = generateRandomCubeRotation(random);
		}
		//check if big cube, okay to do inner and wide moves
		else if(cubeSize > 3) {
			//check if cube size odd, okay to do middle turn
			if(cubeSize%2==1) {
				int moveType = random.nextInt(4);
				switch(moveType) {
					case 0:
						move = generateRandomMiddleMove(random);
						break;
					case 1:
						move = generateRandomFaceMove(random);
						break;
					case 2:
						move = generateRandomInnerMove(random);
						break;
					case 3:
						move = generateRandomWideMove(random, cubeSize);
						break;
					default:
						move = generateRandomFaceMove(random);
				}
			} else {
				int moveType = random.nextInt(3);
				switch(moveType) {
					case 1:
						move = generateRandomFaceMove(random);
						break;
					case 2:
						move = generateRandomInnerMove(random);
						break;
					case 3:
						move = generateRandomWideMove(random, cubeSize);
						break;
					default:
						move = generateRandomFaceMove(random);
				}
			}
			
		}
		//if odd cube, okay to generate middle turn
		else if(cubeSize%2==1) {
			int moveType = random.nextInt(2);
			switch(moveType) {
				case 0:
					move = generateRandomMiddleMove(random);
					break;
				case 1:
					move = generateRandomFaceMove(random);
					break;
				default:
					move = generateRandomFaceMove(random);
			}
		}
		else {
			move = generateRandomFaceMove(random);
		}
		return new Move(move);

	}
	
	private String generateRandomFaceMove(Random random) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(FACES.charAt(random.nextInt(FACES.length())));
		stringBuilder.append(generateRandomNotationPostfix(random));
		
		return stringBuilder.toString();
		
	}
	private String generateRandomMiddleMove(Random random) {
		String[] middleMoves = new String[] {"M","E", "S"};
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(middleMoves[random.nextInt(middleMoves.length)]);
		stringBuilder.append(generateRandomNotationPostfix(random));
		return stringBuilder.toString();
	}
	private String generateRandomCubeRotation(Random random) {
		String[] cubeRotations = new String[] {"x","y", "z"};
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(cubeRotations[random.nextInt(cubeRotations.length)]);
		stringBuilder.append(generateRandomNotationPostfix(random));
		return stringBuilder.toString();
		
	}
	private String generateRandomInnerMove(Random random) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(FACES.charAt(random.nextInt(FACES.length())));
		stringBuilder.append(generateRandomNotationPostfix(random));
		
		return stringBuilder.toString().toLowerCase();
	}
	private String generateRandomWideMove(Random random, int cubeSize) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(random.nextInt((cubeSize/2)-1)+2);
		stringBuilder.append(FACES.charAt(random.nextInt(FACES.length())));
		stringBuilder.append("w");
		stringBuilder.append(generateRandomNotationPostfix(random));
		
		return stringBuilder.toString();
		
	}
	
	private String generateRandomNotationPostfix(Random random) {
		String[] postfixNotaionList = new String[] {"","\'", "2"};
		// Generate random integers in range 0 to 999
		return postfixNotaionList[random.nextInt(postfixNotaionList.length)];
	}
	
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for(Move move : moveList) {
			stringBuilder.append(move.toString());
			stringBuilder.append(" ");
		}
		if(stringBuilder.length() > 1) {
			//remove the last space at the end
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
		}
		
		return stringBuilder.toString();
	}

}
