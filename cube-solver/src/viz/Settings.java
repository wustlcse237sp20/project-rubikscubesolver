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
        box.add(components.createPanelButton("   Solve   ", 64, (event) -> {
            this.solveCube(cubePanel, upperDisplay);
        }));
        box.add(components.createLabel(" Select Cube Size ", 18, Font.PLAIN));
     
        box.add(components.createComboBox(createCubeChoices(2,7), cubePanel.getCube().getSize()-2,
            new Consumer<Integer>(){
                @Override
                public void run(Integer selectedIndex){
                    upperDisplay.hideButtons();
                    upperDisplay.setDisplayMessage("", "");
                    int cubeSize = selectedIndex+2;
                    Cube newCube = new Cube(cubeSize);
                    cubePanel.setCube(newCube);
                    cubePanel.repaint();
                }
            }
            
        ));

        
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

    private void scrambleCube(CubePanel cubePanel, UpperDisplay upperDisplay){
        upperDisplay.hideButtons();
        Algorithm scramble = new Algorithm();
        Cube cube = cubePanel.getCube();
        int cubeSize = cube.getSize();
        if(cubeSize <= 3){
            scramble = Min2PhaseUtil.simpleScramble(5, cube);
        }
        else{
            scramble.generateScramble(cubeSize, 20);
        }
        Cube newCube = new Cube(cubeSize);
        newCube.applyAlgorithm(scramble);
        cubePanel.setCube(newCube);
        System.out.println(scramble.toString());
        upperDisplay.setDisplayMessage("Scramble: ", scramble.toString());
        cubePanel.repaint();
    }

    private void resetCube(CubePanel cubePanel, UpperDisplay upperDisplay){
        upperDisplay.hideButtons();
        upperDisplay.setDisplayMessage("", "");
        int cubeSize = cubePanel.getCube().getSize();
        Cube newCube = new Cube(cubeSize);
        cubePanel.setCube(newCube);
        cubePanel.repaint();
    }

    private void solveCube(CubePanel cubePanel, UpperDisplay upperDisplay){
        
        Cube cube = cubePanel.getCube();

        SolverContext context = new SolverContext(new Min2Phase());
        System.out.println("Before:");
        System.out.println(cube);
        Algorithm solution = context.solveCube(cube);
        upperDisplay.setSolution(solution);
        System.out.println("After:");
        System.out.println(cube);
        System.out.println(solution.toString());
        upperDisplay.showButtons();
        upperDisplay.setDisplayMessage("Solution: ", solution.toString());

        System.out.println("Solve");
        //TODO make this solve the cube
    }

    private void updateCubeSize(CubePanel cubePanel, int selectedIndex) {
        int cubeSize = selectedIndex+2;
        Cube newCube = new Cube(cubeSize);
        cubePanel.setCube(newCube);
    }

}