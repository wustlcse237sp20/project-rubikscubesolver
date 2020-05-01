package viz;

import java.util.LinkedList;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import cube.*;
import util.*;
import solver.*;

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
    private JPanel solvePanel;

    
    public Settings(CubePanel cubePanel, UpperDisplay upperDisplay) {
        //init  panel
        this.settingsPanel = new JPanel();
        this.settingsPanel.setBackground(Color.WHITE);
        this.settingsPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        Components components = new Components(this.WIDTH, Color.WHITE);
        
        Box box = Box.createVerticalBox();
    
        box.add(components.createLabel(" Settings ", 32, Font.BOLD));
        box.add(components.createPanelButton(" Scramble ", 64, (event) -> {
            this.scrambleCube(cubePanel, upperDisplay);
        }));
        box.add(components.createPanelButton("   Reset   ", 64, (event) -> {
            this.resetCube(cubePanel, upperDisplay);
        }));
        solvePanel = components.createPanelButton("   Solve   ", 64, (event) -> {
            this.solveCube(cubePanel, upperDisplay);
        });
        box.add(solvePanel);
        box.add(components.createLabel(" Select Cube Size ", 18, Font.PLAIN));
     
        box.add(components.createComboBox(createCubeChoices(2,7), cubePanel.getCube().getSize()-2,
            new Consumer<Integer>(){
                @Override
                public void run(Integer selectedIndex){
                    upperDisplay.hideButtons();
                    upperDisplay.setDisplayMessage("", "");
                    int cubeSize = selectedIndex+2;
                    solvePanel.setVisible(cubeSize == 3);
                    Cube newCube = new Cube(cubeSize);
                    cubePanel.setCube(newCube);
                }
            }
            
        ));
        this.settingsPanel.add(box);
        cubePanel.requestFocus();
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

    private void scrambleCube(CubePanel cubePanel, UpperDisplay upperDisplay){
        upperDisplay.hideButtons();
        Algorithm scramble = new Algorithm();
        Cube cube = cubePanel.getCube();
        int cubeSize = cube.getSize();
        Cube newCube = new Cube(cubeSize);
        if(cubeSize <= 3){
            scramble = Min2PhaseUtil.simpleScramble(5, newCube);
        }
        else{
            scramble.generateScramble(cubeSize, 20);
            newCube.applyAlgorithm(scramble);
        }
        upperDisplay.setDisplayMessage("Scramble: ", scramble.toString());
        cubePanel.setCube(newCube);
    }

    private void resetCube(CubePanel cubePanel, UpperDisplay upperDisplay){
        upperDisplay.hideButtons();
        upperDisplay.setDisplayMessage("", "");
        int cubeSize = cubePanel.getCube().getSize();
        Cube newCube = new Cube(cubeSize);
        cubePanel.setCube(newCube);
    }

    private void solveCube(CubePanel cubePanel, UpperDisplay upperDisplay){
        Cube cube = new Cube(cubePanel.getCube());
        SolverContext context = new SolverContext(new Min2Phase());
        Algorithm solution = context.solveCube(cube);
        upperDisplay.setSolution(solution);
        upperDisplay.showButtons();
        upperDisplay.setDisplayMessage("Solution: ", solution.toString());
        cubePanel.requestFocus();
    }


    private void updateCubeSize(CubePanel cubePanel, int selectedIndex) {
        int cubeSize = selectedIndex+2;
        Cube newCube = new Cube(cubeSize);
        cubePanel.setCube(newCube);
    }

}