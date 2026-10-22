package mech;

import main.ChessPanel;
//import main.GamePanel;
import main.MouseDetection;
import piece.Bishop;
import piece.Knight;
import piece.Queen;
import piece.Rook;
import piece.King;
import piece.Pawn;
import piece.SuperPiece;
import piece.Type;


public class MoveMechanics {
	
	ChessPanel chP;
	MouseDetection md;
	
	

	public MoveMechanics(ChessPanel chessPanel, MouseDetection md) {
		this.chP = chessPanel;
		this.md = md;
	}
	
	public void moveSelectedPiece(int pickedCol, int pickedRow) {
		
		 // Reject moves that would leave your king in check
	    for (int[] move : chP.getIllegalMoves()) {
	        if (move[0] == pickedCol && move[1] == pickedRow) {
	            return;
	        }
	    }

	    SuperPiece selected = chP.getSelectedPiece();
	    
	    if (chP.getSelectedPiece() != null &&
	    		chP.getSelectedPiece().canMove(pickedCol, pickedRow)) {

	    	
	    	// Capture
	        if (chP.getSelectedPiece().hittin != null) {
	        	chP.getSim().remove(chP.getSelectedPiece().hittin.getIndex());
	        }
	        
	     // Determine castling
	        checkCastle();

	        chP.getSelectedPiece().col = pickedCol;
	        chP.getSelectedPiece().row = pickedRow;
	        
//	        if (chP.castle != null) {
//	        	chP.castle.updatePos();
//       	}
	        SuperPiece rook = chP.getCastle();

	        if (rook != null) {
	            rook.updatePos();
	        }

	        chP.getSelectedPiece().x = pickedCol * Board.SQ_SIZE + Board.HALFSQ;
	        chP.getSelectedPiece().y = pickedRow * Board.SQ_SIZE + Board.HALFSQ;

	        chP.getSelectedPiece().updatePos();

	        
	        
//	        activePiece = selectedPiece;
	        
	        // Check promotion
	        chP.setActivePiece(selected);

	        if (canPromote()) {
	        	chP.setPromoting(true);
       	} else {
       		changeTurn();
       		chP.setActivePiece(null);
       	}
	        
	        chP.setSelectedPiece(null);
	        chP.getLegalMoves().clear();
//	        selectedPiece = null;
//	        legalMoves.clear();
//	        activePiece = null;
	    }
	}
	
