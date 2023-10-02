package kr.ac.jbnu.se.tetris;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class Tetris extends JPanel {

    final int Frame_X = 350, Frame_Y = 450, Status_X = 60, Status_Y = 30;
    
    private TetrisGameManager parent;

    private JPanel statusPanel, scorePanel;
    private JLabel statusbar, scoreBar;
    FileWriter MaxScore;
    BufferedReader scoreReading = new BufferedReader(new FileReader("Score\\MaxScore.txt"));
    String writescore = scoreReading.readLine();
    int cmpscore;
    private Board board;
    JProgressBar progressBar = new JProgressBar();

    public Tetris(TetrisGameManager parent, boolean isComputer) throws CloneNotSupportedException, IOException {
        this.parent = parent;
        board = isComputer ? new BoardAI(this, 80) : new BoardPlayer(this);
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
        setJProgressBar();
        setPreferredSize(new Dimension(Frame_X, Frame_Y));
        setBackground(new Color(220, 220, 220));
        setLayout(null);

        BlockPreview blockPreview = board.getBlockPreview();

        statusbar = new JLabel("0");
        scoreBar = new JLabel("최대 점수 : " + writescore);

        statusPanel = new JPanel();
        scorePanel = new JPanel();
        statusPanel.add(statusbar, BorderLayout.CENTER);
        scorePanel.add(scoreBar, BorderLayout.CENTER);

        board.setBounds(10, 10, board.PreferredSizeWidth, board.PreferredSizeHeight);
        blockPreview.setBounds(20 + board.PreferredSizeWidth, 10, blockPreview.panelWidth, blockPreview.panelHeight);
        statusPanel.setBounds(20 + board.PreferredSizeWidth, 10 + board.PreferredSizeHeight - Status_Y, Status_X, Status_Y);
        scorePanel.setBounds(20 + board.PreferredSizeWidth, 10 + board.PreferredSizeHeight - Status_Y - 60, Status_X+20, Status_Y);
        progressBar.setLocation(0 + board.PreferredSizeWidth+30, 10 + board.PreferredSizeHeight - Status_Y - 150);

        add(board, BorderLayout.SOUTH);
        add(blockPreview, BorderLayout.NORTH);
        add(statusPanel);
        add(scorePanel);
        add(progressBar);
    }
    public void FileWriter() throws IOException {
        MaxScore = new FileWriter("Score\\MaxScore.txt");
        MaxScore.write(writescore);
        MaxScore.close();
    }

    public void setJProgressBar() {
        progressBar.setSize(80,30);
        progressBar.setStringPainted(true);
        progressBar.setString("HP");
        progressBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        progressBar.setBackground(Color.RED);
        progressBar.setForeground(Color.WHITE);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
    }
}

