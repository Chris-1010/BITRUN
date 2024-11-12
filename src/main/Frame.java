package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	Panel panel;
	ImageIcon icon = new ImageIcon("res/playerSprites/boyBehind1.png");

	Frame() {

		panel = new Panel();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("BIT RUN");
		this.setIconImage(icon.getImage());

		this.add(panel);

		this.pack();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
