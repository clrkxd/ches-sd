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
				 if (isValidSquare(pickedCol, pickedRow)) {
					 return true;
				 }
				 
			 }
			 
			 // castle
			 if (moved == false) {
				 // short castle
				 if (pickedCol == prevCol + 2 && pickedRow == prevRow && isOnStraightLine(pickedCol, pickedRow) == false) {
					 for (SuperPiece piece : ChessPanel.sim) {
							if (piece.col == prevCol + 3 && piece.row == prevRow && piece.moved == false) {
								ChessPanel.castle = piece;
								return true;
							}
						}
				 }
				 
				 // long castle
				 if (pickedCol == prevCol - 2 && pickedRow == prevRow && isOnStraightLine(pickedCol, pickedRow) == false) {
						SuperPiece p[] = new SuperPiece[2];
						for (SuperPiece piece : ChessPanel.sim) {
							if (piece.col  == prevCol - 3 && piece.row == pickedRow) {
								p[0] = piece;
							}
							if (piece.col == prevCol - 4 && piece.row == pickedRow) {
								p[1] = piece;
							}
							if (p[0] == null && p[1] != null && p[1].moved == false) {
								ChessPanel.castle = p[1];
								return true;
							}
						}
					}
			 }
		 }
		return false;
	}

	
}
