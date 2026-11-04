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

	    	 // Synchronize piece lists
//	        chP.copyPieces(chP.getSim(), chP.getPieces());
	    	
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
	     // Set the moved piece as active before checking promotion
//	        chP.setActivePiece(selected);

	        chP.getSelectedPiece().x = pickedCol * Board.SQ_SIZE + Board.HALFSQ;
	        chP.getSelectedPiece().y = pickedRow * Board.SQ_SIZE + Board.HALFSQ;

	        chP.getSelectedPiece().updatePos();

	        
	        
//	        activePiece = selectedPiece;
	        
	        // Check promotion
	        chP.setActivePiece(selected);
	        
	        boolean promotionMove = canPromote();

	        

	        if (promotionMove) {
	            chP.setPromoting(true);
	        } else {
	            changeTurn();
	            chP.setActivePiece(null);
	        }
	        chP.setSelectedPiece(null);
	        chP.getLegalMoves().clear();
//	        if (canPromote()) {
//	        	chP.setPromoting(true);
//	        	
//	        	System.out.println("Select-to-move promotion triggered");
//	            System.out.println("Active piece: " + chP.getActivePiece());
//	            System.out.println("Promotion options: " + chP.getPromoteTo().size());
//       	} else {
//       		changeTurn();
//       		chP.setActivePiece(null);
//       	}
//	        
//	        chP.setSelectedPiece(null);
//	        chP.getLegalMoves().clear();
//	        selectedPiece = null;
//	        legalMoves.clear();
//	        activePiece = null;
	    }
	}
	
	public boolean isKingDoingIllegal(SuperPiece king) {
		
//		if (king.type == Type.KING) {
//			for (SuperPiece p : chP.getPieces()) {
//				if (p != king && p.turn != king.turn && p.canMove(king.col, king.row)) {
//					return true;
//				}
//			}
//		}
//		return false;
		
		 if (king == null) {
		        return false;
		    }

		    if (king.type == Type.KING) {

		        for (SuperPiece p : chP.getSim()) {

		            if (p != king &&
		                p.turn != king.turn &&
		                p.canMove(king.col, king.row)) {

		                return true;
		            }
		        }
		    }

		    return false;
	}
	
	public SuperPiece getKingInCheck() {

	    SuperPiece king = getKing(false);

	    if (king != null && isKingDoingIllegal(king)) {
	        return king;
	    }

	    return null;
	}
	
	
	public boolean hoyCheckPoAngKing() {
		SuperPiece king = getKing(false);
		
		for (SuperPiece p : chP.getPieces()) {
			if (p.turn != king.turn && p.canMove(king.col, king.row)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCheck() {
		 SuperPiece king = getKing(false);

		    if (king == null) {
		        return false;
		    }

		    for (SuperPiece p : chP.getSim()) {

		        if (p.turn != king.turn &&
		            p.canMove(king.col, king.row)) {

		            chP.checks = p;
		            return true;
		        }
		    }

		    chP.checks = null;
		    return false;
	}
	
	public SuperPiece getKing(boolean enemy) {
		
//	SuperPiece king = null;
//			
//	for (SuperPiece p : chP.getPieces()) {
//		if (enemy) {
//			if (p.type == Type.KING && p.turn != chP.getCurrentTurn()) {
//				king = p;
//			}
//		} else {
//			if (p.type == Type.KING && p.turn == chP.getCurrentTurn()) {
//				king = p;
//			}
//		}
//		
//	}
//		return king;	
		
		for (SuperPiece p : chP.getSim()) {

	        if (p.type == Type.KING) {

	            if (enemy && p.turn != chP.getCurrentTurn()) {
	                return p;
	            }

	            if (!enemy && p.turn == chP.getCurrentTurn()) {
	                return p;
	            }
	        }
	    }

	    return null;
	}
	
	public boolean isCheckMate() {
		
		SuperPiece king = getKing(true);
		
		if (kingCanMove(king)) {
			return false;
		} else {
			// checks if a piece can block the attack 
			// checks the pos of checking piece and king
			int colDiff = Math.abs(chP.checks.col - king.col);
			int rowDiff = Math.abs(chP.checks.row - king.row);
			

			if (colDiff == 0) {
				// checkingP is attacking vertically
				if (chP.checks.row < king.row) {
					for (int row = chP.checks.row; row < king.row; row++) {
						for (SuperPiece piece : chP.getSim()) {
							if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(chP.checks.col, row)) {
								return false;
							}
						}
					}
				}
				if (chP.checks.row > king.row) {
					for (int row = chP.checks.row; row > king.row; row--) {
						for (SuperPiece piece : chP.getSim()) {
							if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(chP.checks.col, row)) {
								return false;
							}
						}
					}
				}
			}
			else if (rowDiff == 0) {
				// checkingP is attacking horizontally
				if (chP.checks.col < king.col) {
					for (int col = chP.checks.col; col < king.col; col++) {
						for (SuperPiece piece : chP.getSim()) {
							if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(col, chP.checks.row)) {
								return false;
							}
						}
					}
				}
				if (chP.checks.col > king.col) {
					for (int col = chP.checks.col; col > king.col; col--) {
						for (SuperPiece piece : chP.getSim()) {
							if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(col, chP.checks.row)) {
								return false;
							}
						}
					}
				}
			}
			else if (colDiff == rowDiff) {
				// checkingP is attacking diagonally
				if (chP.checks.row < king.row) {
					if (chP.checks.col < king.col) {
						for (int col = chP.checks.col, row = chP.checks.row; col < king.col; col++, row++) {
							for (SuperPiece piece : chP.getSim()) {
								if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
					if (chP.checks.col > king.col) {
						for (int col = chP.checks.col, row = chP.checks.row; col > king.col; col--, row++) {
							for (SuperPiece piece : chP.getSim()) {
								if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
				}
				if (chP.checks.row > king.row) {
					if (chP.checks.col < king.col) {
						for (int col = chP.checks.col, row = chP.checks.row; col < king.col; col++, row--) {
							for (SuperPiece piece : chP.getSim()) {
								if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
					if (chP.checks.col > king.col) {
						for (int col = chP.checks.col, row = chP.checks.row; col > king.col; col--, row--) {
							for (SuperPiece piece : chP.getSim()) {
								if (piece != king && piece.turn != chP.getCurrentTurn() && piece.canMove(col, row)) {
									return false;
								}
							}
						}
					}
				}
			}
			else {
				// a knight is attacking
			}
		}
		
		return true;
		
	}
	public boolean kingCanMove(SuperPiece king) {
		
		//simulate if there are square that the king can move to
		if (isValidMove(king , -1, -1)) { return true;}
		if (isValidMove(king , 0, -1)) { return true;}
		if (isValidMove(king , 1, -1)) { return true;}
		if (isValidMove(king , -1, 0)) { return true;}
		if (isValidMove(king , 1, 0)) { return true;}
		if (isValidMove(king , -1, 1)) { return true;}
		if (isValidMove(king , 0, 1)) { return true;}
		if (isValidMove(king , 1, 1)) { return true;}
		return false;
		
	}
	public boolean isValidMove(SuperPiece king, int colPlus, int rowPlus) {
		
boolean isValidMove = false;
		
		// update king's pos
		king.col += colPlus;
		king.row += rowPlus;
		
		if (king.canMove(king.col, king.row)) {
			if (king.hittin != null) {
				chP.getSim().remove(king.hittin.getIndex());
				}
			if (isKingDoingIllegal(king) == false) {
				isValidMove = true;
			}
		}
		
		// reset the king's pos and restore the removed piece
		king.resetPos();
		chP.copyPieces(chP.getSim(), chP.getPieces());
		
		return isValidMove;
		
//		return false;
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
//	    int nextTurn = chP.getCurrentTurn() == ChessPanel.WHITE ? ChessPanel.BLACK : ChessPanel.WHITE;
//
//	    chP.setCurrentTurn(nextTurn);
//
//	    for (SuperPiece p : chP.getPieces()) {
//	        if (p.turn == nextTurn) {
//	            p.pawnJump = false;
//	        }
//	    }
		int nextTurn = chP.getCurrentTurn() == ChessPanel.WHITE
		        ? ChessPanel.BLACK
		        : ChessPanel.WHITE;

		    chP.setCurrentTurn(nextTurn);

		    for (SuperPiece p : chP.getSim()) {

		        if (p.turn == nextTurn) {
		            p.pawnJump = false;
		        }
		    }

		    // Check the opponent's King
		    if (isCheck()) {

		        System.out.println("CHECK!");

		        if (isCheckMate()) {

		            System.out.println("CHECKMATE!");

		            // TODO: Set game over state
		        }
		    }
	}
	
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
//		 SuperPiece selected = chP.getSelectedPiece();
		
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
		
		System.out.println("ENTERED PROMOTE()");
		 SuperPiece activePiece = chP.getActivePiece();
		
		if (!chP.isPromoting() == true || activePiece == null) {
	        return;
	    }
		
//		 if (md.pressed) {
		if (md.justPressed) {
	        md.justPressed = false;
			 for (SuperPiece p : chP.getPromoteTo()) {
				 
				 	int x = p.getX(p.col);
		            int y = p.getY(p.row);
		            
		            if (md.x >= x && md.x < x + Board.SQ_SIZE && md.y >= y && md.y < y + Board.SQ_SIZE) {
		            	
		            	chP.getSim().remove(activePiece);
		                    
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
//			                        break;
			                    	return;
		                    }
		                    
		                    chP.copyPieces(chP.getSim(), chP.getPieces());
		                    chP.setActivePiece(null);
		                    chP.setSelectedPiece(null);
		                    chP.getLegalMoves().clear();
		                    chP.getPromoteTo().clear();
		                    chP.setPromoting(false);
		                    changeTurn();
		                    break;
		            }
			 }
		 }
	}

	
}
