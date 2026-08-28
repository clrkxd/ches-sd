package piece;

import main.ChessPanel;
import main.GamePanel;
import mech.MoveMechanics;
import mech.PaletteSwap;
import mech.PiecePalette;

public class King extends SuperPiece{

	public King(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.KING;
		img = getImg("/piece/king");

		if(turn == ChessPanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}
	
	public boolean canMove(int pickedCol, int pickedRow) {
		 if (isInsideBoard(pickedCol, pickedRow)) {
			 
			 if (Math.abs(pickedCol - prevCol) + Math.abs(pickedRow - prevRow) == 1 ||
					 Math.abs(pickedCol - prevCol) * Math.abs(pickedRow - prevRow) == 1) {
				 return true;
			 }
		 }
		return false;
	}

	
}
