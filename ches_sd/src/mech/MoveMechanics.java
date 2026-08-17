package mech;

import main.GamePanel;
import piece.SuperPiece;

public class MoveMechanics {
	
	
	GamePanel gp;
	
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

	    for (SuperPiece sp : gp.sim) {

	        if (sp.turn == currentTurn &&
	            sp.col == col &&
	            sp.row == row) {

	            activeP = sp;
	            selectedP = sp;
	            return;
	        }
	    }
	}
	
	public void handleClick(int mouseX, int mouseY) {

	    int col = mouseX / Board.SQ_SIZE;
	    int row = mouseY / Board.SQ_SIZE;

	    if (activeP == null && selectedP == null) {

	        selectPiece(col, row);

	    } else {

//	        simulateMove(col, row);
	    }
	}
	
	private void simulateMove() {
		
	}
}
