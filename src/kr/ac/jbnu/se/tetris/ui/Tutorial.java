package kr.ac.jbnu.se.tetris.ui;
import javax.swing.JFrame;

public class Tutorial extends JFrame {
    
    private String tutorialpng = "image/tutorials.png";
    
    public Tutorial() {
        setFrame();
    }

    private void setFrame() {
        Wallpapers background = new Wallpapers(tutorialpng, 299, 537);
        add(background.getPane());
        setSize(299, 537);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}