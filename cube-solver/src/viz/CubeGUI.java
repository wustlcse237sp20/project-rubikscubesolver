package viz;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import viz.*;

public class CubeGUI extends JFrame{

	public CubeGUI() {
	    super(" Rubik's Cube GUI ");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLayout(new BorderLayout());
	    
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
	}
	

}

