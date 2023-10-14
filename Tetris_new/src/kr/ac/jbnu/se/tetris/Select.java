package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Select extends JFrame {

    public static final int Frame_X = 800, Frame_Y = 453;
    private final int Bt_W = 200, Bt_H = 267, Bt_Y = 180;

    private Backgrounds background;
    private Music music;

    private SelectLevel selectLevel;

    private JButton ai, versus, tutorial, settingBtn;
    
    private Setting setting;
    private Tutorial tuto;

    Select() throws UnsupportedAudioFileException, LineUnavailableException {
        setFrame();

        selectLevel = new SelectLevel(this);
        setting = new Setting(this);
        
        music = new Music();
        music.startMusic();
    }

    public Music getMusic(){
        return music;
    }

    public void setFrame() {
        setSize(Frame_X, Frame_Y);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // background = new Backgrounds("TetrisCode/image/backg.png", Frame_X, Frame_Y);
        background = new Backgrounds("image\\Background.jpg", Frame_X, Frame_Y);
        setButton();
        add(ai); add(versus); add(settingBtn); add(background.getPane());
    }

    public void setButton() {
        
        // ai = new JButton(new ImageIcon("TetrisCode/image/AImodes.png"));
        ai = new JButton(new ImageIcon("image\\AImodes.png"));
        ai.setBounds(40,Bt_Y,Bt_W,Bt_H);
        setButtonBorder(ai);

        // versus = new JButton(new ImageIcon("TetrisCode/image/2P_modes.png"));
        versus = new JButton(new ImageIcon("image\\2P_modes.png"));
        versus.setBounds(290,Bt_Y,Bt_W,Bt_H);
        setButtonBorder(versus);

        // settingBtn = new JButton(new ImageIcon("TetrisCode/image/settings.png"));
        settingBtn = new JButton(new ImageIcon("image\\settings.png"));
        settingBtn.setBounds(540,Bt_Y,Bt_W,Bt_H);
        setButtonBorder(settingBtn);
        buttonAction();
    }

    private void setButtonBorder(JButton jb){ // 버튼의 이미지를 나타내기 위해 테두리 등을 제거 (부드럽게 나타내기 .. )
        jb.setBorderPainted(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setOpaque(false);
    }

    public void buttonAction() {
        Select select = this;
        ai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                selectLevel.setVisible(true);
            }
        });

        versus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

                TetrisGameManager.level = 0; // 플레이어 대전인 경우 0 레벨 설정
                
                TetrisGameManager game = new TetrisGameManager(select);
                game.start(false);
                game.setVisible(true);
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

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException {
        Select se = new Select();
        se.setVisible(true);
    }
}