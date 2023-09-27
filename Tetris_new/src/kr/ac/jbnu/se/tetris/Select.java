package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    static final int Frame_X = 430, Frame_Y = 336;

    private int Bt_W = 100, Bt_H = 133;

    private Backgrounds background;
    private Music music;

    private TetrisGameManager game;

    private JButton ai, versus, tutorial, settingBtn;
    
    private Setting setting;
    
    Button bt;

    Select() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        setFrame();

        setting = new Setting(this);

        music = new Music();
        music.startMusic();
    }

    public Music getMusic(){
        return music;
    }

    public void setFrame() throws IOException {
        background = new Backgrounds();
        setButton();
        add(ai); add(versus); add(settingBtn);add(tutorial); add(background.getPane());
        setSize(Frame_X, Frame_Y);
        setLayout(null);
        setResizable(false);
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

        // settingBtn = new JButton(new ImageIcon("TetrisCode/image/settings.png"));
        settingBtn = new JButton(new ImageIcon("image\\settings.png"));
        settingBtn.setBounds(250,180,Bt_W,Bt_H);
        bt = new Button(settingBtn);

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
                setVisible(false);
                try {
                    game = new TetrisGameManager(true);
                    game.setVisible(true);
                } catch (CloneNotSupportedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }   
            }
        });

        versus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    game = new TetrisGameManager(false);
                    game.setVisible(true);
                } catch (CloneNotSupportedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        settingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                setting.setVisible(true);
            }
        });
    }
}