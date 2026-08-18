package piece;

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

		if(turn == GamePanel.BLACK) {
		    img = PaletteSwap.swap(
		        img,
		        PiecePalette.BLACK
		    );
		}
	}

	
}
