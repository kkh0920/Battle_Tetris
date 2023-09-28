package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tetris extends JPanel {

    final int Frame_X = 350, Frame_Y = 450, Status_X = 60, Status_Y = 30;
    
    private TetrisGameManager parent;

    private JPanel statusPanel;
    private JLabel statusbar;
    
    private Board board;

    public Tetris(TetrisGameManager parent, boolean isComputer) throws CloneNotSupportedException {
        this.parent = parent;
        board = isComputer ? new BoardAI(this, 70) : new BoardPlayer(this);
        setTetrisLayout();
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

    private void setTetrisLayout(){
        setPreferredSize(new Dimension(Frame_X, Frame_Y));
        setBackground(new Color(220, 220, 220));
        setLayout(null);

        BlockPreview blockPreview = board.getBlockPreview();

        statusbar = new JLabel("0");

        statusPanel = new JPanel();
        statusPanel.add(statusbar, BorderLayout.CENTER);

        board.setBounds(10, 10, board.PreferredSizeWidth, board.PreferredSizeHeight);
        blockPreview.setBounds(20 + board.PreferredSizeWidth, 10, blockPreview.panelWidth, blockPreview.panelHeight);
        statusPanel.setBounds(20 + board.PreferredSizeWidth, 10 + board.PreferredSizeHeight - Status_Y, Status_X, Status_Y);
        
        add(board, BorderLayout.SOUTH);
        add(blockPreview, BorderLayout.NORTH);
        add(statusPanel);
    }
}

