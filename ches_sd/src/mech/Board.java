package mech;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Board {

	public final static int MAX_COL = 8;
	public final static int MAX_ROW = 8;
	
	static int scale = 2;
	public final static int SQ_SIZE = 32 * scale;
	public final static int HALFSQ = SQ_SIZE/2;
	
	public static int boardX;
	public static int boardY;
	
	public Board() {
		
		
//		int boardSize = SQ_SIZE * 8;
//		boardX = (GamePanel.FIN_W - boardSize) / 2;
//		boardY = (GamePanel.FIN_H - boardSize) / 2;
		centerTheBoard();
	}
	
	
	
	
	public void centerTheBoard() {
//		int boardSize = SQ_SIZE * 8;
//		boardX = (GamePanel.FIN_W - boardSize) / 2;
//		boardY = (GamePanel.FIN_H - boardSize) / 2;	
		
		int boardWidth = MAX_COL * SQ_SIZE;
        int boardHeight = MAX_ROW * SQ_SIZE;
        boardX = (GamePanel.FIN_W - boardWidth) / 2;
        boardY = (GamePanel.FIN_H - boardHeight) / 2;
	}
	
	
	             	
	public void draw(Graphics2D g2) {
		
		// 64x64 board arrangement
		for (int c = 0; c < 8; c++) {
			for (int r = 0; r < 8; r++) {
				
				
				if ((c+r)%2 == 0) {
					g2.setColor(new Color(210, 165, 125));
				} else {
					g2.setColor(new Color(175, 115, 70));
					
				}
				
				g2.fillRect(boardX+c*SQ_SIZE, boardY+r*SQ_SIZE,SQ_SIZE, SQ_SIZE);
				
				g2.setColor(Color.black);
				g2.drawRect(boardX+c*SQ_SIZE, boardY+r*SQ_SIZE,SQ_SIZE, SQ_SIZE);
				
			}
		}
	}
	
}
