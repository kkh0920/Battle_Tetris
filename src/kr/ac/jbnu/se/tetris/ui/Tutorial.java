package kr.ac.jbnu.se.tetris.ui;
import javax.swing.JFrame;

public class Tutorial extends JFrame {
    
    private String tutorialPng = "image/tutorials.png";
    
    public Tutorial() {
        setFrame();
    }

    private void setFrame() {
        Wallpapers backGround = new Wallpapers(tutorialPng, 299, 537);
        add(backGround.getPane());
        setSize(299, 537);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}