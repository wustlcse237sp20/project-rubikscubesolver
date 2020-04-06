package cube;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to represent moves on a NxNxN cube
 * 
 * @author Brad Hodkinson
 */
public class Move {

	final String FACES = "URFDLB";

	final static String FACE_TURN_NOTATION = "^([UDRLFB])([2']?)$";
	final static String MIDDLE_TURN_NOTATION = "^([MES])([2']?)$";
	final static String CUBE_ROTATION_NOTATION = "^([xyz])([2']?)$";
	final static String INNER_SLICE_NOTATION = "^([udrlfb])([2']?)$";
	final static String WIDE_TURN_NOTATION = "^([\\d]*)([UDRLFB])[w]([2']?)$";

	String move;

	int face;

	static boolean innerRotation;
	boolean doubleRotation;
	boolean counterClockwise;
	boolean cubeRotation;
	boolean middleRotation;

	static int layerCount;

	public Move(String move) {
		// Remove leading and trailing spaces
		move = move.strip();
		// check if the move is valid
		if (checkValidMove(move)) {
			this.move = move;
		} else {
			System.out.println("Not a valid move");
		}
	}
	
	
	
	private boolean checkValidMove(String move) {
		return checkFaceTurn(move) | checkMiddleTurn(move) | checkCubeRotation(move) | checkInnerTurn(move)
				| checkWideTurn(move);
	}

	private boolean checkFaceTurn(String move) {
		Pattern face_turn_pattern = Pattern.compile(FACE_TURN_NOTATION);
		Matcher face_turn_matcher = face_turn_pattern.matcher(move);
		if (face_turn_matcher.find()) {
			this.face = FACES.indexOf(face_turn_matcher.group(1));
			this.innerRotation = false;
			this.counterClockwise = face_turn_matcher.group(2).equals("\'");
			this.doubleRotation = face_turn_matcher.group(2).equals("2");
			this.cubeRotation = false;
			this.middleRotation = false;
			this.layerCount = 1;
			return true;
		}
		return false;
	}

	private boolean checkMiddleTurn(String move) {
		String middleMoves = "SEM";

		Pattern middle_turn_pattern = Pattern.compile(MIDDLE_TURN_NOTATION);
		Matcher middle_turn_matcher = middle_turn_pattern.matcher(move);
		if (middle_turn_matcher.find()) {
			this.face = middleMoves.indexOf(middle_turn_matcher.group(1)) + 2;
			this.innerRotation = true;
			this.counterClockwise = middle_turn_matcher.group(2).equals("\'");
			this.doubleRotation = middle_turn_matcher.group(2).equals("2");
			this.cubeRotation = false;
			this.middleRotation = true;
			this.layerCount = 1;
			return true;
		}
		return false;
	}

	private boolean checkCubeRotation(String move) {
		String cubeRotations = "yxz";
		Pattern cube_rotation_pattern = Pattern.compile(CUBE_ROTATION_NOTATION);
		Matcher cube_rotation_matcher = cube_rotation_pattern.matcher(move);
		if (cube_rotation_matcher.find()) {
			this.face = cubeRotations.indexOf(cube_rotation_matcher.group(1));
			this.innerRotation = false;
			this.counterClockwise = cube_rotation_matcher.group(2).equals("\'");
			this.doubleRotation = cube_rotation_matcher.group(2).equals("2");
			this.cubeRotation = true;
			this.middleRotation = false;
			this.layerCount = 0;
			return true;
		}
		return false;
	}

	private boolean checkInnerTurn(String move) {
		Pattern inner_turn_pattern = Pattern.compile(INNER_SLICE_NOTATION);
		Matcher inner_turn_matcher = inner_turn_pattern.matcher(move);
		if (inner_turn_matcher.find()) {
			this.face = FACES.indexOf(inner_turn_matcher.group(1).toUpperCase());
			this.innerRotation = true;
			this.counterClockwise = inner_turn_matcher.group(2).equals("\'");
			this.doubleRotation = inner_turn_matcher.group(2).equals("2");
			this.cubeRotation = false;
			this.middleRotation = false;
			this.layerCount = 1;
			return true;
		}
		return false;
	}

	private boolean checkWideTurn(String move) {
		Pattern wide_turn_pattern = Pattern.compile(WIDE_TURN_NOTATION);
		Matcher wide_turn_matcher = wide_turn_pattern.matcher(move);
		if (wide_turn_matcher.find()) {
			String numberLayer = wide_turn_matcher.group(1);
			numberLayer = numberLayer.length() == 0 ? "2" : numberLayer;
			this.layerCount = Integer.parseInt(numberLayer);
			this.face = FACES.indexOf(wide_turn_matcher.group(2));
			this.innerRotation = false;
			this.counterClockwise = wide_turn_matcher.group(3).equals("\'");
			this.doubleRotation = wide_turn_matcher.group(3).equals("2");
			this.cubeRotation = false;
			this.middleRotation = false;
			return true;
		}
		return false;
	}

	public String getMove() {
		return move;
	}

	public int getFace() {
		return face;
	}

	public boolean isInnerRotation() {
		return innerRotation;
	}

	public boolean isDoubleRotation() {
		return doubleRotation;
	}

	public boolean isCounterClockwise() {
		return counterClockwise;
	}
	
	public boolean isMiddleRotation() {
		return middleRotation;
	}

	public boolean isCubeRotation() {
		return cubeRotation;
	}

	public int getLayerCount() {
		return layerCount;
	}

	/*
	 * Checks if a move is valid according to the WCA Cube Notation Standards.
	 * 
	 * Notation Reference Article 12 Section 12a:
	 * https://www.worldcubeassociation.org/regulations/#article-12-notation
	 * 
	 * @param move string to check
	 * @return true if move is valid, else false
	 */
	public boolean isValidMove(String move) {

		return move.matches(FACE_TURN_NOTATION) | move.matches(MIDDLE_TURN_NOTATION)
				| move.matches(CUBE_ROTATION_NOTATION) | move.matches(INNER_SLICE_NOTATION)
				| move.matches(WIDE_TURN_NOTATION);

	}

	/*
	 * Checks if a move is valid according to the WCA Cube Notation Standards.
	 * 
	 * Notation Reference Article 12 Section 12a:
	 * https://www.worldcubeassociation.org/regulations/#article-12-notation
	 * 
	 * @param move string to check
	 * @param cubeSize to check if the move is possible given the cube size
	 * @return true if move is valid, else false
	 */
	public static boolean isValidMove(String move, int cubeSize) {
		//can't turn more layers than the size of the cube
		if(layerCount > cubeSize) {
			return false;
		}
		//can't have an inner rotation if cube size less than or equal to 2
		if(innerRotation && cubeSize <= 2) {
			return false;
		}
		//can't have a middle move if even cube
		if(move.matches(MIDDLE_TURN_NOTATION) && cubeSize%2==0) {
			return false;
		}
		//make sure the move conforms to the WCA notation standards
		return move.matches(FACE_TURN_NOTATION) | move.matches(MIDDLE_TURN_NOTATION)
				| move.matches(CUBE_ROTATION_NOTATION) | move.matches(INNER_SLICE_NOTATION)
				| move.matches(WIDE_TURN_NOTATION);

	}

	public String toString() {
		return this.move;
	}

}
