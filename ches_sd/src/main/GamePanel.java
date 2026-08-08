package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import mech.Board;

public class GamePanel extends JPanel implements Runnable{
	
	public final static int FIN_W = 1024;
	public final static int FIN_H = 768;
	final int FPS = 60;
	Thread gt;
	
	Board board = new Board();
	
	// color
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	int currentTurn = WHITE;
	
	public GamePanel() {
		setPreferredSize(new Dimension(FIN_W, FIN_H));
		setBackground(Color.gray);
	}
	
	public void launch() {
		gt = new Thread(this);
		gt.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// game loop
		double interval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while (gt != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / interval;
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	public void update() {
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// board
		board.draw(g2);
		
		
	}

	
}
