package kr.ac.jbnu.se.tetris;

// import javafx.scene.layout.Background;
// import sun.audio.AudioPlayer;
// import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Setting extends JFrame {

    private Select home;

    private Backgrounds backgrounds;
    private JButton musicButton, changekey, backselect;
    private boolean isMusicStarted = true;

    private KeyChange keyChange;

    Button bt;

    Setting(Select home) throws IOException {
        this.home = home;
        keyChange = new KeyChange();
        setFrame();
    }

    public void setFrame() throws IOException {
        backgrounds = new Backgrounds();
        setButton();
        add(musicButton); add(changekey); add(backselect); add(backgrounds.getPane());
        setSize(Select.Frame_X, Select.Frame_Y);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addButton() {
        // musicButton = new JButton(new ImageIcon("TetrisCode/image/musicOn.png"));
        musicButton = new JButton(new ImageIcon("image\\musicOn.png"));

        changekey = new JButton("키 변경");

        // backselect = new JButton(new ImageIcon("TetrisCode/image/back.png"));
        backselect = new JButton(new ImageIcon("image\\back.png"));
    }

    public void setButton() {
        addButton();
        
        bt = new Button(musicButton);
        musicButton.setBounds(50, 60, 100, 100);

        changekey.setBounds(250, 80, 150, 150);

        bt = new Button(backselect);
        backselect.setBounds(0, 0, 70, 58);

        buttonAction();
    }

    private void pauseMusic(){
        isMusicStarted = !isMusicStarted;
        if(isMusicStarted){
            // musicButton.setIcon(new ImageIcon("TetrisCode/image/musicOn.png"));
            musicButton.setIcon(new ImageIcon("image\\musicOn.png"));
            home.getMusic().startMusic();
        }
        else{
            // musicButton.setIcon(new ImageIcon("TetrisCode/image/musicOff.png"));
            musicButton.setIcon(new ImageIcon("image\\musicOff.png"));
            home.getMusic().stopMusic();
        }
    }

    public void buttonAction() {
            musicButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pauseMusic();
                }
            });
            changekey.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keyChange.setVisible(true);
                }
            });
            backselect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {           
                    setVisible(false);
                    home.setVisible(true);
                }
            });
        }
}
