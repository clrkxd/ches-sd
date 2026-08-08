package piece;

import main.GamePanel;
import mech.PaletteSwap;
import mech.PiecePalette;

public class Knight extends SuperPiece{

	public Knight(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.KNIGHT;
		img = getImg("/piece/knight");

		if(turn == GamePanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}

	
}
