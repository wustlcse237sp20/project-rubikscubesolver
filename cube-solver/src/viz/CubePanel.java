package viz;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

import cube.*;


public class CubePanel extends JPanel {

    /*
	 * RGB defined colors of Rubik's Cube
	 */
    Color[] rubiksColors = new Color[] {
		new Color(255,255,255), //white
		new Color(0, 70, 173), //blue
		new Color(183, 18, 52), //red
		new Color(255, 213, 0), //yellow
		new Color(0, 155, 72), //green
		new Color(255, 88, 0) //orange
	};
	
	private Cube cube;

	public CubePanel(Cube cube){
		super();
		this.cube = cube;
	}
	/*
	* This method takes in a CubeColor and converts it to a Color Object
	* @params cubeColor
	* @return Color
	*/
	private Color convertCubeColorToColor(CubeColor cubeColor){
		switch(cubeColor){
			case WHITE: 
				return rubiksColors[0];
			case BLUE:
				return rubiksColors[1];
			case RED:
				return rubiksColors[2];
			case YELLOW:
				return rubiksColors[3];
			case GREEN:
				return rubiksColors[4];
			case ORANGE:
				return rubiksColors[5];
			default: 
				return UIManager.getColor("Panel.background");
		}
	}

	private void setFaceIndex(int face, int xCord, int yCord){
		//TODO: set face index 
	}

    /*
	 * This method is needed to draw something on JPanel other than drawing the background color.
     * 
     * Implements the paintComponent of a JPanel class.
	 * The paintComponent takes one argument, graphics g.
     * 
     * When a new CubePanel is created, it will draw the panel 
     * with the code from paintComponent.
	 */
	public void paintComponent(Graphics g) {
		//square size and spacing details
		int spacing = 2;
		int squareSize = 40;
		int spacingOffset = 2;
		int gridSize = 15;
		int randomSwitch = 0;
		int cubeSize = this.cube.getSize();
		int faceIndex = 1;
		Facelet[] currentFace = this.cube.getFace(faceIndex);

		g.setColor(Color.WHITE);
		for (int i = 0; i < cubeSize; i++){
			for (int j = 0; j < cubeSize; j++){
				//x, y, width, height
				g.setColor(this.convertCubeColorToColor(currentFace[i*cubeSize+j].getColor()));
				g.fillRect(spacing+i*squareSize, spacing+j*squareSize+squareSize, squareSize-spacingOffset*spacing, squareSize-spacingOffset*spacing);
				randomSwitch++;
			}
		}
    }
}