package kr.ac.jbnu.se.tetris;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Backgrounds extends JFrame {
    int Frame_X = 430, Frame_Y = 336;
    BufferedImage img = null;
    BackG background;
    JLayeredPane frame = new JLayeredPane();
    Backgrounds() throws IOException {
        DrawBackGround();
    }

    public void DrawBackGround() throws IOException { // LayerdPane에 이미지를 덮어씌우는 메소드
        frame.setSize(Frame_X,Frame_Y);
        frame.setLayout(null);

        img = ImageIO.read(new File("TetrisCode/image/backg.png"));
        // img = ImageIO.read(new File("image\\backg.png"));

        background = new BackG();
        background.setSize(Frame_X,Frame_Y);
        frame.add(background);
    }

    class BackG extends JPanel { // Panel에 이미지를 나타내기 위해서,
        public void paint(Graphics g){
            g.drawImage(img, 0,0,null);
        }
    }
}
