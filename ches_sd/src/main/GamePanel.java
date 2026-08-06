package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import mech.Board;

public class GamePanel extends JPanel{
	
	public final int FIN_W = 1024;
	public final int FIN_H = 768;
	
	
	Board board = new Board();
	
	public GamePanel() {
		setPreferredSize(new Dimension(FIN_W, FIN_H));
		setBackground(Color.black);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// board
		board.draw(g2);
		
	}
}
