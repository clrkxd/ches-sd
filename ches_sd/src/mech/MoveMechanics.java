package mech;

import main.GamePanel;

public class MoveMechanics {
	
	
	GamePanel gp;

	// color
		public static final int WHITE = 0;
		public static final int BLACK = 1;
		int currentTurn = WHITE;
		
	public MoveMechanics(GamePanel gp) {
		this.gp = gp;
	}
}
