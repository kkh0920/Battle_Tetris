package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tetris extends JPanel {
    
    private TetrisGameManager parent;

    private JLabel statusbar;
    
    private Board board;

    private JPanel panel;

    public Tetris(TetrisGameManager parent, boolean isComputer) throws CloneNotSupportedException {
        setPreferredSize(new Dimension(350, 450));
        setBackground(new Color(230, 230, 230));

        this.parent = parent;

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(75, 120));
    
        statusbar = new JLabel("0");

        if(isComputer)
            board = new BoardAI(this, 80);
        else
            board = new BoardPlayer(this);

        panel.add(board.getBlockPreview(), BorderLayout.NORTH);
        panel.add(statusbar, BorderLayout.SOUTH);

        add(board, BorderLayout.WEST);
        add(panel, BorderLayout.EAST);
    }
    public TetrisGameManager gameManager(){
        return parent;
    }
    public JLabel getStatusBar() {
        return statusbar;
    }
    public Board getBoard() {
        return board;
    }
}

