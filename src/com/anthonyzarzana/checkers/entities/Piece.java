package com.anthonyzarzana.checkers.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.anthonyzarzana.checkers.GameController;
import com.anthonyzarzana.checkers.Settings;
import com.anthonyzarzana.checkers.ui.BoardSpot;

public class Piece {
	
	public int[] location = new int[2];	

	private Color color;
	private boolean isKinged = false;
	
	public Piece(Color color) {
		this.color = color;
	}
	
	public void paint(Graphics g, BoardSpot spot) {
		int size = Settings.PIECE_SIZE;
		int scaleFactorWidth = GameController.getInstance().getFrame().getWidth() / Settings.DEFAULT_WIDTH;
		int scaleFactorHeight = GameController.getInstance().getFrame().getHeight() / Settings.DEFAULT_HEIGHT;
		int x = (spot.getWidth() - size) / 2;
		int y = (spot.getHeight() - size) / 2;
		g.setColor(color);
		
		x -= ((size * scaleFactorWidth) - size) / 2;
		y -= ((size * scaleFactorHeight) - size) / 2;
	
		g.fillOval(x, y, size * scaleFactorWidth, size * scaleFactorHeight);
		
		if(isKinged) {
			size /= 2;
			x = (spot.getWidth() - size) / 2;
			y = (spot.getHeight() - size) / 2;
			x -= ((size * scaleFactorWidth) - size) / 2;
			y -= ((size * scaleFactorHeight) - size) / 2;
			g.setColor(Color.BLUE);
			g.fillOval(x, y, size * scaleFactorWidth, size * scaleFactorHeight);
		}
	}
	
	public void move(int row, int col) {
		location[0] = row;
		location[1] = col;
	}
	
	public void king() {
		this.isKinged = true;
	}
	
	public boolean isKinged() {
		return this.isKinged;
	}
	
	public int[] getLocation() {
		return location;
	}
	
	public Color getColor() {
		return this.color;
	}
}