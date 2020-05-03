package viz;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.*;

/**
 * Creates any components of the GUI
 * @author Brad Hodkinson
 */
public class Components {

    private final String FONT_NAME = "Helvetica Neue";
    private int width;
    private Color backgroundColor;

    public Components(int width, Color backgroundColor){
        this.width = width;
        this.backgroundColor = backgroundColor;
    }

    /**
     * Used for creating a component of a panel, with specified component
     * @param compoent
     * @param height
     * @return JPanel
     */
    public JPanel createPanelComponet(Component component, int height){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(this.width, height));
        panel.setBackground(this.backgroundColor);
        panel.add(component);
        return panel;
    }

    /**
     * Used for creating buttons using the label of the button, 
     * the height of the button and the action to perform on buttonPress
     * @param label
     * @param height
     * @return JButton
     */
    public JButton createButton(String label, int height, ActionListener action){
        JButton button = new JButton(label);
        button.setFont(new Font(this.FONT_NAME, Font.PLAIN, 18));
        button.setLayout(new FlowLayout());
        button.setPreferredSize(new Dimension(this.width-32, height-16));
        //add action listener
        button.addActionListener(action);
        return button;
    }

    /**
     * Uses create button and create panel component methods to create panel with button
     * @param label
     * @param height
     * @param action
     * @return JPanel
     */
    public JPanel createPanelButton(String label, int height, ActionListener action){
        JButton button = createButton(label, height, action);
        return createPanelComponet(button, height);
    }

    /**
     * Creates a button and adds it to a panel
     * @param label
     * @param height
     * @return JPanel
     */
    private JLabel createJLabel(String text, int fontSize, int fontType){
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        Font font = new Font(this.FONT_NAME, fontType, fontSize);
        label.setFont(font.deriveFont(font.getStyle() | fontType));
        return label;
    }

    /**
     * Creates and label and adds it to a panel
     * @param text
     * @param fontSize
     * @param fontType
     * @return JPanel
     */
    public JPanel createLabel(String text, int fontSize, int fontType){
        JLabel label = createJLabel(text, fontSize, fontType);
        int height = (int)(fontSize*1.6);
        return createPanelComponet(label, height);
    }


    /**
     * Creates a detail label and adds it to a panel
     * @param boldText
     * @param text
     * @param fontSize
     * @return JPanel
     */
    public JPanel createDetailLabel(String boldText, String text, int fontSize){
        JPanel panel = new JPanel();
        panel.add(createJLabel(boldText, fontSize, Font.BOLD));
        panel.add(createJLabel(text, fontSize, Font.PLAIN));
        int height = (int)(fontSize*1.7);
        panel.setPreferredSize(new Dimension(this.width, height));
        panel.setBackground(this.backgroundColor);
        return panel;
    }

    /**
     * creates a dropdown menu and adds it to a panel
     * @param validChoices
     * @param defaultChoice
     * @param action
     * @return JPanel
     */
    public JPanel createComboBox(List<String> validChoices, int defaultChoice, Consumer<Integer> action){
        JComboBox comboBox = new JComboBox(validChoices.toArray());
        comboBox.setFont(new Font("Airal", Font.PLAIN, 18));
        comboBox.setSize(new Dimension(this.width-32, 64));
        comboBox.setPreferredSize(new Dimension(this.width-32, 64));
        comboBox.setSelectedIndex(defaultChoice);
        comboBox.addActionListener((event) -> {
            int selectedIndex = comboBox.getSelectedIndex();
            action.run(selectedIndex);
        });

        return createPanelComponet(comboBox, 64);
    }

}