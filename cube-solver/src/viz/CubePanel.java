package viz;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.UIManager;

import cube.Cube;
import cube.CubeColor;
import cube.Facelet;


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
	private int faceletWidth;

	public CubePanel(Cube cube){
		super();
		this.cube = cube;
		this.faceletWidth = 76-(int)(7.25*this.cube.getSize());
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
		int faceSize = this.faceletWidth*this.cube.getSize();

		drawFace(graphics, 0, facePadding+cubePadding+faceSize, 0);
		drawFace(graphics, 4, cubePadding, facePadding+faceSize);
		drawFace(graphics, 2, facePadding+cubePadding+faceSize, facePadding+faceSize);
		drawFace(graphics, 1, facePadding*2+cubePadding+faceSize*2, facePadding+faceSize);
		drawFace(graphics, 5, facePadding*3+cubePadding+faceSize*3, facePadding+faceSize);
		drawFace(graphics, 3, facePadding+cubePadding+faceSize, facePadding*2+faceSize*2);
	}
	
	private void drawFace(Graphics graphics, int faceIndex, int faceX, int faceY){
		//square size and spacing details
		int spacing = 2;
		int spacingOffset = 2;

		int rectSize = this.faceletWidth-spacingOffset*spacing;

		int cubeSize = this.cube.getSize();
		Facelet[] currentFace = this.cube.getFace(faceIndex);


		graphics.setColor(Color.WHITE);
		for (int i = 0; i < cubeSize; i++){
			for (int j = 0; j < cubeSize; j++){
				//x, y, width, height
				graphics.setColor(this.convertCubeColorToColor(currentFace[i*cubeSize+j].getColor()));
				int x = faceX + (spacing+i*this.faceletWidth);
				int y = faceY + (spacing+j*this.faceletWidth+this.faceletWidth);
				graphics.fillRect(x, y, rectSize, rectSize);
				// graphics.fillRect(spacing+i*squareSize, spacing+j*squareSize+squareSize, squareSize-spacingOffset*spacing, squareSize-spacingOffset*spacing);
			}
		}
	}
}