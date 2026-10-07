package piece;

import main.ChessPanel;
import main.GamePanel;
import mech.MoveMechanics;
import mech.PaletteSwap;
import mech.PiecePalette;

public class Pawn extends SuperPiece{

	public Pawn(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.PAWN;
		img = getImg("/piece/pawn");

		if(turn == ChessPanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}

	public boolean canMove(int pickedCol, int pickedRow) {
		if (isInsideBoard(pickedCol, pickedRow) && isSameSq(pickedCol, pickedRow) == false) {
			
			// moveDirection
			int moveDirection;
			if (turn == ChessPanel.WHITE) {
				moveDirection = -1;
			} else {
				moveDirection = 1;
			}
			
			// check hittin, dont use isValidSquare
			hittin = gettingHit(pickedCol, pickedRow);
			
			// 1 square move
			if (pickedCol == prevCol && pickedRow == prevRow + moveDirection && hittin == null) {
				return true;
			}
			
			// 2 square movement
			if (pickedCol == prevCol && pickedRow == prevRow + moveDirection*2 && hittin == null && moved == false && isOnStraightLine(pickedCol, pickedRow) == false) {
				return true;
			}
			
			// diagonal movement & capture if an enemy piece is diagonally in front of it 
			if (Math.abs(pickedCol - prevCol) == 1 && pickedRow == prevRow + moveDirection && hittin != null && hittin.turn != turn) {
					return true;
			}
			
			// en passant
			if (Math.abs(pickedCol - prevCol) == 1 && pickedRow == prevRow + moveDirection) {
				for (SuperPiece piece : ChessPanel.sim) {
					if (piece.col == pickedCol && piece.row == prevRow && piece.pawnJump == true) {
						hittin = piece;
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
