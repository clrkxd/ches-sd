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

public class ChessPanel extends JPanel{

	Board board = new Board();
	MouseDetection md;
	MoveMechanics movMech;
	
	// pieces and simulation
	public static ArrayList<SuperPiece> pieces = new ArrayList<>(); // backup
	public static ArrayList<SuperPiece> sim = new ArrayList<>(); // simulates the pieces
	public ArrayList<int[]> legalMoves = new ArrayList<>(); // green dots
	
	// piece selection UI
	SuperPiece activePiece;
	SuperPiece selectedPiece;
	boolean draggin = false;
	
	
	// color
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	int currentTurn = WHITE;
	
	
	boolean canMove;
	boolean validSquare;
	
	public ChessPanel(MouseDetection md) {
		this.md = md;
		
		addMouseMotionListener(md);
		addMouseListener(md);
		

	    int boardWidth = Board.MAX_COL * Board.SQ_SIZE;
	    int boardHeight = Board.MAX_ROW * Board.SQ_SIZE;


	    setPreferredSize(new Dimension(boardWidth, boardHeight));
	    setMinimumSize(new Dimension(boardWidth, boardHeight));
	    setMaximumSize(new Dimension(boardWidth, boardHeight));
	    setFocusable(true);
	    requestFocusInWindow();

        movMech = new MoveMechanics(this, md);

        setThemPieces();
        copyPieces(pieces, sim);
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
		pieces.add(new King(WHITE, 4, 5));
		
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
	
	private void moveSelectedPiece(int pickedCol, int pickedRow) {


	    if (selectedPiece.canMove(pickedCol, pickedRow)) {

	        selectedPiece.col = pickedCol;
	        selectedPiece.row = pickedRow;

	        selectedPiece.x = pickedCol * Board.SQ_SIZE + Board.HALFSQ;
	        selectedPiece.y = pickedRow * Board.SQ_SIZE + Board.HALFSQ;

	        selectedPiece.updatePos();

	        selectedPiece = null;
	        legalMoves.clear();
	        }

	}
	
//	public void updateGame() {
//		
//		 
//		
////		selectedPiece = null;
//		// second click logic check this later
//		 if (md.justPressed) {
//			
//		        int col = md.x / Board.SQ_SIZE;
//		        int row = md.y / Board.SQ_SIZE;
//		        
//		      
////		        System.out.println("press Count:" + pressCount);
//		        
//		        
//
//		        // SECOND CLICK
//		        if (selectedPiece != null) {
//		        	
////		        	pressCount = 0;
////			        System.out.println("press Count:" + pressCount);
//
//		            moveSelectedPiece(col, row);
//
//		            md.justPressed = false;
//		            
//		            return;
//		        }
//		    }
//		
//		// mouse pressed
//		if (md.pressed) {
//			if (activePiece == null && selectedPiece == null) {
//				
//				int boardX = md.x - getX();
//				int boardY = md.y - getY();
//				
////				pressCount += 1;
//			    int col = md.x / Board.SQ_SIZE;
//			    int row = md.y / Board.SQ_SIZE;
//				// if activePiece is null, user can pick up the pieces
//				for (SuperPiece p : sim) { //if md is on currentTurn's pieces, user can pick it up as the activePiecee
//					if (p.turn == currentTurn && p.col == col && p.row == row) {
//						activePiece = p;
////						selectedPiece = activePiece;
//					}
//				}
//			} 
//			
//			// A piece is being held
//	        if (activePiece != null && md.dragged) {
//	            draggin = true;
//	            simulateMove();
//	        }
//		}
//
//		// Mouse released
//	    if (!md.pressed) {
//
//	        if (activePiece != null) {
//
//	            if (draggin) {
//	                // DRAG AND DROP
//	            	if (validSquare) {
//	            		activePiece.updatePos();
//	            		
//	            		allLegalMoves(activePiece);
//	            	}
//	               
//	                
//
//	            } else {
//
//	                // SELECT TO MOVE
//	                selectedPiece = activePiece;
//	                System.out.println(selectedPiece.type);
////	                pressCount = 0;
//	                allLegalMoves(selectedPiece);
//	            }
//
//	            activePiece.resetPos();
//	            activePiece = null;
//	            draggin = false; 
////	            legalMoves.clear();
//	        }
//	    }
//	    
//	    // Reset click
//	            md.justPressed = false;  
//	}
	
//	public void updateGame() {
//
//	    // =========================
//	    // MOUSE PRESSED
//	    // =========================
//	    if (md.justPressed) {
//
//	        int col = md.x / Board.SQ_SIZE;
//	        int row = md.y / Board.SQ_SIZE;
//
//	        // If a piece is already selected,
//	        // this click is the destination.
//	        if (selectedPiece != null) {
//
//	            moveSelectedPiece(col, row);
//
//	            md.justPressed = false;
//	            return;
//	        }
//
//	        // Find piece to pick up
//	        for (SuperPiece p : sim) {
//
//	            if (p.turn == currentTurn &&
//	                p.col == col &&
//	                p.row == row) {
//
//	                activePiece = p;
//	                break;
//	            }
//	        }
//
//	        md.justPressed = false;
//	    }
//
//
//	    // =========================
//	    // MOUSE HELD
//	    // =========================
//	    if (md.pressed && activePiece != null) {
//
//	        // Mouse moved while holding the piece
//	        if (md.dragged) {
//	            draggin = true;
//	            simulateMove();
//	        }
//	    }
//
//
//	    // =========================
//	    // MOUSE RELEASED
//	    // =========================
//	    if (!md.pressed && activePiece != null) {
//
//	        if (draggin) {
//
//	            // DRAG AND DROP
//	            if (validSquare) {
//	                activePiece.updatePos();
//	            }
//
//	        } else {
//
//	            // JUST CLICKED
//	            selectedPiece = activePiece;
//
//	            System.out.println("Selected: " + selectedPiece.type);
//
//	            allLegalMoves(selectedPiece);
//	        }
//
//	        activePiece.resetPos();
//	        activePiece = null;
//	        draggin = false;
//	    }
//	}
	
	public void updateGame() {

	    // =========================
	    // MOUSE JUST PRESSED
	    // =========================
	    if (md.justPressed) {

	        int col = md.x / Board.SQ_SIZE;
	        int row = md.y / Board.SQ_SIZE;

	        // SECOND CLICK
	        if (selectedPiece != null) {

	            moveSelectedPiece(col, row);

	            md.justPressed = false;
	            return;
	        }

	        // FIRST CLICK
	        if (activePiece == null) {

	            for (SuperPiece p : sim) {

	                if (p.turn == currentTurn &&
	                    p.col == col &&
	                    p.row == row) {

	                    activePiece = p;
	                    
	                    allLegalMoves(activePiece);
	                    break;
	                }
	            }
	        }

	        md.justPressed = false;
	    }


	    // =========================
	    // DRAGGING
	    // =========================
	    if (md.pressed && activePiece != null) {

	        if (md.dragged) {

	            draggin = true;

	            simulateMove();
	        }
	    }


	    // =========================
	    // RELEASE
	    // =========================
	    if (!md.pressed && activePiece != null) {

	        if (draggin) {

	            // DRAG AND DROP
	            if (validSquare) {
	                activePiece.updatePos();
	            }

	        } else {

	            // CLICK ONLY
	            selectedPiece = activePiece;

	            allLegalMoves(selectedPiece);
	        }

	        activePiece.resetPos();

	        activePiece = null;
	        draggin = false;
	    }
	}
	
	private void simulateMove() {
		
		canMove = false;
		validSquare = false;
		
	    int boardX = md.x;
	    int boardY = md.y;

	    activePiece.x = boardX - Board.HALFSQ;
	    activePiece.y = boardY - Board.HALFSQ;
		
//		activePiece.x = md.x;
//		activePiece.y = md.y;
	    
	    activePiece.col = activePiece.getCol(activePiece.x);
	    activePiece.row = activePiece.getRow(activePiece.y);
	    
	    if(activePiece.canMove(activePiece.col, activePiece.row)) {
	    	canMove = true;
	    	validSquare = true;
	    }
	}
	
private void allLegalMoves(SuperPiece p) {
		
		legalMoves.clear();
		
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				
				if (p.canMove(col, row)) {
					legalMoves.add(new int[] {col, row});
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// board
		board.draw(g2);
		
		// pieces
		for (SuperPiece p: sim) {
			p.draw(g2);
			
//			g2.setColor(Color.RED);
//
//			g2.drawRect(SuperPiece.pieceX, SuperPiece.pieceY, Board.SQ_SIZE, Board.SQ_SIZE);
		}
		
		if (activePiece != null) {
			
			if (canMove) {
				g2.setColor(Color.WHITE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
				g2.fillRect(board.boardX + activePiece.col * Board.SQ_SIZE, board.boardY + activePiece.row * Board.SQ_SIZE, Board.SQ_SIZE, Board.SQ_SIZE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
			
			for (int[] move : legalMoves) {
				
				int x = move[0] * Board.SQ_SIZE;
				int y = move[1] * Board.SQ_SIZE;
				
				g2.setColor(Color.green);
				g2.fillOval(x + 18, y + 18,28,28);
			}
			
		
			// draw the activePiece
			activePiece.draw(g2);
		}
		

	}

}
