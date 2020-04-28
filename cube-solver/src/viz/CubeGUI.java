package viz;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.tools.*;
import java.net.URL;
import javax.imageio.*;
import viz.*;

public class CubeGUI extends JFrame{

	public CubeGUI() {
	    super(" Rubik's Cube Explorer ");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		//change default icon
		// URL url = ClassLoader.getSystemResource(".");
		// System.out.println("URL: "+url);
		// Toolkit toolkit = Toolkit.getDefaultToolkit();
		//Image icon = toolkit.getImage(this.getClass().getResource("image.png"));

		// Image icon = toolkit.createImage(url);
		// this.setIconImage(icon);

		// ImageIcon icon = new ImageIcon(".//assets//icons//cube.png");
		// this.setIconImage(icon.getImage());
		Image icon = null;
		try{    
			//String filepath = "/Users/brad/git/project-rubikscubesolver/cube-solver/src/viz/assets/icons/cube.png";
			// String filepath = "./cube.png";
			// this.setIconImage(new ImageIcon(getClass().getResource(filepath)).getImage());
			// this.setIconImage(new ImageIcon(this.getClass().getResource(filepath)).getImage());
			String imagePath = "/cube.png";
			URL imageUrl = CubeGUI.class.getResource(imagePath);
			icon = Toolkit.getDefaultToolkit().getImage(imageUrl);
		}
	 	catch (Exception exception){
			//do something
			System.out.println("Error loading icon: " + exception.getMessage());
		}
		if(icon != null){
			this.setIconImage(icon);
		}


	    
        JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
        
		//create panel for settings
		Settings settings = new Settings();
		
		//create panel for cube
		CubePanel cubeArea = new CubePanel();

    	// Add the panel to this JFrame
		mainPanel.add(settings.getPanel(), BorderLayout.WEST);
		mainPanel.add(cubeArea);

		this.add(mainPanel);
		
        // Size this JFrame 
        this.setSize(1024,768);
       
        // Make this JFrame visible
		this.setVisible(true);
		
	}

	public static void main(String[] args) {
		new CubeGUI();
      
		// BufferedReader reader = new BufferedReader(new FileReader(file));
		// String currentLine = reader.readLine();
		// reader.close();
		// System.out.println();
	}
	

}

