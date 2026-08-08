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
	
	// delta time for better performance
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// game loop
		double interval = 1000000000.0/FPS; // how long is a frame
		double dt = 0; // delta time keeps track of how many time has passed
		long prevTime = System.nanoTime(); // previous loop time
//		long currentTime; 
		
		while (gt != null) {
			long currentTime = System.nanoTime(); 
			
			dt += (currentTime - prevTime) / interval; // how much time has passed
			prevTime = currentTime; // remember time for next loop
			
			// enough time has passed and repeat
			if (dt >= 1) {
				update();
				repaint();
				dt--;
			}
		}
	}
	
	// sleep loop for lower CPU usage
//	@Override
//	public void run() {
//	    double drawInterval = 1000000000.0 / FPS;
//	    double nextDrawTime = System.nanoTime() + drawInterval;
//
//	    while (gt != null) {
//	        update();
//	        repaint();
//
//	        try {
//	            double remainingTime = nextDrawTime - System.nanoTime();
//	            remainingTime /= 1000000;
//
//	            if (remainingTime < 0) {
//	                remainingTime = 0;
//	            }
//
//	            Thread.sleep((long) remainingTime);
//
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//
//	        nextDrawTime += drawInterval;
//	    }
//	}
	
	public void update() {
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// board
		board.draw(g2);
		
		
	}

	
}