	public boolean isKingDoingIllegal(SuperPiece king) {
		
		if (king.type == Type.KING) {
			for (SuperPiece p : chP.getPieces()) {
				if (p != king && p.turn != king.turn && p.canMove(king.col, king.row)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void checkCastle() {
		if (ChessPanel.castle != null) {
			if (ChessPanel.castle.col == 0) {
				ChessPanel.castle.col += 3;
			} else if (ChessPanel.castle.col == 7) {
				ChessPanel.castle.col -= 2;
			}
			ChessPanel.castle.x = ChessPanel.castle.getX(ChessPanel.castle.col);
		}
	}
	
	public void changeTurn() {
	    int nextTurn = chP.getCurrentTurn() == ChessPanel.WHITE ? ChessPanel.BLACK : ChessPanel.WHITE;

	    chP.setCurrentTurn(nextTurn);

	    for (SuperPiece p : chP.getPieces()) {
	        if (p.turn == nextTurn) {
	            p.pawnJump = false;
	        }
	    }
	}
	
//	private void allLegalMoves(SuperPiece p) {
//		
//		legalMoves.clear();
//		
//		for (int col = 0; col < 8; col++) {
//			for (int row = 0; row < 8; row++) {
//				
//				if (p.canMove(col, row)) {
//					legalMoves.add(new int[] {col, row});
//				}
//			}
//		}
//	}
	public void allLegalMoves(SuperPiece p) {

		chP.getLegalMoves().clear();
	    chP.getIllegalMoves().clear();

	    int originalCol = p.col;
	    int originalRow = p.row;
	    int originalX = p.x;
	    int originalY = p.y;

	    for (int col = 0; col < 8; col++) {
	        for (int row = 0; row < 8; row++) {

	            if (!p.canMove(col, row)) {
	                continue;
	            }

	            // Temporarily move the piece
	            p.col = col;
	            p.row = row;
	            p.x = col * Board.SQ_SIZE + Board.HALFSQ;
	            p.y = row * Board.SQ_SIZE + Board.HALFSQ;

	            SuperPiece king = null;

	            for (SuperPiece piece : chP.getSim()) {
	                if (piece.type == Type.KING && piece.turn == p.turn) {
	                    king = piece;
	                    break;
	                }
	            }

	            // Check king safety
	            boolean illegal = king != null && isKingDoingIllegal(king);

	            // Restore the piece
	            p.col = originalCol;
	            p.row = originalRow;
	            p.x = originalX;
	            p.y = originalY;

	            if (illegal) {
	            	chP.getIllegalMoves().add(new int[] {col, row});
	            } else {
	            	chP.getLegalMoves().add(new int[] {col, row});
	            }
	        }
	    }
	}
	
	public boolean canPromote() {
		
		 SuperPiece activePiece = chP.getActivePiece();
		
		if (activePiece == null) {
	        return false;
	    }
		
		if (activePiece.type == Type.PAWN) {
			if (chP.getCurrentTurn() == ChessPanel.WHITE && activePiece.row == 0) {
				chP.getPromoteTo().clear();
				chP.getPromoteTo().add(new Rook(chP.getCurrentTurn(), activePiece.col, activePiece.row));
				chP.getPromoteTo().add(new Knight(chP.getCurrentTurn(), activePiece.col, activePiece.row + 1));
				chP.getPromoteTo().add(new Bishop(chP.getCurrentTurn(), activePiece.col, activePiece.row + 2));
				chP.getPromoteTo().add(new Queen(chP.getCurrentTurn(), activePiece.col, activePiece.row + 3));
				return true;
			}
			if (chP.getCurrentTurn() == ChessPanel.BLACK && activePiece.row == 7) {
				chP.getPromoteTo().clear();
				chP.getPromoteTo().add(new Rook(chP.getCurrentTurn(), activePiece.col, activePiece.row));
				chP.getPromoteTo().add(new Knight(chP.getCurrentTurn(), activePiece.col, activePiece.row - 1));
				chP.getPromoteTo().add(new Bishop(chP.getCurrentTurn(), activePiece.col, activePiece.row - 2));
				chP.getPromoteTo().add(new Queen(chP.getCurrentTurn(), activePiece.col, activePiece.row - 3));
				return true;
			}
		}
		return false;
	}
	
	public void promote() {
		
		 SuperPiece activePiece = chP.getActivePiece();
		
		if (!chP.isPromoting() == true || activePiece == null) {
	        return;
	    }
		
		 if (md.pressed) {
			 for (SuperPiece p : chP.getPromoteTo()) {
				 
				 	int x = p.getX(p.col);
		            int y = p.getY(p.row);
		            
		            if (md.x >= x && md.x < x + Board.SQ_SIZE && md.y >= y && md.y < y + Board.SQ_SIZE) {
		                    
		                    switch (p.type) {
			                    case ROOK:
			                    	chP.getSim().add(new Rook(chP.getCurrentTurn(), activePiece.col, activePiece.row));
			                        break;
			                        
			                    case KNIGHT:
			                    	chP.getSim().add(new Knight(chP.getCurrentTurn(), activePiece.col, activePiece.row));
			                        break;
			                        
			                    case BISHOP:
			                    	chP.getSim().add(new Bishop(chP.getCurrentTurn(), activePiece.col, activePiece.row));
			                        break;
			                        
			                    case QUEEN:
			                    	chP.getSim().add(new Queen(chP.getCurrentTurn(), activePiece.col, activePiece.row));
			                        break;
			                        
			                    default:
			                        break;
		                    }
		                    
		                    chP.getSim().remove(activePiece.getIndex());
		                    chP.copyPieces(chP.getSim(), chP.getPieces());
		                    
		                    activePiece = null;
//		                    chP.promoting = false;
		                    chP.setPromoting(false);
		                    changeTurn();
		                    
		                    break;
		            }
			 }
		 }
	}

	
}
