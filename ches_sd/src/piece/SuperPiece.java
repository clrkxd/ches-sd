package piece;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.ChessPanel;
import main.GamePanel;
import mech.Board;

public class SuperPiece {

	public Type type;
	public BufferedImage img;
	public int x, y, col, row, prevCol, prevRow; //prevCol and prevRow are the previous col and row
	public int turn; // color of turn
	public static int pieceX;
	public static int pieceY;
	public SuperPiece hittin;
	
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
	
	public int getIndex() {
		for (int i = 0; i < ChessPanel.sim.size(); i++) {
			if (ChessPanel.sim.get(i) == this) {
				return i;
			}
		}
		return 0;
	}
	
	public void updatePos() {
		
		x = getX(col);
		y = getY(row);
		prevCol = getCol(x);
		prevRow = getRow(y);
	}
	
	public void resetPos() {
		
		col = prevCol;
		row = prevRow;
		x = getX(col);
		y = getY(row);
	}
	
	public boolean canMove(int pickedCol, int pickedRow) {
		return false;
	}
	
	public boolean isInsideBoard(int pickedCol, int pickedRow) {
		if (pickedCol >= 0 && pickedCol <= 7 && pickedRow >= 0 && pickedRow <= 7) {
			return true;
		}
		return false;
	}
	
	public boolean isSameSq(int pickedCol, int pickedRow) {
		if (pickedCol == prevCol && pickedRow == prevRow) {
			return true;
		}
		return false;
	}
	
	public SuperPiece gettingHit(int pickedCol, int pickedRow) {
		for (SuperPiece p : ChessPanel.sim) {
			if (p.col == pickedCol && p.row == pickedRow && p != this) {
				return p;
			}
		}
		return null;
	}
	
	public boolean isValidSquare(int pickedCol, int pickedRow) {
		hittin = gettingHit(pickedCol, pickedRow);
		
		if (hittin == null) {
			return true;
		} else {
			if (hittin.turn != this.turn) {
				return true;
			} else {
				hittin = null;
			}
		}
		
//		if (gettingHit(pickedCol, pickedRow) == null) {
//			return true;
//		}
		
		return false;
	}
	
	public boolean isOnStraightLine(int pickedCol, int pickedRow) {
		// when piece is moving to the left
		for (int c = prevCol - 1; c > pickedCol; c--) {
			for(SuperPiece piece : ChessPanel.sim) {
				if (piece.col == c && piece.row == pickedRow) {
					hittin = piece;
					return true;
				}
			}
		}
		
		// right
		for (int c = prevCol + 1; c < pickedCol; c++) {
			for(SuperPiece piece : ChessPanel.sim) {
				if (piece.col == c && piece.row == pickedRow) {
					hittin = piece;
					return true;
				}
			}
		}
		
		// up
		for (int r = prevRow - 1; r > pickedRow; r--) {
			for(SuperPiece piece : ChessPanel.sim) {
				if (piece.col == pickedCol && piece.row == r) {
					hittin = piece;
					return true;
				}
			}
		}
		
		// down
		for (int r = prevRow + 1; r < pickedRow; r++) {
			for(SuperPiece piece : ChessPanel.sim) {
				if (piece.col == pickedCol && piece.row == r) {
					hittin = piece;
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	public boolean isOnDiagonalLine(int pickedCol, int pickedRow) {
		
		
		if (pickedRow < prevRow) {
			// up left
			for (int c = prevCol - 1; c > pickedCol; c--) {
				int diff = Math.abs(c - prevCol);
				for (SuperPiece piece : ChessPanel.sim) {
					if (piece.col == c && piece.row == prevRow - diff) {
						hittin = piece;
						return true;
					}
				}
			}
		
			// up right
			for (int c = prevCol + 1; c < pickedCol; c++) {
				int diff = Math.abs(c - prevCol);
				for (SuperPiece piece : ChessPanel.sim) {
					if (piece.col == c && piece.row == prevRow - diff) {
						hittin = piece;
						return true;
					}
				}
			}
			
		}
		
		if (pickedRow > prevRow) {
			// down left
			for (int c = prevCol - 1; c > pickedCol; c--) {
				int diff = Math.abs(c - prevCol);
				for (SuperPiece piece : ChessPanel.sim) {
					if (piece.col == c && piece.row == prevRow + diff) {
						hittin = piece;
						return true;
					}
				}
			}
		
			// down right
			for (int c = prevCol + 1; c < pickedCol; c++) {
				int diff = Math.abs(c - prevCol);
				for (SuperPiece piece : ChessPanel.sim) {
					if (piece.col == c && piece.row == prevRow + diff) {
						hittin = piece;
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(img, x, y, Board.SQ_SIZE, Board.SQ_SIZE, null);
		g2.setColor(Color.red);
		g2.drawRect(pieceX+x, pieceY+y, Board.SQ_SIZE, Board.SQ_SIZE);
	}
}
