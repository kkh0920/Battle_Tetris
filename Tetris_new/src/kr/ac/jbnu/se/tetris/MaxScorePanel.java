package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MaxScorePanel extends JPanel {
    private JLabel maxScoreLabel;
    private FileWriter scoreWriter;

    private BufferedReader scoreReading;
    private String maxScore;

    public MaxScorePanel(){
        setMaxScorePanel();
    }

    public int getMaxScore(){
        return Integer.parseInt(maxScore);
    }

    private void setMaxScorePanel() {  
        try {
            // scoreReading = new BufferedReader(new FileReader("TetrisCode/Score/MaxScore.txt"));
            scoreReading = new BufferedReader(new FileReader("Score\\MaxScore.txt"));
            maxScore = scoreReading.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        maxScoreLabel = new JLabel("최대 점수 : " + maxScore);
        add(maxScoreLabel, BorderLayout.CENTER);
    }

    public void FileWriter(int score) {
        try {
            // scoreWriter = new FileWriter("TetrisCode/Score/MaxScore.txt");
            scoreWriter = new FileWriter("Score\\MaxScore.txt");
            scoreWriter.write(Integer.toString(score));
            scoreWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
