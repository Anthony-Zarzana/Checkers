package com.anthonyzarzana.checkers.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.anthonyzarzana.checkers.GameController;
import com.anthonyzarzana.checkers.Settings;

import java.awt.Font;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = -4055139307267640840L;

	private JLabel lblScoreRed, lblScoreBlack;
	private JLabel lblCurrentTeam;
	private JPanel scorePanel;
	private JPanel panelInfo;

	public GameFrame() {
		setSize(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);
		setTitle("Checkers");
		getContentPane().setLayout(new BorderLayout());

		panelInfo = new JPanel();
		getContentPane().add(panelInfo, BorderLayout.NORTH);
		panelInfo.setLayout(new BorderLayout(0, 0));

		scorePanel = new JPanel();
		panelInfo.add(scorePanel, BorderLayout.EAST);

		lblScoreRed = new JLabel("Red: 0");
		scorePanel.add(lblScoreRed);
		lblScoreRed.setHorizontalAlignment(SwingConstants.LEFT);

		lblScoreBlack = new JLabel("Black: 0");
		scorePanel.add(lblScoreBlack);

		lblCurrentTeam = new JLabel("Current Team: Red");
		panelInfo.add(lblCurrentTeam, BorderLayout.CENTER);
		panelInfo.setVisible(false);
		lblCurrentTeam.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCurrentTeam.setBackground(Color.GRAY);
		setMinimumSize(new Dimension(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void updateRedScore(int score) {
		lblScoreRed.setText("Red Score: " + score);
	}

	public void updateBlackScore(int score) {
		lblScoreBlack.setText("Black Score: " + score);
	}

	public void gameOver(Color winner) {
		String resultText;
		if (winner == Color.RED) {
			resultText = "red";
		} else {
			resultText = "black";
		}
		JOptionPane.showMessageDialog(this, resultText + " has won the game! Thanks for playing!",
				"Game Over!", JOptionPane.PLAIN_MESSAGE);
		System.exit(1);
	}
	
	public void setCurrentTeamText(String text) {
		lblCurrentTeam.setText(text);
	}

	public void updateCurrentTeamText(Color currentTeam) {
		if(GameController.getInstance().isSingleplayer())
			return;
		if (currentTeam == Color.RED) {
			lblCurrentTeam.setText("Current Team: Red");
		} else {
			lblCurrentTeam.setText("Current Team: Black");
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);
	}

	public JPanel getInfoPanel() {
		return panelInfo;
	}
}