package viz;

import java.util.*;

import cube.Cube;

public class CubeExplorer {

	public static void main(String[] args) {
		
		Scanner keyBoard = new Scanner(System.in);
		int cubeSize = getUsersCubeSize(keyBoard);
		
		Cube cube = new Cube(cubeSize);
		displayCubeUsageMessage();
		System.out.println(cube);


	}

	private static void displayCubeUsageMessage() {
		System.out.println("\nType \"scramble\" to mix the cube up");
		System.out.println("Type \"solve\" to solve the cube\n");
		
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

}
