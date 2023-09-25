package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tetris extends JPanel {

    JLabel statusbar;
    Board board;
    boolean isComputer;

    public Tetris(boolean isComputer) throws CloneNotSupportedException {
        setPreferredSize(new Dimension(200, 450));
        setBackground(new Color(230, 230, 230));
        
        this.isComputer = isComputer;

        statusbar = new JLabel("0");

        if(isComputer)
            board = new BoardAI(this);
        else
            board = new BoardPlayer(this);

        add(board, BorderLayout.WEST);
        add(statusbar, BorderLayout.SOUTH);
    }
    
    public JLabel getStatusBar() {
        return statusbar;
    }
    public Board getBoard() {
        return board;
    }
    public boolean isComputer(){
        return isComputer;
    }
}

