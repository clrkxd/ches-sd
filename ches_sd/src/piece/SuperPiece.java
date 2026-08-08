package piece;

import java.awt.image.BufferedImage;

import mech.Board;

public class SuperPiece {

	public BufferedImage img;
	public int x, y, col, row, prevCol, prevRow; //prevCol and prevRow are the previous col and row
	public int turn; // color of turn
	
	public SuperPiece(int turn, int col, int row) {
		this.turn = turn;
		this.col = col;
		this.row = row;
	}
	
	public int getX(int col) {
		return col * Board.SQ_SIZE;
	}
	
	public int getY(int row) {
		return row * Board.SQ_SIZE;
	}
	
}
