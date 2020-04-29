package viz;

import java.awt.*;
import javax.swing.*;

public class LowerControls extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel lowerControlsPanel;

    private final int WIDTH = 832;
    private final int HEIGHT = 96;

    public LowerControls(){
        this.lowerControlsPanel = new JPanel();

        Color backgroundColor = UIManager.getColor("Panel.background");

        this.lowerControlsPanel.setBackground(backgroundColor);
        // this.lowerControlsPanel.setBackground(Color.BLUE);
        this.lowerControlsPanel.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

    }

    public JPanel getPanel(){
        return this.lowerControlsPanel;
    }
}