package piece;

import main.GamePanel;
import mech.PaletteSwap;
import mech.PiecePalette;

public class Queen extends SuperPiece{

	public Queen(int turn, int col, int row) {
		super(turn, col, row);
		// TODO Auto-generated constructor stub
		
		type = Type.QUEEN;
		img = getImg("/piece/queen");

		if(turn == GamePanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}

	
}
