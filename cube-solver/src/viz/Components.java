package viz;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.*;


public class Components {

    private final String FONT_NAME = "Helvetica Neue";
    private int width;
    private Color backgroundColor;

    public Components(int width, Color backgroundColor){
        this.width = width;
        this.backgroundColor = backgroundColor;
    }

    public JPanel createPanelComponet(Component component, int height){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(this.width, height));
        panel.setBackground(this.backgroundColor);
        panel.add(component);
        return panel;
    }

    public JButton createButton(String label, int height, ActionListener action){
        JButton button = new JButton(label);
        button.setFont(new Font(this.FONT_NAME, Font.PLAIN, 18));
        button.setLayout(new FlowLayout());
        button.setPreferredSize(new Dimension(this.width-32, height-16));
        //add action listener
        button.addActionListener(action);
        return button;
    }

    public JPanel createPanelButton(String label, int height, ActionListener action){
        JButton button = createButton(label, height, action);
        return createPanelComponet(button, height);
    }

    private JLabel createJLabel(String text, int fontSize, int fontType){
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        Font font = new Font(this.FONT_NAME, fontType, fontSize);
        label.setFont(font.deriveFont(font.getStyle() | fontType));
        return label;
    }

    public JPanel createLabel(String text, int fontSize, int fontType){
        JLabel label = createJLabel(text, fontSize, fontType);
        int height = (int)(fontSize*1.6);
        return createPanelComponet(label, height);
    }

    public JPanel createDetailLabel(String boldText, String text, int fontSize){
        JPanel panel = new JPanel();
        panel.add(createJLabel(boldText, fontSize, Font.BOLD));
        panel.add(createJLabel(text, fontSize, Font.PLAIN));
        int height = (int)(fontSize*1.7);
        panel.setPreferredSize(new Dimension(this.width, height));
        panel.setBackground(this.backgroundColor);
        return panel;
    }

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