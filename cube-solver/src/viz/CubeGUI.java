package viz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CubeGUI implements ActionListener{
	
	private JFrame frame;
	private JPanel panel;
	private JLabel label; 
	
	
	public CubeGUI() {
		frame = new JFrame();
		
		JButton button = new JButton("Click me");
		button.addActionListener(this);
		button.setBounds(50, 100, 50, 100);
		
		
		label = new JLabel("Clicks");
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
		panel.setLayout(new GridLayout());
		panel.add(button);
		panel.add(label);
		
		frame.setSize(400, 1000);
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Cube Solver");
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new CubeGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Clicked");
		
	}
}
