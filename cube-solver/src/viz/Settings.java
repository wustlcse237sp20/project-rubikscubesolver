package viz;

import java.util.LinkedList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

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
    private final String FONT_NAME = "Helvetica Neue";

    
    public Settings() {
        //init  panel
        this.settingsPanel = new JPanel();
        this.settingsPanel.setBackground(Color.WHITE);
        this.settingsPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        Box box = Box.createVerticalBox();
    
        box.add(createLabel(" Settings ", 32, Font.BOLD));
        box.add(createButton(" Scramble ", new scrambleButtonListener()));
        box.add(createButton("   Reset   ", new resetButtonListener()));
        box.add(createButton("   Solve   ", new solveButtonListener()));
        box.add(createLabel(" Select Cube Size ", 18, Font.PLAIN));
        box.add(createChoice(createCubeChoices(2,7)));

        this.settingsPanel.add(box);
    }

    public JPanel getPanel(){
        return this.settingsPanel;
    }

    private JPanel createPanelComponet(Component component, int height){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(this.WIDTH, height));
        panel.setBackground(Color.white);
        panel.add(component);
        return panel;
    }

    private JPanel createButton(String label, ActionListener action){
        JButton button = new JButton(label);
        button.setFont(new Font(this.FONT_NAME, Font.PLAIN, 18));
        button.setLayout(new FlowLayout());
        button.setPreferredSize(new Dimension(this.WIDTH-32, 48));
        //add action listener
        button.addActionListener(action);
        return createPanelComponet(button, 64);
    }

    private JPanel createLabel(String text, int fontSize, int fontType){
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        Font font = new Font(this.FONT_NAME, fontType, fontSize);
        label.setFont(font.deriveFont(font.getStyle() | fontType));
        int height = (int)(fontSize*1.6);
        return createPanelComponet(label, height);
    }

    private List<String> createCubeChoices(int lowerCubeSize, int upperCubeSize){
        List<String> validCubeChoices = new LinkedList<String>();
        for(int cubeSize=lowerCubeSize; cubeSize<=upperCubeSize; cubeSize++){
            validCubeChoices.add(String.format("  %d x %d  ", cubeSize, cubeSize));
        }
        return validCubeChoices;
    }

    private JPanel createChoice(List<String> validChoices){
        Choice choice = new Choice();
        choice.setFont(new Font("Airal", Font.PLAIN, 18));
        choice.setSize(new Dimension(this.WIDTH-32, 48));
        choice.setPreferredSize(new Dimension(this.WIDTH-32, 48));
        for(String option: validChoices){
            choice.add(option);
        }
        return createPanelComponet(choice, 48);
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