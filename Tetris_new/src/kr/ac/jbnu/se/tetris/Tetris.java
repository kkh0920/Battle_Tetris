package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tetris extends JPanel {

	Player player;
	JLabel statusbar;
	Board board;

	public Tetris() {
		setPreferredSize(new Dimension(200, 450));
		setBackground(Color.ORANGE);
		
		player = new Player();
		statusbar = new JLabel("0");
		board = new Board(this);
		
		add(board, BorderLayout.WEST);
		add(statusbar, BorderLayout.SOUTH);

		board.start();
	}
	public JLabel getStatusBar() {
		return statusbar;
	}
	public Board getBoard() {
		return board;
	}
	public Player getPlayer(){
		return player;
	}
}

