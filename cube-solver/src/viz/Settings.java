package viz;

import java.util.LinkedList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * Creates the settings panel for the cube solver GUI
 * 
 * @author Brad Hodkinson
 */
public class Settings {

    private JPanel settingsPanel;
    
    private final int WIDTH = 192;
    private final int HEIGHT = 768;

    
    public Settings() {
        //init  panel
        this.settingsPanel = new JPanel();
        this.settingsPanel.setBackground(Color.WHITE);
        this.settingsPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        Components components = new Components(this.WIDTH, Color.WHITE);
        
        Box box = Box.createVerticalBox();
    
        box.add(components.createLabel(" Settings ", 32, Font.BOLD));
        box.add(components.createButton(" Scramble ", 64, new scrambleButtonListener()));
        box.add(components.createButton("   Reset   ", 64, new resetButtonListener()));
        box.add(components.createButton("   Solve   ", 64, new solveButtonListener()));
        box.add(components.createLabel(" Select Cube Size ", 18, Font.PLAIN));
        box.add(components.createChoice(createCubeChoices(2,7), 64));

        this.settingsPanel.add(box);
    }

    public JPanel getPanel(){
        return this.settingsPanel;
    }

    private List<String> createCubeChoices(int lowerCubeSize, int upperCubeSize){
        List<String> validCubeChoices = new LinkedList<String>();
        for(int cubeSize=lowerCubeSize; cubeSize<=upperCubeSize; cubeSize++){
            validCubeChoices.add(String.format("  %d x %d  ", cubeSize, cubeSize));
        }
        return validCubeChoices;
    }

    private class scrambleButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	System.out.println("Scramble");
        }
    }
    
    private class resetButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	System.out.println("Reset");
        }
    }

	private class solveButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	System.out.println("Solve");
        }
	}
}