package com.anthonyzarzana.checkers.ui;

import javax.swing.JPanel;

import com.anthonyzarzana.checkers.GameController;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 629549046502326904L;

	public MenuPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton btnSinglePlayer = new JButton("Single Player");
		btnSinglePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameController.getInstance().playSingleplayer();
			}
		});
		add(btnSinglePlayer);
		
		JButton btnMultiplayer = new JButton("Multiplayer");
		btnMultiplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnMultiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameController.getInstance().playMultiplayer();
			}
		});
		add(btnMultiplayer);
	}
}