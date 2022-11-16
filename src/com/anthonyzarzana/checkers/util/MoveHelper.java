package com.anthonyzarzana.checkers.util;

import java.awt.Color;

import com.anthonyzarzana.checkers.GameController;
import com.anthonyzarzana.checkers.entities.Piece;
import com.anthonyzarzana.checkers.ui.BoardSpot;

public class MoveHelper {

	/**** Helpers ****/
	
	public static boolean canMoveOneSpot(int[] fromLocation, int[] toLocation, Piece piece) {
		int fromY = fromLocation[0];
		int toY = toLocation[0];
		int fromX = fromLocation[1];
		int toX = toLocation[1];
		int deltaY = toY - fromY;
		int deltaX = toX - fromX;
		Color color = piece.getColor();

		if (deltaX != 1 && deltaX != -1) {
			return false;
		}

		if (deltaY != 1 && deltaY != -1) {
			return false;
		}

		if (color == Color.RED) {
			if (deltaY == -1) {
				return true;
			} else if (deltaY == 1) {
				if (piece.isKinged()) {
					return true;
				}
			}
		} else {
			if (deltaY == 1) {
				return true;
			} else if (deltaY == -1) {
				if (piece.isKinged()) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean pieceShouldBeKinged(BoardSpot newSpot) {
		Piece piece = newSpot.getPiece();
		Color color = piece.getColor();
		int spotY = GameController.getInstance().getBoard().getBoardSpot(newSpot)[0];
		if (color == Color.RED) {
			if (spotY == 0)
				return true;
		} else if (color == Color.BLACK) {
			if (spotY == 7)
				return true;
		}
		return false;
	}

	public static CanJumpResult canJump(int[] fromLocation, int[] toLocation, Piece piece) {
		CanJumpResult result = new CanJumpResult();
		result.canJump = false;
		int fromY = fromLocation[0];
		int fromX = fromLocation[1];
		int toY = toLocation[0];
		int toX = toLocation[1];
		int deltaX = toX - fromX;
		int deltaY = toY - fromY;
		Color movedPieceColor = piece.getColor();
		int targetY;
		if (movedPieceColor == Color.RED)
			targetY = -2;
		else
			targetY = 2;
		if (deltaY == targetY || piece.isKinged()) {
			if (piece.isKinged()) {
				if (deltaY != 2 && deltaY != -2)
					return result;
			}
			// move right
			int jumpedX = fromX + 1;

			if (deltaX > 0) {
				// move right
				jumpedX = fromX + 1;
			} else {
				// move left
				jumpedX = fromX - 1;
			}
			int jumpedY = 0;
			if (!piece.isKinged()) {
				if (movedPieceColor == Color.RED) {
					jumpedY = fromY - 1;
				} else {
					jumpedY = fromY + 1;
				}
			} else {
				if (fromY < toY) {
					jumpedY = fromY + 1;
				} else {
					jumpedY = fromY - 1;
				}
			}
			BoardSpot spotToJump = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
			if (spotToJump.hasPiece()) {
				if (spotToJump.getPiece().getColor() != movedPieceColor) {
					result.canJump = true;
					result.pieceToJump = spotToJump.getPiece();
				}
			}
		}
		return result;
	}

	private static CanDoubleJumpResult canDoubleJump(int[] to, Piece piece) {
		CanDoubleJumpResult result = new CanDoubleJumpResult();
		result.canJump = false;
		int targetY;
		int targetX;
		int jumpedX;
		int jumpedY;
		BoardSpot targetSpot;
		BoardSpot jumpedSpot;
		Color color = piece.getColor();
		if (!piece.isKinged()) {
			if (color == Color.RED) {
				targetY = to[0] - 2;
				jumpedY = to[0] - 1;
			} else {
				targetY = to[0] + 2;
				jumpedY = to[0] + 1;
			}
			// look left
			targetX = to[1] - 2;
			jumpedX = to[1] - 1;
			if (targetY < 8  && targetY >= 0 && targetX < 8 && targetX >= 0) {
				targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
				jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
				if (!targetSpot.hasPiece() && jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() != color) {
					result.canJump = true;
					result.pieceToJump = jumpedSpot.getPiece();
					result.newLocation = new int[] { targetY, targetX };
				}
			}

			// look right
			targetX = to[1] + 2;
			jumpedX = to[1] + 1;
			if (targetY < 8  && targetY >= 0 && targetX < 8 && targetX >= 0) {
				targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
				jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
				if (!targetSpot.hasPiece() && jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() != color) {
					result.canJump = true;
					result.pieceToJump = jumpedSpot.getPiece();
					result.newLocation = new int[] { targetY, targetX };
				}
			}
		} else {
			int[] targetYRange = new int[2];
			targetYRange[0] = to[0] - 2;
			targetYRange[1] = to[0] + 2;
			for(int y : targetYRange) {
				targetY = y;
				if(y == to[0] - 2) {
					jumpedY = to[0] - 1;
				} else {
					jumpedY = to[0] + 1;
				}
				
				// look left
				targetX = to[1] - 2;
				jumpedX = to[1] - 1;
				if (targetY < 8  && targetY >= 0 && targetX < 8 && targetX >= 0) {
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
					if (!targetSpot.hasPiece() && jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() != color) {
						result.canJump = true;
						result.pieceToJump = jumpedSpot.getPiece();
						result.newLocation = new int[] { targetY, targetX };
					}
				}

				// look right
				targetX = to[1] + 2;
				jumpedX = to[1] + 1;
				if (targetY < 8  && targetY >= 0 && targetX < 8 && targetX >= 0) {
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
					if (!targetSpot.hasPiece() && jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() != color) {
						result.canJump = true;
						result.pieceToJump = jumpedSpot.getPiece();
						result.newLocation = new int[] { targetY, targetX };
					}
				}
			}
		}
		return result;
	}

	private static void doubleJump(int[] location, Piece piece) {
		BoardSpot oldSpot; 
		while (canDoubleJump(location, piece).canJump) {
			CanDoubleJumpResult result = canDoubleJump(location, piece);
			GameController.getInstance().jumpPiece(result.pieceToJump, piece);
			oldSpot = GameController.getInstance().getBoard().getBoardSpot(location[0], location[1]);
			location = result.newLocation;
			BoardSpot newSpot = GameController.getInstance().getBoard().getBoardSpot(location[0], location[1]);
			GameController.getInstance().selectSpot(oldSpot);
			GameController.getInstance().movePiece(newSpot);
			if (pieceShouldBeKinged(newSpot)) {
				newSpot.getPiece().king();
			}
			System.out.println("Executed multi jump");
		}
	}

	/*********************/
	
	public static void doMove(BoardSpot from, BoardSpot to) {
		int[] fromLocation = GameController.getInstance().getBoard().getBoardSpot(from);
		int[] toLocation = GameController.getInstance().getBoard().getBoardSpot(to);
		int fromY = fromLocation[0];
		int fromX = fromLocation[1];
		int toY = toLocation[0];
		int toX = toLocation[1];
		int deltaX = toX - fromX;
		int deltaY = toY - fromY;

		Piece piece = from.getPiece();
		
		if (deltaY == 0 || deltaX == 0)
			return;

		if (deltaY == 1 || deltaY == -1) {
			if (canMoveOneSpot(fromLocation, toLocation, from.getPiece())) {
				// move
				GameController.getInstance().movePiece(to);
			} else
				return;
		} else if (deltaX == 2 || deltaX == -2) {
			if (canJump(fromLocation, toLocation, from.getPiece()).canJump) {
				// jump
				CanJumpResult result = canJump(fromLocation, toLocation, from.getPiece());
				GameController.getInstance().jumpPiece(result.pieceToJump, from.getPiece());
				GameController.getInstance().movePiece(to);
				if (canDoubleJump(toLocation, piece).canJump) {
					// double jump
					doubleJump(toLocation, piece);
				}
			} else
				return;
		}

		if (to.getPiece() != null && pieceShouldBeKinged(to)) {
			to.getPiece().king();
		}
		
		GameController.getInstance().swapCurrentPlayer();
	}
}