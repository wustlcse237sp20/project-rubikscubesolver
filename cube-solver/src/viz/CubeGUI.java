package viz;

import java.awt.*;
import javax.swing.*;
import java.net.URL;

import viz.*;

public class CubeGUI extends JFrame{

	public CubeGUI() {
	    super(" Rubik's Cube Explorer ");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
        //Set GUI dock icon
        try {
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final URL imageUrl = this.getClass().getClassLoader().getResource("cube.png");
			final Image icon = toolkit.getImage(imageUrl);
			final Taskbar taskbar = Taskbar.getTaskbar();

            //sets icon for mac
			taskbar.setIconImage(icon);

			//set icon for windows
			this.setIconImage(icon);

        } catch (Exception exception) {
            System.out.println("Error loading icon: " + exception.getMessage());
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
	}

}

