package piece;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mech.Board;

public class SuperPiece {

	public BufferedImage img;
	public int x, y, col, row, prevCol, prevRow; //prevCol and prevRow are the previous col and row
	public int turn; // color of turn
	
	public SuperPiece(int turn, int col, int row) {
		this.turn = turn;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		prevCol = col;
		prevRow = row;
	}
	
	public BufferedImage getImg(String pathImg) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(getClass().getResourceAsStream(pathImg + ".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public int getX(int col) {
		return col * Board.SQ_SIZE;
	}
	
	public int getY(int row) {
		return row * Board.SQ_SIZE;
	}
	
}
