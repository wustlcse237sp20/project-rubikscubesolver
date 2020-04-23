package viz;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class CubeGUI extends JFrame{
	
	private JButton scrambleButton;
	private JButton solveButton;
	private JPanel upperPanel;
	private Choice sizeChoice;
	
	
	
	public CubeGUI() {
	    super(" Rubik's Cube GUI ");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLayout(new BorderLayout());
	    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        scrambleButton = new JButton(" Scramble ");
        solveButton = new JButton(" Solve ");
        
        upperPanel = new JPanel();
    	upperPanel.setLayout(new BorderLayout());
    	
    	
    	//add option for game bored size
    	sizeChoice = new Choice();
    	sizeChoice.add(" 2 x 2 ");
    	sizeChoice.add(" 3 x 3 ");
    	sizeChoice.add(" 4 x 4 ");
    	sizeChoice.add(" 5 x 5 ");
    	sizeChoice.add(" 6 x 6 ");
    	sizeChoice.add(" 7 x 7 ");
    	
    	upperPanel.add(sizeChoice, BorderLayout.NORTH);
    	upperPanel.add(scrambleButton, BorderLayout.CENTER);
    	upperPanel.add(solveButton, BorderLayout.SOUTH);

    	//add action listeners 
    	scrambleButton.addActionListener(new scrambleButtonListener());
    	solveButton.addActionListener(new solveButtonListener());
 
        
        
    	// Add the panel to this JFrame
    	mainPanel.add(upperPanel, BorderLayout.NORTH);
        this.add(mainPanel);

        // Size this JFrame 
        this.setSize(800,400);
       
        // Make this JFrame visible
        this.setVisible(true);
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

	public static void main(String[] args) {
		new CubeGUI();
	}
	
	
}

