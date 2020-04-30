package viz;

import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import cube.*;




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

    private Algorithm solution;
    private int solutionIndex;


    public UpperDisplay(CubePanel cubePanel){
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

        this.nextButton = controlComponents.createButton("Next", 56, (event) -> {
            System.out.println("Next");
            Cube cube = cubePanel.getCube();
            List<Move> solutionList = this.solution.getMoveList();

            if(this.solutionIndex < solutionList.size()){
                Move move = (Move)solutionList.toArray()[this.solutionIndex];
                System.out.println("i = " + this.solutionIndex + " "+move.toString());
                this.solutionIndex++;
                cube.rotate(move);
                cubePanel.repaint();
                cubePanel.setCube(cube);
            }
            
            
        });
        this.previousButton = controlComponents.createButton("Previous", 56, (event)->{
            System.out.println("Previous");
            Cube cube = cubePanel.getCube();
            
            if(this.solutionIndex > 0){
                this.solutionIndex--;


                List<Move> solutionList = this.solution.getMoveList();
                Move prevMove = (Move)solutionList.toArray()[this.solutionIndex];
                Move inverse = prevMove.getInverse();
                System.out.println(prevMove.toString());
                System.out.println(inverse.toString());
                System.out.println("Solution index "+ this.solutionIndex);
                cube.rotate(inverse);
                cubePanel.setCube(cube);
                cubePanel.repaint();
            }
        });

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

    public void setSolution(Algorithm algorithm){
        this.solution = algorithm;
        this.solutionIndex = 0;
    }


    


}