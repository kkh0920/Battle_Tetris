package kr.ac.jbnu.se.tetris;

// import javafx.scene.layout.Background;
// import sun.audio.AudioPlayer;
// import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Setting extends JFrame {

    Backgrounds backgrounds;
    JButton musicOn,musicOff,changekey;
    Button bt;

    //AudioStream BGM; 실질적인 배경음악이 들어가는 객체
    //AudioPlayer a = AudioPlayer.player; //-> AudioPlayer, 배경음악 재생시 필요한 객체


    Setting() throws IOException {
        setFrame();
    }

    public void setFrame() throws IOException {
        backgrounds = new Backgrounds();
        setButton();
        add(musicOn); add(musicOff); add(changekey); add(backgrounds.frame);
        setSize(430,336);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);/*
        musicOn.addActionListener(new AudioSetting());
        musicOff.addActionListener(new AudioSetting());*/ // 배경화면 on off를 위한 버튼 액션 추가
    }

    public void setButton() {
        // musicOn = new JButton(new ImageIcon("TetrisCode/image/musicOn.png"));
        musicOn = new JButton(new ImageIcon("image\\musicOn.png"));
        bt = new Button(musicOn);
        musicOn.setBounds(50,30,100,100);

        // musicOff = new JButton(new ImageIcon("TetrisCode/image/musicOff.png"));
        musicOff = new JButton(new ImageIcon("image\\musicOff.png"));
        bt = new Button(musicOff);
        musicOff.setBounds(50,180,100,100);

        changekey = new JButton("키 변경");
        changekey.setBounds(250,80,150,150);

          changekey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 새로운 프레임 생성, 입력 box에 값 입력 후, 값이 들어왔을 경우 키 값 변경 (키 값 = 다른 클래스에서 변수)
                // 완료 button을 누를 경우 변경 frame은 visible false상태로 전환
            }
        });

    }
    class AudioSetting implements ActionListener { // 배경음악을 키고 끌 수 있게 해주는 클래스
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == musicOn) {
                //a.start(); -> Music Start
            }
            else if(e.getSource() == musicOff){
                // a.stop(); -> Music Stop
            }
        }
    }
}
