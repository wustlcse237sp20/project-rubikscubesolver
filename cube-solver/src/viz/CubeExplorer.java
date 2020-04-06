package viz;

import java.util.*;

import cube.Algorithm;
import cube.Cube;
import cube.Move;

public class CubeExplorer {

	public static void main(String[] args) {
		
		Scanner keyBoard = new Scanner(System.in);
		int cubeSize = getUsersCubeSize(keyBoard);
		
		Cube cube = new Cube(cubeSize);
		displayCubeUsageMessage();
		System.out.println(cube);
		startExplorer(cube);

	}

	
	
	private static void displayCubeUsageMessage() {
		System.out.println("\nType \"scramble\" to mix the cube up");
		System.out.println("Type \"solve\" to solve the cube");
		System.out.println("Type \"new\" to generate a new cube");
		System.out.println("Type \"size\" to change the size of the cube");
		System.out.println("Type \"usage\" to view usage message");
		System.out.println("Type \"quit\" to quit Cube Explorer\n");
		
		
		System.out.println("Enter valid notation to twist the cube. Notation listed below:");
		String[] faces = new String[]{"up", "right", "front", "down", "left", "back"};
		for(String face: faces) {
			System.out.print(face.charAt(0) + " - rotate " + face + " face clockwise\t\t");
			System.out.println(face.toUpperCase().charAt(0) + " - rotate " + face + " face counter-clockwise");
		}
		System.out.println();
	}
	
	
	
	private static int getUsersCubeSize(Scanner keyBoard ) {
		String input = "";
		boolean validInput = false;
		while(!validInput) {
			System.out.println("Enter a cube size: ");
			input = keyBoard.nextLine();
			validInput = isValidCubeSize(input);
			if(!validInput) {
				System.out.println("Invalid input. Please enter a number between 1 and 17");
			}
		}
		return Integer.parseInt(input);
		
	}
	
	
	
	private static boolean isValidCubeSize(String input) {
		int cubeSize = Integer.parseInt(input);			
		return cubeSize >= 1 || cubeSize <= 17;
	}
	
	
	
	private static Cube scrambleCube(Cube cube) {
		int cubeSize = cube.getSize();
		int scrambleLength = 20;
		Algorithm algo = new Algorithm();
		algo.generateScramble(cubeSize, scrambleLength);
		cube.applyAlgorithm(algo);
		return cube;
	}
	
	
	/**
	 * Takes user input and 
	 * @param cube
	 */
	private static void startExplorer(Cube cube) {
		Scanner keyBoard = new Scanner(System.in);
		String input = keyBoard.nextLine();
		boolean cubeChanged = false;
		
		while(!input.equals("quit")) {
			if(input.equals("scramble")) {
				cube = scrambleCube(cube);
				cubeChanged = true;
			}
			else if(input.equals("solve")) {
				System.out.println("Sorry, this feature is not yet available");
				System.out.println("To generate a new cube type \"new\"\n");
				
			}
			else if(input.equals("new")) {
				int cubeSize = cube.getSize();
				cube = new Cube(cubeSize);
				cubeChanged = false;
			}
			else if(input.equals("size")) {
				int cubeSize = getUsersCubeSize(keyBoard);
				cube = new Cube(cubeSize);
				cubeChanged = false;
			}
			else if(input.equals("usage")) {
				displayCubeUsageMessage();
			}
			
			else {
				boolean isValidMove = isValidMove(input);
				if(isValidMove) {
					cube.rotate(new Move(input));
					cubeChanged = true;
				}
				else {
					System.out.println("Not a valid move. Type \"usage\" to view program usage");
				}
				
			}
			cubeChanged = didSolve(cube, cubeChanged);
			System.out.println(cube);
			input = keyBoard.nextLine();
		}
		
		System.out.println("User Quit");
	}
	
	
	
	public static boolean didSolve(Cube cube, boolean cubeChanged) {
		System.out.println(cubeChanged);
		if(cubeChanged) {
			if(cube.isSolved()) {
				System.out.println("CONGRATS, YOU SOLVED THE CUBE");
				cubeChanged =false;
			}
		}
		return cubeChanged;
	}
	
	
	
	public static boolean isValidMove(String move) {
		String FACE_TURN_NOTATION = "^([UDRLFB])([2']?)$";
		String MIDDLE_TURN_NOTATION = "^([MES])([2']?)$";
		String CUBE_ROTATION_NOTATION = "^([xyz])([2']?)$";
		String INNER_SLICE_NOTATION = "^([udrlfb])([2']?)$";
		String WIDE_TURN_NOTATION = "^([\\d]*)([UDRLFB])[w]([2']?)$";
		return move.matches(FACE_TURN_NOTATION) | move.matches(MIDDLE_TURN_NOTATION)
				| move.matches(CUBE_ROTATION_NOTATION) | move.matches(INNER_SLICE_NOTATION)
				| move.matches(WIDE_TURN_NOTATION);
	}
	

}
