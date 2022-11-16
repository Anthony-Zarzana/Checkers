package com.anthonyzarzana.checkers.util;

import com.anthonyzarzana.checkers.entities.Piece;
import com.anthonyzarzana.checkers.ui.BoardSpot;

public class CanJumpResult extends CanMoveResult {
	public boolean canJump = false;
	public Piece pieceToJump;
	public BoardSpot targetSpot;
}