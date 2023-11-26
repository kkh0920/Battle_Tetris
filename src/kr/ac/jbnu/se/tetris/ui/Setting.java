package kr.ac.jbnu.se.tetris.ui;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Setting extends JFrame {

    private Select home;

    private Backgrounds backgrounds;
    private CustomButton musicButton, changekey, backselect;
    private boolean isMusicStarted = true;

    private KeyChange keyChange;

    Setting(Select home) {
        this.home = home;
        keyChange = new KeyChange();
        setFrame();
    }

    private void setFrame() {
        setSize(home.getWidth(), home.getHeight());
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        backgrounds = new Backgrounds("image/Background.jpg", home.getWidth(), home.getHeight());
        setButton();
        add(musicButton); add(changekey); add(backselect); add(backgrounds.getPane());
    }

    private void addButton() {
        musicButton = new CustomButton(new ImageIcon("image/buttons/musicOn.png"));
        changekey = new CustomButton(new ImageIcon("image/buttons/keychange.png"));
        backselect = new CustomButton(new ImageIcon("image/buttons/back.png"));
    }

    private void setButton() {
        addButton();
        
        musicButton.setBounds(350, 40, 100, 100);
        changekey.setBounds(250, 250, 300, 150);
        backselect.setBounds(20, 20, 70, 58);

        buttonAction();
    }

    private void pauseMusic(){
        isMusicStarted = !isMusicStarted;
        if(isMusicStarted){
            musicButton.setIcon(new ImageIcon("image/buttons/musicOn.png"));
            home.getMusic().startMusic();
        }
        else{
            musicButton.setIcon(new ImageIcon("image/buttons/musicOff.png"));
            home.getMusic().stopMusic();
        }
    }

    private void buttonAction() {
        musicButton.addActionListener(e-> pauseMusic() );

        changekey.addActionListener(e-> keyChange.setVisible(true) );

        backselect.addActionListener(e-> {
            setVisible(false);
            home.setVisible(true);
        });
    }
}
