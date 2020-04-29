package viz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class UpperDisplay extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel upperDisplayPanel;

    private final int WIDTH = 832;
    private final int HEIGHT = 96;

    private Color backgroundColor;

    private JPanel displayPanel;
    private Box verticalBox;

    private JButton nextButton;
    private JButton previousButton;


    public UpperDisplay(){
        this.upperDisplayPanel = new JPanel();

        this.backgroundColor = UIManager.getColor("Panel.background");

        this.upperDisplayPanel.setBackground(backgroundColor);
        // this.upperDisplayPanel.setBackground(Color.BLUE);
        this.upperDisplayPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        Components displayComponents = new Components(this.WIDTH, backgroundColor);
        Components controlComponents = new Components(this.WIDTH/6, backgroundColor);

        this.verticalBox = Box.createVerticalBox();
        Box horizontalControls = Box.createHorizontalBox();

        this.displayPanel = displayComponents.createDetailLabel("", "", 18);
        verticalBox.add(this.displayPanel);

        horizontalControls.add(Box.createHorizontalStrut(256));
        horizontalControls.add(Box.createHorizontalGlue());

        this.nextButton = controlComponents.createButton("Next", 56, new nextButtonListener());
        this.previousButton = controlComponents.createButton("Previous", 56, new previousButtonListener());

        horizontalControls.add(previousButton);
        horizontalControls.add(nextButton);
        horizontalControls.add(Box.createHorizontalGlue());
        horizontalControls.add(Box.createHorizontalStrut(256));

        verticalBox.add(horizontalControls);

        this.upperDisplayPanel.add(verticalBox);
        this.hideButtons();

    }

    public JPanel getPanel(){
        return this.upperDisplayPanel;
    }

    public void hideButtons(){
        this.nextButton.setVisible(false);
        this.previousButton.setVisible(false);
    }

    public void showButtons(){
        this.nextButton.setVisible(true);
        this.previousButton.setVisible(true);
    }

    public void setDisplayMessage(String boldText, String text){
        this.displayPanel.setVisible(false);
        Components displayComponents = new Components(this.WIDTH, backgroundColor);
        this.verticalBox.remove(this.displayPanel);
        this.displayPanel = displayComponents.createDetailLabel(boldText, text, 18);
        this.verticalBox.add(this.displayPanel, 0);
        this.displayPanel.setVisible(true);

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