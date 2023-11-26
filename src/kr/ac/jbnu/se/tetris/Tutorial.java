package kr.ac.jbnu.se.tetris;
import javax.swing.JFrame;

public class Tutorial extends JFrame {
    
    private String tutorialpng = "image/tutorials.png";
    
    Tutorial() {
        setFrame();
    }

    private void setFrame() {
        Backgrounds background = new Backgrounds(tutorialpng, 299, 537);
        add(background.getPane());
        setSize(299, 537);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}