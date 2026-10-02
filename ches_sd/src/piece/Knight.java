package piece;

import main.ChessPanel;
import main.GamePanel;
import mech.MoveMechanics;
import mech.PaletteSwap;
import mech.PiecePalette;

public class Knight extends SuperPiece{

	public Knight(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.KNIGHT;
		img = getImg("/piece/knight");

		if(turn == ChessPanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}
	
	public boolean canMove(int pickedCol, int pickedRow) {
		if (isInsideBoard(pickedCol, pickedRow)) {
			if (Math.abs(pickedCol - prevCol) * Math.abs(pickedRow - prevRow) == 2) { // 1:2 or 2:1
				if (isValidSquare(pickedCol, pickedRow)) {
					return true;
				}
			}
		}
		return false;
	}

	
}
