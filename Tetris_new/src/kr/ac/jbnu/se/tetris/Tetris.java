package kr.ac.jbnu.se.tetris;

import java.awt.*;
import java.io.*;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Tetris extends JPanel {

    final int Frame_X = 350, Frame_Y = 450, Status_X = 60, Status_Y = 30;
    private int second = 1, minute = 0;
    private TetrisGameManager parent;

    private JPanel statusPanel, scorePanel, timerpanel;
    private JLabel statusbar, scoreBar;
    JLabel timernum;
    FileWriter MaxScore;
    BufferedReader scoreReading = new BufferedReader(new FileReader("Score\\MaxScore.txt"));
    String writescore = scoreReading.readLine();
    int cmpscore;
    private Board board;
    JProgressBar jprogressBar = new JProgressBar();
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public Tetris(TetrisGameManager parent, boolean isComputer) throws CloneNotSupportedException, IOException {
        this.parent = parent;
        board = isComputer ? new BoardAI(this, 80) : new BoardPlayer(this);
        setTetrisLayout();
        startTimer();
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
        timernum = new JLabel();

        statusPanel = new JPanel();
        scorePanel = new JPanel();
        timerpanel = new JPanel();

        statusPanel.add(statusbar, BorderLayout.CENTER);
        scorePanel.add(scoreBar, BorderLayout.CENTER);
        timerpanel.add(timernum, BorderLayout.CENTER);

        board.setBounds(10, 10, board.PreferredSizeWidth, board.PreferredSizeHeight);
        blockPreview.setBounds(20 + board.PreferredSizeWidth, 10, blockPreview.panelWidth, blockPreview.panelHeight);
        statusPanel.setBounds(20 + board.PreferredSizeWidth, 10 + board.PreferredSizeHeight - Status_Y, Status_X, Status_Y);
        scorePanel.setBounds(20 + board.PreferredSizeWidth, 10 + board.PreferredSizeHeight - Status_Y - 60, Status_X+20, Status_Y);
        jprogressBar.setLocation(0 + board.PreferredSizeWidth+30, 10 + board.PreferredSizeHeight - Status_Y - 150);
        timerpanel.setBounds(0 + board.PreferredSizeWidth+30, 10 + board.PreferredSizeHeight - Status_Y - 200, Status_X+30, Status_Y);

        add(board, BorderLayout.SOUTH);
        add(blockPreview, BorderLayout.NORTH);
        add(statusPanel);
        add(scorePanel);
        add(jprogressBar);
        add(timerpanel);
    }

    public void startTimer() {
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }
    public void FileWriter() throws IOException {
        MaxScore = new FileWriter("Score\\MaxScore.txt");
        MaxScore.write(writescore);
        MaxScore.close();
    }

    public void setJProgressBar() {
        jprogressBar.setSize(100,40);
        jprogressBar.setStringPainted(true);
        jprogressBar.setString("HP");
        jprogressBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        jprogressBar.setBackground(Color.RED);
        jprogressBar.setForeground(Color.WHITE);
        jprogressBar.setValue(0);
        jprogressBar.setMinimum(0);
        jprogressBar.setMaximum(100);
        jprogressBar.setStringPainted(true);

    }
    Runnable task = new Runnable() {
        @Override
        public void run() {
                timernum.setText(minute + "m " + second + "s");
                repaint();
                second++;
                if(second >= 60) {
                    second -= 60;
                    minute++;
                }
                if(minute > 10) scheduler.shutdown();
        }
    };
}

