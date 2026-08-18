package mech;

import main.GamePanel;
import main.MouseDetection;
import piece.SuperPiece;

public class MoveMechanics {
	
	
	GamePanel gp;
	MouseDetection md;
	
	public SuperPiece selectedP;
	public SuperPiece activeP;

	// color
		public static final int WHITE = 0;
		public static final int BLACK = 1;
		private int currentTurn = WHITE;
		
	public MoveMechanics(GamePanel gp, MouseDetection md) {
		this.gp = gp;
		this.md = md;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	
//	public void selectPiece(int col, int row) {
//
//		// if activeP is null that is passed to game panel, check if user can pick up a piece
//	    for (SuperPiece sp : gp.sim) {
//
//	    	// if mouse is on current turn's piece, the user can pick up the piece
//	        if (sp.turn == currentTurn &&
//	            sp.col == col &&
//	            sp.row == row) {
//
//	            activeP = sp;
//	            selectedP = sp;
//	            
//	            System.out.println("picked" + sp.type);
//	            return;
//	        }
//	    }
//	    
//	    // this method will be passed on the handleClick method
//	}
	
//	public void handleClick(int mouseX, int mouseY) {
//
//	    int col = mouseX / Board.SQ_SIZE;
//	    int row = mouseY / Board.SQ_SIZE;
//
//	    System.out.println("col: " + col);
//	    System.out.println("row: " + row);
//	    if (activeP == null && selectedP == null) {
//
//	        selectPiece(col, row);
//
//	    } else {
//
//	        simulateMove();
//	    }
//	}
	/* 
	 * simulateMove is not really important but it is an advanced feature for a chess game
	 * chess is a turn-based strategy game, not real-time game unlike action platformers or 2D RPG games 
	 * that's why a simulation for all the move is a great feature for a chess game
	 */
	public void simulateMove() {

	    activeP.x = md.x - Board.boardX - Board.HALFSQ;
	    activeP.y = md.y - Board.boardY - Board.HALFSQ;
	    activeP.col = activeP.getCol(activeP.x);
	    activeP.row = activeP.getRow(activeP.y);

//	    activeP.x = md.x - Board.HALFSQ;
//	    activeP.y = md.y - Board.HALFSQ;
	}
//	public void simulateMove(int mouseX, int mouseY) {
//		// this method will be passed on the handleClick method
//		
//		// if piece is held, update the position
////		activeP.x = md.x;
////		activeP.y = md.y;
//		if (activeP != null) {
//	        activeP.x = mouseX;
//	        activeP.y = mouseY;
//	    }
//	}
}
