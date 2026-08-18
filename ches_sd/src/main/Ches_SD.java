package main;

import javax.swing.JFrame;

public class Ches_SD {

	public static void main(String[] args) {
		JFrame w = new JFrame("ches-sd");
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setResizable(false);
		
		GamePanel gp = new GamePanel();
		w.add(gp);
		w.pack();
		
		w.setLocationRelativeTo(null);
		w.setVisible(true);
	}
}
