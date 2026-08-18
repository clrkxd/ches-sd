package main;

import java.awt.AlphaComposite;
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
	
	public final static int FIN_W = 896; // 1024
	public final static int FIN_H = 640;  // 768
	final int FPS = 60;
	Thread gt;
	
	Board board = new Board();
	MouseDetection md = new MouseDetection();
	MoveMechanics movMech = new MoveMechanics(this, md);
	
	
	// pieces and simulation
	public static ArrayList<SuperPiece> pieces = new ArrayList<>(); // backup
	public static ArrayList<SuperPiece> sim = new ArrayList<>(); // simulates the pieces
	
	// piece selection UI
	SuperPiece activePiece, selectedPiece;
	
	// color
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	int currentTurn = WHITE;
	
	public GamePanel() {
		setPreferredSize(new Dimension(FIN_W, FIN_H));
		setBackground(Color.gray);
		addMouseMotionListener(md);
		addMouseListener(md);
		
		
		
		setThemPieces();
		copyPieces(pieces, sim); // from, to (respectively)
	}
	
	public void launch() {
		gt = new Thread(this);
		gt.start();
	}
	
	public void setThemPieces() {
		// white 
		pieces.add(new Pawn(WHITE, 0, 6));
		pieces.add(new Pawn(WHITE, 1, 6));
		pieces.add(new Pawn(WHITE, 2, 6));
		pieces.add(new Pawn(WHITE, 3, 6));
		pieces.add(new Pawn(WHITE, 4, 6));
		pieces.add(new Pawn(WHITE, 5, 6));
		pieces.add(new Pawn(WHITE, 6, 6));
		pieces.add(new Pawn(WHITE, 7, 6));
		pieces.add(new Rook(WHITE, 0, 7));
		pieces.add(new Rook(WHITE, 7, 7));
		pieces.add(new Knight(WHITE, 1, 7));
		pieces.add(new Knight(WHITE, 6, 7));
		pieces.add(new Bishop(WHITE, 2, 7));
		pieces.add(new Bishop(WHITE, 5, 7));
		pieces.add(new Queen(WHITE, 3, 7));
		pieces.add(new King(WHITE, 4, 7));
		
		// black
		pieces.add(new Pawn(BLACK, 0, 1));
		pieces.add(new Pawn(BLACK, 1, 1));
		pieces.add(new Pawn(BLACK, 2, 1));
		pieces.add(new Pawn(BLACK, 3, 1));
		pieces.add(new Pawn(BLACK, 4, 1));
		pieces.add(new Pawn(BLACK, 5, 1));
		pieces.add(new Pawn(BLACK, 6, 1));
		pieces.add(new Pawn(BLACK, 7, 1));
		pieces.add(new Rook(BLACK, 0, 0));
		pieces.add(new Rook(BLACK, 7, 0));
		pieces.add(new Knight(BLACK, 1, 0));
		pieces.add(new Knight(BLACK, 6, 0));
		pieces.add(new Bishop(BLACK, 2, 0));
		pieces.add(new Bishop(BLACK, 5, 0));
		pieces.add(new Queen(BLACK, 3, 0));
		pieces.add(new King(BLACK, 4, 0));
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
	
	private void update() {
		// mouse pressed
		if (md.pressed) {
			if (activePiece == null) {
				// if activePiece is null, user can pick up the pieces
				for (SuperPiece p : sim) { //if md is on currentTurn's pieces, user can pick it up as the activePiecee
					if (p.turn == currentTurn && p.col == md.x/Board.SQ_SIZE && p.row == md.y/Board.SQ_SIZE) {
						activePiece = p;
					}
				}
			} else {
				simulateMove(); // if the player is holding a piece, simulate the next move
			}
		}
	}
	
	private void simulateMove() {
		
		activePiece.x = md.x - Board.HALFSQ;
		activePiece.y = md.y - Board.HALFSQ;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// board
		board.draw(g2);
		
		// pieces
		for (SuperPiece p: sim) {
			p.draw(g2);
			
			g2.setColor(Color.RED);

			g2.drawRect(
					p.pieceX,
			        p.pieceY,
			    Board.SQ_SIZE,
			    Board.SQ_SIZE
			);
		}

//		
//		if (movMe ch.activeP != null) {
//			
//			
//		// CLARK FIX MO SIMULATEMOVE, ILIPAT DITO SA GAMEPANEL LAHAT YUN	
//			
//			g2.setColor(Color.WHITE);
//			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
//			g2.fillRect(board.boardX + movMech.activeP.col * Board.SQ_SIZE, board.boardY + movMech.activeP.row * Board.SQ_SIZE, Board.SQ_SIZE, Board.SQ_SIZE);
//			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//		    movMech.activeP.draw(g2);
//		    
//		    
//		    
//		}
	}

	
}
