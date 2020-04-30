package viz;

import java.awt.*;
import javax.swing.*;
import cube.*;


public class CubePanel extends JPanel {

	private static final long serialVersionUID = 5834042018320178484L;
	
	private Cube cube;
	private int faceletSize;

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

	public CubePanel(Cube cube){
		super();
		this.cube = cube;
		this.faceletSize = calculateFaceletSize(this.cube.getSize());
	}

	public Cube getCube(){
		return new Cube(this.cube);
	}

	public void setCube(Cube cube){
		this.cube = new Cube(cube);
		this.faceletSize = calculateFaceletSize(this.cube.getSize());
	}

	/**
	 * Returns the size of the optimal facelet size for the cube panel.
	 * Uses an equation derived from 3 different cube sizes using the max
	 * facelet size. Equation derived is shown below:
	 * 
	 * 	y = 0.8750x^2 - 16x + 94.13
	 * 
	 * @param cubeSize
	 * @return faceletSize
	 */
	private int calculateFaceletSize(int cubeSize){
		return (int)((0.8750*cubeSize*cubeSize) - (16*cubeSize) + 94.13);
	}

	/**
	* This method takes in a CubeColor and converts it to a Color Object
	* @param cubeColor
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

    /*
	 * This method is needed to draw something on JPanel other than drawing the background color.
     * 
     * Implements the paintComponent of a JPanel class.
	 * The paintComponent takes one argument, graphics g.
     * 
     * When a new CubePanel is created, it will draw the panel 
     * with the code from paintComponent.
	 */
	public void paintComponent(Graphics graphics) {

		int cubePadding = 32;
		int facePadding = 4;
		int faceSize = this.faceletSize*this.cube.getSize();

		drawFace(graphics, 0, facePadding+cubePadding+faceSize, 0);
		drawFace(graphics, 4, cubePadding, facePadding+faceSize);
		drawFace(graphics, 2, facePadding+cubePadding+faceSize, facePadding+faceSize);
		drawFace(graphics, 1, facePadding*2+cubePadding+faceSize*2, facePadding+faceSize);
		drawFace(graphics, 5, facePadding*3+cubePadding+faceSize*3, facePadding+faceSize);
		drawFace(graphics, 3, facePadding+cubePadding+faceSize, facePadding*2+faceSize*2);
	}
	
	private void drawFace(Graphics graphics, int faceIndex, int faceX, int faceY){
		//spacing details
		int spacing = 2;
		int spacingOffset = 2;

		int rectSize = this.faceletSize-spacingOffset*spacing;

		int cubeSize = this.cube.getSize();
		Facelet[] currentFace = this.cube.getFace(faceIndex);

		for (int i = 0; i < cubeSize; i++){
			for (int j = 0; j < cubeSize; j++){
				//set the proper sticker color
				graphics.setColor(this.convertCubeColorToColor(currentFace[i*cubeSize+j].getColor()));

				//calcualte coordinates
				int x = faceX + (spacing+j*this.faceletSize);
				int y = faceY + (spacing+i*this.faceletSize+this.faceletSize)-26;
				
				//make a square: x, y, width, height
				graphics.fillRect(x, y, rectSize, rectSize);
			}
		}
	}
}