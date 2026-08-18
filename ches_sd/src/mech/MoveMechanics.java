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
		
	public MoveMechanics(GamePanel gp) {
		this.gp = gp;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	private void selectPiece(int col, int row) {

		// if activeP is null that is passed to game panel, check if user can pick up a piece
	    for (SuperPiece sp : gp.sim) {

	    	// if mouse is on current turn's piece, the user can pick up the piece
	        if (sp.turn == currentTurn &&
	            sp.col == col &&
	            sp.row == row) {

	            activeP = sp;
	            selectedP = sp;
	            return;
	        }
	    }
	    
	    // this method will be passed on the handleClick method
	}
	
	public void handleClick(int mouseX, int mouseY) {

	    int col = mouseX / Board.SQ_SIZE;
	    int row = mouseY / Board.SQ_SIZE;

	    
	    if (activeP == null && selectedP == null) {

	        selectPiece(col, row);

	    } else {

	        simulateMove();
	    }
	}
	
	private void simulateMove() {
		// this method will be passed on the handleClick method
		
		// if piece is held, update the position
		activeP.x = md.x;
		activeP.y = md.y;
	}
}
