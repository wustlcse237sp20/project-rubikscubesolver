package viz;

import java.util.LinkedList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


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

    public JPanel createButton(String label, int height, ActionListener action){
        JButton button = new JButton(label);
        button.setFont(new Font(this.FONT_NAME, Font.PLAIN, 18));
        button.setLayout(new FlowLayout());
        button.setPreferredSize(new Dimension(this.width-32, height-16));
        //add action listener
        button.addActionListener(action);
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

    public JPanel createChoice(List<String> validChoices, int height){
        Choice choice = new Choice();
        choice.setFont(new Font("Airal", Font.PLAIN, 18));
        choice.setSize(new Dimension(this.width-32, height));
        choice.setPreferredSize(new Dimension(this.width-32, height));
        for(String option: validChoices){
            choice.add(option);
        }
        return createPanelComponet(choice, height);
    }

}