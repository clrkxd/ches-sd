package piece;

import main.GamePanel;
import mech.PaletteSwap;
import mech.PiecePalette;

public class King extends SuperPiece{

	public King(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.KING;
		img = getImg("/piece/pawn");

		if(turn == GamePanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}

	
}
