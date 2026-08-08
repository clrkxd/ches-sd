package piece;

import main.GamePanel;
import mech.PaletteSwap;
import mech.PiecePalette;

public class Pawn extends SuperPiece{

	public Pawn(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.PAWN;
		img = getImg("/piece/pawn");

		if(turn == GamePanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}

	
}
