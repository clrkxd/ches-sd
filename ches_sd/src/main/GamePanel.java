package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import mech.Board;
import mech.MoveMechanics;
import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Queen;
import piece.Rook;
import piece.SuperPiece;

public class GamePanel extends JPanel implements Runnable{
	
	public final static int FIN_W = 1024;
	public final static int FIN_H = 768;
	final int FPS = 60;
	Thread gt;
	
	Board board = new Board();
	MoveMechanics movMech;
	MouseDetection md = new MouseDetection();
	
	// pieces and simulation
	public static ArrayList<SuperPiece> pieces = new ArrayList<>(); // backup
	public static ArrayList<SuperPiece> sim = new ArrayList<>(); // simulates the pieces
	
	
//	// color
//	public static final int WHITE = 0;
//	public static final int BLACK = 1;
//	int currentTurn = WHITE;
	
	public GamePanel() {
		setPreferredSize(new Dimension(FIN_W, FIN_H));
		setBackground(Color.gray);
		addMouseMotionListener(md);
		addMouseListener(md);
		
		movMech = new MoveMechanics(this);
		
		setThemPieces();
		copyPieces(pieces, sim);
	}
	
	public void launch() {
		gt = new Thread(this);
		gt.start();
	}
	
	public void setThemPieces() {
		// white 
		pieces.add(new Pawn(movMech.WHITE, 0, 6));
		pieces.add(new Pawn(movMech.WHITE, 1, 6));
		pieces.add(new Pawn(movMech.WHITE, 2, 6));
		pieces.add(new Pawn(movMech.WHITE, 3, 6));
		pieces.add(new Pawn(movMech.WHITE, 4, 6));
		pieces.add(new Pawn(movMech.WHITE, 5, 6));
		pieces.add(new Pawn(movMech.WHITE, 6, 6));
		pieces.add(new Pawn(movMech.WHITE, 7, 6));
		pieces.add(new Rook(movMech.WHITE, 0, 7));
		pieces.add(new Rook(movMech.WHITE, 7, 7));
		pieces.add(new Knight(movMech.WHITE, 1, 7));
		pieces.add(new Knight(movMech.WHITE, 6, 7));
		pieces.add(new Bishop(movMech.WHITE, 2, 7));
		pieces.add(new Bishop(movMech.WHITE, 5, 7));
		pieces.add(new Queen(movMech.WHITE, 3, 7));
		pieces.add(new King(movMech.WHITE, 4, 7));
		
		// black
		pieces.add(new Pawn(movMech.BLACK, 0, 1));
		pieces.add(new Pawn(movMech.BLACK, 1, 1));
		pieces.add(new Pawn(movMech.BLACK, 2, 1));
		pieces.add(new Pawn(movMech.BLACK, 3, 1));
		pieces.add(new Pawn(movMech.BLACK, 4, 1));
		pieces.add(new Pawn(movMech.BLACK, 5, 1));
		pieces.add(new Pawn(movMech.BLACK, 6, 1));
		pieces.add(new Pawn(movMech.BLACK, 7, 1));
		pieces.add(new Rook(movMech.BLACK, 0, 0));
		pieces.add(new Rook(movMech.BLACK, 7, 0));
		pieces.add(new Knight(movMech.BLACK, 1, 0));
		pieces.add(new Knight(movMech.BLACK, 6, 0));
		pieces.add(new Bishop(movMech.BLACK, 2, 0));
		pieces.add(new Bishop(movMech.BLACK, 5, 0));
		pieces.add(new Queen(movMech.BLACK, 3, 0));
		pieces.add(new King(movMech.BLACK, 4, 0));
	}
	
	private void copyPieces(ArrayList<SuperPiece> from, ArrayList<SuperPiece> to) {
		
		to.clear();
		for (int i=0; i<from.size(); i++) {
			to.add(from.get(i));
		}
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
		
		// pieces
		for (SuperPiece p: sim) {
			p.draw(g2);
		}
		
	}

	
}
