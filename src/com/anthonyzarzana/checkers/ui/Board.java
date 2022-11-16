package com.anthonyzarzana.checkers.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Board extends JPanel {
	
	private static final long serialVersionUID = -6167367864055592046L;

	private BoardSpot[][] spots = new BoardSpot[8][8];
	
	public Board() {
		setSize(500, 500);
		setLayout(new GridLayout(8, 8)); 
		drawBoard();
		setVisible(true);
	}
	
	private void drawBoard() {
		Color color = Color.WHITE;
		for(int i = 0; i < 8; i++) {
			if(color == Color.WHITE)
				color = Color.GREEN;
			 else
				color = Color.WHITE;
			for(int j = 0; j < 8; j++) {
				
				if(color == Color.WHITE)
					color = Color.GREEN;
				 else
					color = Color.WHITE;
				
				BoardSpot spot = new BoardSpot(color);
				spots[i][j] = spot;
				add(spot);
			}
		}
	}
	
	public BoardSpot getBoardSpot(int row, int column) {
		return spots[row][column];
	}
	
	public int[] getBoardSpot(BoardSpot spot) {
		for(int r = 0; r < spots.length; r++) {
			for(int c = 0; c < spots[r].length; c++) {
				BoardSpot currentSpot = spots[r][c];
				if(spot == currentSpot) {
					return new int[] { r, c };
				}
			}
		}
		return null;
	}
	
	public void forEachSpot(RunForEachSpot actionToRun) {
		for(int r = 0; r < spots.length; r++) {
			for(int c = 0; c < spots[r].length; c++) {
				BoardSpot currentSpot = spots[r][c];
				actionToRun.runForEachSpot(currentSpot);
			}
		}
	}
	
	public interface RunForEachSpot {
		void runForEachSpot(BoardSpot spot);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		forEachSpot(spot -> spot.repaint());
	}
}