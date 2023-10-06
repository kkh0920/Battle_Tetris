package kr.ac.jbnu.se.tetris;
import java.io.IOException;
import javax.swing.JFrame;

public class Tutorial extends JFrame {
    
    private final int tutorial_Frame_X = 315, tutorial_Frame_Y = 430;

    private Backgrounds background;
    
    // private String tutorialpng = "TetrisCode/image/tutorials.png";
    private String tutorialpng = "image\\tutorials.png";
    
    Tutorial() throws IOException {
        setFrame();
    }

    private void setFrame() throws IOException {
        background = new Backgrounds(tutorialpng, tutorial_Frame_X, tutorial_Frame_Y);
        add(background.getPane());
        setSize(tutorial_Frame_X, tutorial_Frame_Y);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}