package viz;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import cube.*;


public class LowerControls extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel lowerControlsPanel;

    private final int WIDTH = 832;
    private final int HEIGHT = 144;

    Color backgroundColor;

    final char[] FACES = new char[] {'U','R', 'F', 'D', 'L', 'B'};
    final char[] MOVE_NOTATION = new char[]{' ', '\''};

    public LowerControls(CubePanel cubePanel){
        this.lowerControlsPanel = new JPanel();

        this.backgroundColor = UIManager.getColor("Panel.background");

        this.lowerControlsPanel.setBackground(backgroundColor);
        // this.lowerControlsPanel.setBackground(Color.BLUE);
        this.lowerControlsPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        this.updateLowerControls(cubePanel);
    }

    public void updateLowerControls(CubePanel cubePanel){
        this.lowerControlsPanel.removeAll();
        Components controlComponents = new Components(96, this.backgroundColor);

        List<Move> validMoves = Move.getSimpleCubeMoves(cubePanel.getCube().getSize());

        final int MAX_BUTTON_ROW = 12;
        int horizontalRowCount = (validMoves.size()+MAX_BUTTON_ROW-1)/MAX_BUTTON_ROW;

        JPanel movePanel = new JPanel(new GridLayout(horizontalRowCount, MAX_BUTTON_ROW));
        movePanel.setSize(this.WIDTH, this.HEIGHT);
        for(Move move: validMoves){
            movePanel.add(controlComponents.createButton(move.toString(), 56, (event) -> {
                cubePanel.applyMove(move.toString());
            }));
        }
        this.lowerControlsPanel.add(movePanel);
        this.lowerControlsPanel.repaint();
    }

    public JPanel getPanel(){
        return this.lowerControlsPanel;
    }
}