package viz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class UpperDisplay extends JPanel {

    private JPanel upperDisplayPanel;

    private final int WIDTH = 832;
    private final int HEIGHT = 128;

    public UpperDisplay(){
        this.upperDisplayPanel = new JPanel();

        Color backgroundColor = UIManager.getColor("Panel.background");

        this.upperDisplayPanel.setBackground(backgroundColor);
        this.upperDisplayPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        Components displayComponents = new Components(this.WIDTH, backgroundColor);
        Components controlComponents = new Components(this.WIDTH/6, backgroundColor);

        Box verticalBox = Box.createVerticalBox();
        Box horizontalControls = Box.createHorizontalBox();

        verticalBox.add(displayComponents.createDetailLabel("Scramble: ", "F2 U L F D B L\' F R2 U R U\' L U2 D", 22));

        horizontalControls.add(Box.createHorizontalStrut(256));
        horizontalControls.add(Box.createHorizontalGlue());
        horizontalControls.add(controlComponents.createButton("Previous", 56, new previousButtonListener()));
        horizontalControls.add(controlComponents.createButton("Next", 56, new nextButtonListener()));
        horizontalControls.add(Box.createHorizontalGlue());
        horizontalControls.add(Box.createHorizontalStrut(256));

        verticalBox.add(horizontalControls);

        this.upperDisplayPanel.add(verticalBox);

    }

    public JPanel getPanel(){
        return this.upperDisplayPanel;
    }

    private class previousButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	System.out.println("Previous");
        }
    }
    

    private class nextButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
        	System.out.println("Next");
        }
    }
    


}