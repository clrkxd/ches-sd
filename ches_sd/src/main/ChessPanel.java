package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
import piece.Type;

public class ChessPanel extends JPanel{

	Board board = new Board();
	MouseDetection md;
	MoveMechanics movMech;
	
	
	// pieces and simulation
	public static ArrayList<SuperPiece> pieces = new ArrayList<>(); // backup
	public static ArrayList<SuperPiece> sim = new ArrayList<>(); // simulates the pieces
	public ArrayList<int[]> legalMoves = new ArrayList<>(); // green dots
	public ArrayList<SuperPiece> promoteTo = new ArrayList<>(); // promoting pawns
	public ArrayList<int[]> illegalMoves = new ArrayList<>(); //checks illegal moves in select-to-move
	
	// piece selection UI
	public SuperPiece activePiece;
	public SuperPiece selectedPiece;
	public static SuperPiece castle;
	boolean draggin = false;
	
	
	// color
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static int currentTurn = WHITE;
	
	
	boolean canMove;
	boolean validSquare;
	public boolean promoting;
	
	public ChessPanel(MouseDetection md) {
		this.md = md;
		movMech = new MoveMechanics(this, md);
		addMouseMotionListener(md);
		addMouseListener(md);
		

	    int boardWidth = Board.MAX_COL * Board.SQ_SIZE;
	    int boardHeight = Board.MAX_ROW * Board.SQ_SIZE;


	    setPreferredSize(new Dimension(boardWidth, boardHeight));
	    setMinimumSize(new Dimension(boardWidth, boardHeight));
	    setMaximumSize(new Dimension(boardWidth, boardHeight));
	    setFocusable(true);
	    requestFocusInWindow();

//        movMech = new MoveMechanics(this, md);

//        setThemPieces();
//		testPromotion();
		testIllegal();
//		testStalemate();
        copyPieces(pieces, sim);
	}
	
	public ArrayList<SuperPiece> getPieces() {
	    return pieces;
	}

	public ArrayList<SuperPiece> getSim() {
	    return sim;
	}

	public ArrayList<int[]> getLegalMoves() {
	    return legalMoves;
	}

	public ArrayList<int[]> getIllegalMoves() {
	    return illegalMoves;
	}

	public ArrayList<SuperPiece> getPromoteTo() {
	    return promoteTo;
	}

	public SuperPiece getActivePiece() {
	    return activePiece;
	}

	public void setActivePiece(SuperPiece activePiece) {
	    this.activePiece = activePiece;
	}

	public SuperPiece getCastle() {
	    return castle;
	}

	public int getCurrentTurn() {
	    return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
	    this.currentTurn = currentTurn;
	}

	public boolean isPromoting() {
	    return promoting;
	}

	public void setPromoting(boolean promoting) {
	    this.promoting = promoting;
	}
	
	public SuperPiece getSelectedPiece() {
	    return selectedPiece;
	}

