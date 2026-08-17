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
		int currentTurn = WHITE;
		
	public MoveMechanics(GamePanel gp) {
		this.gp = gp;
	}
}
