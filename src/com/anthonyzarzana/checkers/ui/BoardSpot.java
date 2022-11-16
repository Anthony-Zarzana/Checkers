package com.anthonyzarzana.checkers.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.anthonyzarzana.checkers.GameController;
import com.anthonyzarzana.checkers.entities.Piece;

public class BoardSpot extends JPanel {

	private static final long serialVersionUID = -8227087959261849883L;

	private boolean mouseIsOnPanel = false;
	
	private Piece piece;
	
	public BoardSpot(Color color) {
		setBackground(color);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(mouseIsOnPanel) {
					mouseClickedSpot();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mouseIsOnPanel = true;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mouseIsOnPanel = false;
			}
		});
		setVisible(true);
	}
	
	private void mouseClickedSpot() {
		int[] location = GameController.getInstance().getBoard().getBoardSpot(this);
		int row = location[0];
		int col = location[1];
		System.out.println("Spot clicked at row=" + row + ", col=" + col);
		GameController.getInstance().spotSelected(this);
	}
	
	public void addBorder() {
		Border border = BorderFactory.createLineBorder(Color.RED);
		setBorder(border);
	}
	
	public void removeBorder() {
		setBorder(null);
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void removePiece() {
		this.piece = null;
	}
	
	public boolean hasPiece() {
		return piece != null;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(hasPiece()) {
			piece.paint(g, this);
		}
	}
}