package com.anthonyzarzana.checkers.ai;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.anthonyzarzana.checkers.GameController;
import com.anthonyzarzana.checkers.entities.Piece;
import com.anthonyzarzana.checkers.ui.BoardSpot;
import com.anthonyzarzana.checkers.util.CanJumpResult;
import com.anthonyzarzana.checkers.util.CanMoveResult;

public class AIHelper {

	public static CanMoveResult findPieceToMove() {
		ArrayList<BoardSpot> blackSpots = getBlackSpots();
		HashMap<BoardSpot, CanJumpResult> spotsThatCanJump = new HashMap<>();
		HashMap<BoardSpot, CanMoveOnceResult> spotsThatCanMoveOnce = new HashMap<>();
		for (BoardSpot spot : blackSpots) {
			if (canJumpPiece(spot).canJump) {
				CanJumpResult result = canJumpPiece(spot);
				spotsThatCanJump.put(spot, result);
			} else {
				if (canMoveOneSpot(spot).canMove) {
					CanMoveOnceResult result = canMoveOneSpot(spot);
					spotsThatCanMoveOnce.put(spot, result);
				}
			}
		}

		// calculate chances algorithm
		// if there is/are piece(s) to jump, run an x % chance to see if AI will jump
		// if decided not to jump
		// run x percent chance to decide if the AI should move the farthest piece
		// if not move random piece once
		if (!spotsThatCanJump.isEmpty()) {
			int shouldJump = new Random().nextInt(100);
			if (shouldJump < 90 || spotsThatCanMoveOnce.isEmpty()) {
				// Do jump and return
				CanJumpResult result = (CanJumpResult) spotsThatCanJump.values().toArray()[0];
				return result;
			}
		}

		ArrayList<BoardSpot> spotsToMove = new ArrayList<BoardSpot>();
		for (BoardSpot spot : spotsThatCanMoveOnce.keySet()) {
			spotsToMove.add(spot);
		}

		// BoardSpot[] spotsToMove =
		// (BoardSpot[])spotsThatCanMoveOnce.keySet().toArray();
		int shouldFavorFarthestPiece = new Random().nextInt(100);
		if (shouldFavorFarthestPiece > 50 && !spotsToMove.isEmpty()) {
			// pick farthest piece and move then return
			BoardSpot spotToMove = getSpotFarthestAway(spotsToMove);
			CanMoveOnceResult result = spotsThatCanMoveOnce.get(spotToMove);
			return result;
		} else {
			// pick random piece to move then return
			int index = 0;
			if (spotsToMove.size() > 1) {
				Random rdm = new Random();
				index = rdm.nextInt(spotsToMove.size() - 1);
			}
			BoardSpot spotToMove = spotsToMove.get(index);
			CanMoveOnceResult result = spotsThatCanMoveOnce.get(spotToMove);
			return result;
		}
	}

	private static CanJumpResult canJumpPiece(BoardSpot spot) {
		CanJumpResult result = new CanJumpResult();
		result.canJump = false;
		result.fromSpot = spot;
		// assuming the given piece is black, because black is AI controlled
		Piece piece = spot.getPiece();

		int[] fromLocation = GameController.getInstance().getBoard().getBoardSpot(spot);
		int fromX = fromLocation[1];
		int fromY = fromLocation[0];

		int targetX;
		int targetY;
		int jumpedX;
		int jumpedY;

		BoardSpot jumpedSpot;
		BoardSpot targetSpot;

		// look down
		jumpedY = fromY + 1;
		targetY = fromY + 2;
		if (fromY <= 5) {
			// look left
			if (fromX >= 2) {
				jumpedX = fromX - 1;
				targetX = fromX - 2;
				jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
				targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
				if (jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() == Color.RED && !targetSpot.hasPiece()) {
					// can jump left
					result.targetSpot = targetSpot;
					result.canJump = true;
				}
			}

			// look right
			if (fromX <= 5) {
				jumpedX = fromX + 1;
				targetX = fromX + 2;
				jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
				targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
				if (jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() == Color.RED && !targetSpot.hasPiece()) {
					// can jump right
					result.targetSpot = targetSpot;
					result.canJump = true;
				}
			}
			
			if (piece.isKinged()) {

				// look up
				jumpedY = fromY - 1;
				targetY = fromY - 2;
				if (fromY >= 2) {
					// look left
					if (fromX >= 2) {
						jumpedX = fromX - 1;
						targetX = fromX - 2;
						jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
						targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
						if (jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() == Color.RED
								&& !targetSpot.hasPiece()) {
							// can jump left
							result.targetSpot = targetSpot;
							result.canJump = true;
						}
					}

					// look right
					if (fromX <= 5) {
						jumpedX = fromX + 1;
						targetX = fromX + 2;
						jumpedSpot = GameController.getInstance().getBoard().getBoardSpot(jumpedY, jumpedX);
						targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
						if (jumpedSpot.hasPiece() && jumpedSpot.getPiece().getColor() == Color.RED
								&& !targetSpot.hasPiece()) {
							// can jump right
							result.targetSpot = targetSpot;
							result.canJump = true;
						}
					}
				}
			}
		}
		return result;
	}

