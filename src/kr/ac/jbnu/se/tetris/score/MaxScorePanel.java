package kr.ac.jbnu.se.tetris.score;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.ac.jbnu.se.tetris.game.TetrisGameManager;

public class MaxScorePanel extends JPanel {

    private JLabel maxScoreLabel;
    private transient FileWriter scoreWriter;
    private transient BufferedReader scoreReading;
    private String maxScore;

    public MaxScorePanel(){
        setMaxScorePanel();
    }

    public int getMaxScore(){
        return Integer.parseInt(maxScore);
    }

    private void setMaxScorePanel() {  
        int i = TetrisGameManager.level;      
        if(i == 0)
            return;

        try {
            scoreReading = new BufferedReader(new FileReader("Score/MaxScore" + i + ".txt"));
            maxScore = scoreReading.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        maxScoreLabel = new JLabel("최대 점수 : " + maxScore);
        add(maxScoreLabel, BorderLayout.CENTER);
    }

    public void fileWriter(int score) {
        int i = TetrisGameManager.level;
        if(i == 0)
            return;

        try {
            scoreWriter = new FileWriter("Score/MaxScore" + i + ".txt");
            scoreWriter.write(Integer.toString(score));
            scoreWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
