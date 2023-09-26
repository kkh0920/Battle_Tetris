package kr.ac.jbnu.se.tetris;

import javafx.scene.layout.Background;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;



public class Setting extends JFrame {

    Backgrounds backgrounds;
    JButton musicOn,musicOff,changekey,backselect;
    Button bt;

    Music music;




    Setting() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        setFrame();
    }

    public void setFrame() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        backgrounds = new Backgrounds();
        setButton();
        add(musicOn); add(musicOff); add(changekey); add(backselect); add(backgrounds.frame);
        setSize(430,336);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addButton() {

        musicOn = new JButton(new ImageIcon("image\\musicOn.png"));

        musicOff = new JButton(new ImageIcon("image\\musicOff.png"));

        backselect = new JButton(new ImageIcon("image\\back.png"));
    }

    public void setButton() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        addButton();
        music = new Music();

        bt = new Button(musicOn);
        musicOn.setBounds(50, 60, 100, 100);

        bt = new Button(musicOff);
        musicOff.setBounds(50, 180, 100, 100);

        changekey = new JButton("키 변경");
        changekey.setBounds(250, 80, 150, 150);

        bt = new Button(backselect);
        backselect.setBounds(0, 0, 70, 58);

        buttonAction();
    }
    public void buttonAction() {

            changekey.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new KeyChange();
                }
            });
            musicOn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    music.startMusic();
                }
            });
            musicOff.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    music.stopMusic();
                }
            });

            backselect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        new Select();
                        setVisible(false);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
}
