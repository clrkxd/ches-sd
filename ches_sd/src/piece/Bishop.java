package piece;

import main.ChessPanel;
import main.GamePanel;
import mech.MoveMechanics;
import mech.PaletteSwap;
import mech.PiecePalette;

public class Bishop extends SuperPiece{

	public Bishop(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.BISHOP;
		img = getImg("/piece/bishop");

		if(turn == ChessPanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}

	public boolean canMove(int pickedCol, int pickedRow) {
		if (isInsideBoard(pickedCol, pickedRow) && isSameSq(pickedCol, pickedRow) == false) {
			if (Math.abs(pickedCol - prevCol) == Math.abs(pickedRow - prevRow)) {
				if (isValidSquare(pickedCol, pickedRow) && isOnDiagonalLine(pickedCol, pickedRow) == false) {
					return true;
				}
			}
		}
		return false;
	}
}
