package piece;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import mech.Board;

public class SuperPiece {

	public Type type;
	public BufferedImage img;
	public int x, y, col, row, prevCol, prevRow; //prevCol and prevRow are the previous col and row
	public int turn; // color of turn
	public static int pieceX;
	public static int pieceY;
	
	public SuperPiece(int turn, int col, int row) {
		this.turn = turn;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		prevCol = col;
		prevRow = row;
		
//		centerThePiece();
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
	
	public int getCol(int x) {
		return (x + Board.HALFSQ) / Board.SQ_SIZE;
	}
	
	public int getRow(int y) {
		return (y + Board.HALFSQ) / Board.SQ_SIZE;
	}
//	
//	public void centerThePiece() {
////		int boardSize = SQ_SIZE * 8;
////		boardX = (GamePanel.FIN_W - boardSize) / 2;
////		boardY = (GamePanel.FIN_H - boardSize) / 2;	
//		
//		int boardWidth = Board.MAX_COL * Board.SQ_SIZE;
//        int boardHeight = Board.MAX_ROW * Board.SQ_SIZE;
//        pieceX = (GamePanel.FIN_W - boardWidth) / 2;
//        pieceY = (GamePanel.FIN_H - boardHeight) / 2;
//	}
	
	public void updatePos() {
		
		x = getX(col);
		y = getY(row);
		prevCol = getCol(x);
		prevRow = getRow(y);
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(img, x, y, Board.SQ_SIZE, Board.SQ_SIZE, null);
//		g2.setColor(Color.red);
//		g2.drawRect(pieceX+x, pieceY+y, Board.SQ_SIZE, Board.SQ_SIZE);
	}
}
