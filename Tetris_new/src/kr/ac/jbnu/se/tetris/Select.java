package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
   // BackG background;
    JLayeredPane frame = new JLayeredPane();
    Backgrounds backgrounds;

    Select() throws IOException {
        setFrame();
    }
    public void setFrame() throws IOException {
        //drawBackground();
        backgrounds = new Backgrounds();

        setButton();
        add(ai); add(versus); add(setting);add(tutorial);add(backgrounds.frame);
        setSize(Frame_X,Frame_Y);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setButton() {
        ai = new JButton(new ImageIcon("image\\AImodes.png"));
        ai.setBounds(250,-20,Bt_W,Bt_H);
        bt = new Button(ai);

        versus = new JButton(new ImageIcon("Image\\2P_modes.png"));
        versus.setBounds(250,80,Bt_W,Bt_H);
        bt = new Button(versus);

        setting = new JButton(new ImageIcon("image\\settings.png"));
        setting.setBounds(250,180,Bt_W,Bt_H);
        bt = new Button(setting);

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
                Setting st = null;
                setVisible(false);
                try {
                    st = new Setting();
                    st.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}