	public void setSelectedPiece(SuperPiece selectedPiece) {
	    this.selectedPiece = selectedPiece;
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
	
	
	// TEST
//	public void testPromotion() {
//		pieces.add(new Pawn(WHITE, 0, 3));
//		pieces.add(new Pawn(BLACK, 7, 4));
//	}
	
	public void testIllegal() {
		pieces.add(new Pawn(WHITE, 7, 6));
		pieces.add(new King(WHITE, 3, 7));
		pieces.add(new King(BLACK, 0, 3));
		pieces.add(new Bishop(BLACK, 1, 4));
		pieces.add(new Queen(BLACK, 4, 5));
	}
//	
//	public void testStalemate() {
//		pieces.add(new Queen(BLACK, 2, 1));
//		pieces.add(new King(BLACK, 2, 4));
//		pieces.add(new King(WHITE, 0, 3));
//		
//		currentTurn = BLACK;
//	}
	
	public void copyPieces(ArrayList<SuperPiece> from, ArrayList<SuperPiece> to) {
		
		to.clear();
		for (int i=0; i<from.size(); i++) {
			to.add(from.get(i));
		}
	}
	
	// ANG GULO GULO AYUSIN DAPAT STRUCTURE NETO SOMEDAY!
	public void updateGame() {
		
		if (promoting) {
			movMech.promote();
			System.out.println("prom is okay");
			
		} else {
				    // MOUSE JUST PRESSED
		    if (md.justPressed) {
	
		        int col = md.x / Board.SQ_SIZE;
		        int row = md.y / Board.SQ_SIZE;
	
		        // SECOND CLICK
		        if (selectedPiece != null) {
	
		        	// If clicked another piece of the current turn, select that piece instead.
		            for (SuperPiece p : sim) {
		                if (p.turn == currentTurn && p.col == col && p.row == row) {

		                    selectedPiece = p;
		                    activePiece = p;

		                    movMech.allLegalMoves(selectedPiece);

		                    md.justPressed = false;
		                    return;
		                }
		            }
	//	        	simulateMove();
		            movMech.moveSelectedPiece(col, row);
		            
		            md.justPressed = false;
		            return;
		        }
	
		        // FIRST CLICK
		        if (activePiece == null) {
	
		            for (SuperPiece p : sim) {
	
		                if (p.turn == currentTurn && p.col == col && p.row == row) {
	
		                    activePiece = p;
		                    
		                    movMech.allLegalMoves(activePiece);
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
//		        simulateMove();
		    }
	
	
		    // RELEASE
		    if (!md.pressed && activePiece != null) {
	
		        if (draggin) {
	
		            // DRAG AND DROP
		            if (validSquare) {
		            	
		            	// captures
		    	    	if (activePiece.hittin != null) {
		    	    		sim.remove(activePiece.hittin.getIndex());
		    	    	}
		            	
		                copyPieces(sim, pieces);
		            	activePiece.updatePos();
		            	
		            	if (castle != null) {
		            		castle.updatePos();
		            	}
		            	
		            	if (movMech.canPromote()) {
		            		promoting = true;
		            	} else {
		            		movMech.changeTurn();
		            	}
		            	
		            }
	
		        } else {
	
		            // CLICK ONLY
		            selectedPiece = activePiece;
	
		            copyPieces(sim, pieces);
		            
		        }
		        if (!promoting) {
		            activePiece.resetPos();
		            activePiece = null;
		        }
		        draggin = false;
		    }
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
//	    	if (activePiece.hittin != null) {
//	    		sim.remove(activePiece.hittin.getIndex());
//	    	}
	    	
	    	movMech.checkCastle();
	    	
	    	if (movMech.isKingDoingIllegal(activePiece) == false) {
	    		validSquare = true;
	    	}
	    	
	    	
	    	
	    }
	    
//	    System.out.println("Simulating move");
//	    System.out.println("Mouse pressed: " + md.pressed);
//	    System.out.println("Captured piece: " + activePiece.hittin);
//	    
//	    System.out.println("RELEASE HANDLER");
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
//			g2.drawRect(SuperPiece.pieceX, SuperPiece.pieceY, Board.SQ_SIZE, Board.SQ_SIZE);
		}
		
		if (activePiece != null || selectedPiece != null) {
			
			 SuperPiece piece = (activePiece != null) ? activePiece : selectedPiece;
			
			if (canMove) {
				
				if (movMech.isKingDoingIllegal(piece)) {
					g2.setColor(Color.GRAY);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2.fillRect(board.boardX + piece.col * Board.SQ_SIZE, board.boardY + piece.row * Board.SQ_SIZE, Board.SQ_SIZE, Board.SQ_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				} else {
					g2.setColor(Color.WHITE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2.fillRect(board.boardX + piece.col * Board.SQ_SIZE, board.boardY + piece.row * Board.SQ_SIZE, Board.SQ_SIZE, Board.SQ_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
				
			}
			
			// Green dots: moves that the piece can use
			for (int[] move : legalMoves) {
				
				int x = move[0] * Board.SQ_SIZE;
				int y = move[1] * Board.SQ_SIZE;
				
				g2.setColor(new Color(0,128,0));
				g2.fillOval(x + 23, y + 23, 18, 18);
				
				
			}
			
			// Red dots: moves that leave the king in check
			for (int[] move : illegalMoves) {

			    int x = move[0] * Board.SQ_SIZE;
			    int y = move[1] * Board.SQ_SIZE;

			    g2.setColor(Color.RED);
			    g2.fillOval(x + 23, y + 23, 18, 18);
			}
			
			// draw the activePiece
			if (piece != null) {
			    piece.draw(g2);
			}
			
//			if (activePiece != null) {
//			    g2.setColor(Color.RED);
//			    g2.drawRect(
//			        activePiece.col * Board.SQ_SIZE,
//			        activePiece.row * Board.SQ_SIZE,
//			        Board.SQ_SIZE,
//			        Board.SQ_SIZE
//			    );
//			}
			
	}

			if (promoting) {
				
//						g2.setColor(Color.RED);
//					    g2.fillRect(600, 100, 100, 100);
			
				
				for (SuperPiece p : promoteTo) {
					
					int x = p.getX(p.col);
			        int y = p.getY(p.row);
					
			        g2.setColor(Color.WHITE);
				    g2.fillRect(x, y, Board.SQ_SIZE, Board.SQ_SIZE);
//							System.out.println(
//							        p.type + " col=" + p.col + " row=" + p.row +
//							        " x=" + p.getX(p.col) +
//							        " y=" + p.getY(p.row)
//							    );
					
					g2.drawImage(p.img, p.getX(p.col), p.getY(p.row), Board.SQ_SIZE, Board.SQ_SIZE, null);
					g2.drawImage(p.img, x, y, Board.SQ_SIZE, Board.SQ_SIZE, null);
				}
			}
	}
}