	private static CanMoveOnceResult canMoveOneSpot(BoardSpot spot) {
		CanMoveOnceResult result = new CanMoveOnceResult();
		result.fromSpot = spot;
		result.canMove = false;
		// assuming the given piece is black, because black is AI controlled
		Piece piece = spot.getPiece();

		int[] fromLocation = GameController.getInstance().getBoard().getBoardSpot(spot);
		int fromX = fromLocation[1];
		int fromY = fromLocation[0];

		int targetX;
		int targetY;

		BoardSpot targetSpot;

		if (!piece.isKinged()) {
			// look down
			targetY = fromY + 1;
			if (fromY < 7) {
				// look left
				if (fromX >= 1) {
					targetX = fromX - 1;
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					if (!targetSpot.hasPiece()) {
						// can jump left
						result.targetSpot = targetSpot;
						result.canMove = true;
					}
				}

				// look right
				if (fromX < 7) {
					targetX = fromX + 1;
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					if (!targetSpot.hasPiece()) {
						// can jump right
						result.targetSpot = targetSpot;
						result.canMove = true;
					}
				}
			}

		} else {

			// look down
			targetY = fromY + 1;
			if (fromY <= 5) {
				// look left
				if (fromX >= 1) {
					targetX = fromX - 1;
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					if (!targetSpot.hasPiece()) {
						// can jump left
						result.targetSpot = targetSpot;
						result.canMove = true;
					}
				}

				// look right
				if (fromX <= 6) {
					targetX = fromX + 1;
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					if (!targetSpot.hasPiece()) {
						// can jump right
						result.targetSpot = targetSpot;
						result.canMove = true;
					}
				}
			}

			// look up
			targetY = fromY - 1;
			if (fromY >= 1) {
				// look left
				if (fromX >= 1) {
					targetX = fromX - 1;
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					if (!targetSpot.hasPiece()) {
						// can jump left
						result.targetSpot = targetSpot;
						result.canMove = true;
					}
				}

				// look right
				if (fromX <= 6) {
					targetX = fromX + 1;
					targetSpot = GameController.getInstance().getBoard().getBoardSpot(targetY, targetX);
					if (!targetSpot.hasPiece()) {
						// can jump right
						result.targetSpot = targetSpot;
						result.canMove = true;
					}
				}
			}
		}
		return result;
	}

	private static ArrayList<BoardSpot> getBlackSpots() {
		ArrayList<BoardSpot> pieces = new ArrayList<>();
		GameController.getInstance().getBoard().forEachSpot((spot) -> {
			if (spot.getPiece() != null) {
				if (spot.getPiece().getColor() == Color.BLACK) {
					pieces.add(spot);
				}
			}
		});
		return pieces;
	}

	private static BoardSpot getSpotFarthestAway(ArrayList<BoardSpot> spots) {
		BoardSpot highest = spots.get(0);
		for (BoardSpot spot : spots) {
			int[] location = GameController.getInstance().getBoard().getBoardSpot(spot);
			int[] highestLocation = GameController.getInstance().getBoard().getBoardSpot(highest);
			int currentHeight = location[0];
			int highestHeight = highestLocation[0];
			if (highestHeight > currentHeight) {
				highest = spot;
			}
		}
		return highest;
	}
}