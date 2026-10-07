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
	public static SuperPiece castle;
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
//		pieces.add(new Knight(WHITE, 1, 7));
//		pieces.add(new Knight(WHITE, 6, 7));
//		pieces.add(new Bishop(WHITE, 2, 7));
//		pieces.add(new Bishop(WHITE, 5, 7));
//		pieces.add(new Queen(WHITE, 3, 7));
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
//		pieces.add(new Knight(BLACK, 1, 0));
//		pieces.add(new Knight(BLACK, 6, 0));
//		pieces.add(new Bishop(BLACK, 2, 0));
//		pieces.add(new Bishop(BLACK, 5, 0));
//		pieces.add(new Queen(BLACK, 3, 0));
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
	    	
	    	// Capture
	        if (selectedPiece.hittin != null) {
	            sim.remove(selectedPiece.hittin.getIndex());
	        }
	        
	     // Determine castling
	        checkCastle();

	        selectedPiece.col = pickedCol;
	        selectedPiece.row = pickedRow;
	        
	        if (castle != null) {
        		castle.updatePos();
        	}

	        selectedPiece.x = pickedCol * Board.SQ_SIZE + Board.HALFSQ;
	        selectedPiece.y = pickedRow * Board.SQ_SIZE + Board.HALFSQ;

	        selectedPiece.updatePos();

	        selectedPiece = null;
	        legalMoves.clear();
	        
	        

	        changeTurn();
	        }

	}
	
	public void updateGame() {

	    // MOUSE JUST PRESSED
	    if (md.justPressed) {

	        int col = md.x / Board.SQ_SIZE;
	        int row = md.y / Board.SQ_SIZE;

	        // SECOND CLICK
	        if (selectedPiece != null) {

//	        	simulateMove();
	            moveSelectedPiece(col, row);
	            
	            

	            md.justPressed = false;
	            return;
	        }

	        // FIRST CLICK
	        if (activePiece == null) {

	            for (SuperPiece p : sim) {

	                if (p.turn == currentTurn && p.col == col && p.row == row) {

	                    activePiece = p;
	                    
	                    allLegalMoves(activePiece);
	                    break;
	                }
	            }
	        }

	        md.justPressed = false;
	    }


	    // DRAGGING
	    if (md.pressed && activePiece != null) {

	        if (md.dragged) {

	            draggin = true;

	            simulateMove();
	        }
	    }


	    // RELEASE
	    if (!md.pressed && activePiece != null) {

	        if (draggin) {

	            // DRAG AND DROP
	            if (validSquare) {
	                copyPieces(sim, pieces);
	            	activePiece.updatePos();
	            	
	            	if (castle != null) {
	            		castle.updatePos();
	            	}
	                changeTurn();
	            }

	        } else {

	            // CLICK ONLY
	            selectedPiece = activePiece;

	            copyPieces(sim, pieces);
	            
	        }

	        
	        activePiece.resetPos();

	        activePiece = null;
	        draggin = false;
	    }
	}
	
	private void simulateMove() {
		
		canMove = false;
		validSquare = false;
		
        copyPieces(sim, pieces);

        
        //reset the castling position
        if (castle != null) {
        	castle.col = castle.prevCol;
        	castle.x = castle.getX(castle.col);
        	castle = null;
        }
        
		
	    int boardX = md.x;
	    int boardY = md.y;

	    activePiece.x = boardX - Board.HALFSQ;
	    activePiece.y = boardY - Board.HALFSQ;
	    
	    activePiece.col = activePiece.getCol(activePiece.x);
	    activePiece.row = activePiece.getRow(activePiece.y);
	    
	    if(activePiece.canMove(activePiece.col, activePiece.row)) {
	    	canMove = true;
	    	
	    	//captures
	    	if (activePiece.hittin != null) {
	    		sim.remove(activePiece.hittin.getIndex());
	    	}
	    	
	    	checkCastle();
	    	validSquare = true;
	    }
	}
	
	private void checkCastle() {
		if (castle != null) {
			if (castle.col == 0) {
				castle.col += 3;
			} else if (castle.col == 7) {
				castle.col -= 2;
			}
			castle.x = castle.getX(castle.col);
		}
	}
	
	private void changeTurn() {
		if (currentTurn == WHITE) {
			currentTurn = BLACK;
			
			for (SuperPiece p : pieces) {
				if (p.turn == BLACK) {
					p.pawnJump = false;
				}
			}
		} else {
			currentTurn = WHITE;
			
			for (SuperPiece p : pieces) {
				if (p.turn == WHITE) {
					p.pawnJump = false;
				}
			}
		}
		
//		activePiece = null;
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
			g2.setColor(Color.RED);
			g2.drawRect(SuperPiece.pieceX, SuperPiece.pieceY, Board.SQ_SIZE, Board.SQ_SIZE);
		}
		
		if (activePiece != null || selectedPiece != null) {
			
			 SuperPiece piece = (activePiece != null) ? activePiece : selectedPiece;
			
			if (canMove) {
				g2.setColor(Color.WHITE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
				g2.fillRect(board.boardX + piece.col * Board.SQ_SIZE, board.boardY + piece.row * Board.SQ_SIZE, Board.SQ_SIZE, Board.SQ_SIZE);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			}
			
			for (int[] move : legalMoves) {
				
				int x = move[0] * Board.SQ_SIZE;
				int y = move[1] * Board.SQ_SIZE;
				
				g2.setColor(new Color(0,128,0));
				g2.fillOval(x + 23, y + 23, 18, 18);
			}
			
			// draw the activePiece
			if (piece != null) {
			    piece.draw(g2);
			}
		}
	}
}
