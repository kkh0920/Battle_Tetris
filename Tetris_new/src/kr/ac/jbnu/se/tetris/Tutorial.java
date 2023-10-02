package kr.ac.jbnu.se.tetris;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Tutorial extends JFrame{
    Backgrounds background;

    static final String tutorialpng = "image\\Tutorial.png";
    Tutorial() throws IOException {
        setFrame();
    }

    public void setFrame() throws IOException {
        background = new Backgrounds(tutorialpng, Select.Frame_X, Select.Frame_Y);
        add(background.getPane());
        setSize(Select.Frame_X, Select.Frame_Y);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}