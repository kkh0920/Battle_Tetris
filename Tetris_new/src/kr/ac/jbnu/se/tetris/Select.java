package kr.ac.jbnu.se.tetris;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Button {
    Button(JButton jb) { // 버튼의 이미지를 나타내기 위해 테두리 등을 제거 (부드럽게 나타내기 .. )
        jb.setBorderPainted(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setOpaque(false);
    }
}

public class Select extends JFrame {
    int Frame_X = 430, Frame_Y = 336, Bt_W = 100, Bt_H = 133;
    JButton ai,versus,setting,tutorial;
    Button bt;
    BufferedImage img = null;
    BackG background;
    JLayeredPane frame = new JLayeredPane();

    Select() throws IOException {
        setFrame();
    }

    public void drawBackground() throws IOException { // ImageIO.read => 예외처리 -> IOException이 필수
        frame.setSize(Frame_X,Frame_Y);
        frame.setLayout(null);

        // img = ImageIO.read(new File("TetrisCode/image/backg.png"));
        img = ImageIO.read(new File("image\\backg.png"));

        background = new BackG();
        background.setSize(Frame_X,Frame_Y);
        frame.add(background);
    }

    public void setFrame() throws IOException {
        drawBackground();
        setButton();
        add(ai); add(versus); add(setting);add(tutorial);add(frame);
        setSize(Frame_X,Frame_Y);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setButton() {
        
        // ai = new JButton(new ImageIcon("TetrisCode/image/AImodes.png"));
        ai = new JButton(new ImageIcon("image\\AImodes.png"));
        ai.setBounds(250,-20,Bt_W,Bt_H);
        bt = new Button(ai);

        // versus = new JButton(new ImageIcon("TetrisCode/image/2P_modes.png"));
        versus = new JButton(new ImageIcon("image\\2P_modes.png"));
        versus.setBounds(250,80,Bt_W,Bt_H);
        bt = new Button(versus);

        // setting = new JButton(new ImageIcon("TetrisCode/image/settings.png"));
        setting = new JButton(new ImageIcon("image\\settings.png"));
        setting.setBounds(250,180,Bt_W,Bt_H);
        bt = new Button(setting);

        // tutorial = new JButton(new ImageIcon("TetrisCode/image/Tetris.png"));
        tutorial = new JButton(new ImageIcon("image\\Tetris.png"));
        tutorial.setBounds(30,100,150,98);
        bt = new Button(tutorial);
        buttonAction();
    }

    public void buttonAction() {
        ai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TetrisGameManager tr;
                setVisible(false);
                try {
                    tr = new TetrisGameManager(true);
                    tr.setVisible(true);
                } catch (CloneNotSupportedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }   
            }
        });

        versus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TetrisGameManager tr;
                setVisible(false);
                try {
                    tr = new TetrisGameManager(false);
                    tr.setVisible(true);
                } catch (CloneNotSupportedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Setting st;
                setVisible(false);
                try {
                    st = new Setting();
                    st.setVisible(true);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } 
            }
        });
    }
    
    class BackG extends JPanel { // Panel에 배경화면을 나타내기 위해서,
        public void paint(Graphics g){
            g.drawImage(img, 0,0,null);
        }
    }

}