package piece;

import main.ChessPanel;
import main.GamePanel;
import mech.MoveMechanics;
import mech.PaletteSwap;
import mech.PiecePalette;

public class Rook extends SuperPiece{

	public Rook(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.ROOK;
		img = getImg("/piece/rook");

		if(turn == ChessPanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}
	
	public boolean canMove(int pickedCol, int pickedRow) {
		if (isInsideBoard(pickedCol, pickedRow) && isSameSq(pickedCol, pickedRow) == false) {
			// rook moves as long as same col and row
				if (pickedCol == prevCol || pickedRow == prevRow) {
					if (isValidSquare(pickedCol, pickedRow) && pieceIsOnStraightLine(pickedCol, pickedRow) == false) {
						return true;
					}
				}
		}
		return false;
	}

	
}
