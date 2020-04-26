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

    
    public Settings() {
        //init  panel
        this.settingsPanel = new JPanel();
        this.settingsPanel.setBackground(Color.WHITE);
        this.settingsPanel.setPreferredSize(new Dimension(256, 768));

        Box box = Box.createVerticalBox();
    
        box.add(createLabel("     Settings     ", 18, Font.BOLD));
        box.add(createButton("     Scramble     ", new scrambleButtonListener()));
        box.add(createButton("       Solve      ", new solveButtonListener()));
        box.add(createLabel(" Select Cube Size ", 16, Font.PLAIN));
        box.add(createChoice(createCubeChoices(2,7)));

        this.settingsPanel.add(box);
    }

    public JPanel getPanel(){
        return this.settingsPanel;
    }


    private JButton createButton(String label, ActionListener action){
        JButton button = new JButton(label);
        button.setSize(new Dimension(128, 128));
        //add action listeners 
        button.addActionListener(action);
        return button;
    }

    private JLabel createLabel(String text, int fontSize, int fontType){
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        Font font = new Font("Airal", fontType, fontSize);
        label.setFont(font.deriveFont(font.getStyle() | fontType));
        return label;
    }

    private List<String> createCubeChoices(int lowerCubeSize, int upperCubeSize){
        List<String> validCubeChoices = new LinkedList<String>();
        for(int cubeSize=lowerCubeSize; cubeSize<=upperCubeSize; cubeSize++){
            validCubeChoices.add(String.format(" %d x %d ", cubeSize, cubeSize));
        }
        return validCubeChoices;
    }

    private Choice createChoice(List<String> validChoices){
        Choice choice = new Choice();
        for(String option: validChoices){
            choice.add(option);
        }
        return choice;
    }

    private class scrambleButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	System.out.println("Scramble");
        }
    }
	
	private class solveButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	System.out.println("Solve");
        }
	}
}