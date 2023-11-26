package kr.ac.jbnu.se.tetris.dialogs;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import kr.ac.jbnu.se.tetris.Select;
import kr.ac.jbnu.se.tetris.TetrisGameManager;

public class PauseDialog extends JDialog {

    TetrisGameManager manager;

    JLabel pauseText = new JLabel("일시 정지");

    JButton resumeBtn = new JButton("계속하기");
    
    JButton retryBtn = new JButton("재시작");
    
    JButton homeBtn = new JButton("메인화면");

    public PauseDialog(TetrisGameManager ownerFrame){
        super(ownerFrame, true);
        manager = ownerFrame;

        setUndecorated(true);
        setSize(170, 175);
        setLocationRelativeTo(null); 
        setLayout(null);

        setComponentBounds();
        addListener();
        addComponent();
    }

    private void setComponentBounds(){
        pauseText.setBounds(60, 5, 150, 30);
        resumeBtn.setBounds(10, 40, 150, 35);
        retryBtn.setBounds(10, 85, 150, 35);
        homeBtn.setBounds(10, 130, 150, 35);
    }

    private void addListener(){
        resumeBtn.addActionListener( e-> manager.pause() );

        retryBtn.addActionListener(e->{
            setVisible(false);
            manager.dispose();
            manager.start(manager.isComputer());
        });

        homeBtn.addActionListener(e->{
            setVisible(false);
            manager.dispose();
            Select mainPage = new Select();
            mainPage.setVisible(true);
        });
    }

    private void addComponent() {
        add(pauseText);
        add(retryBtn);
        add(homeBtn);
    }
}
