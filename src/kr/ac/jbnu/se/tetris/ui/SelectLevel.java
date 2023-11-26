package kr.ac.jbnu.se.tetris.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import kr.ac.jbnu.se.tetris.board.BoardAI;
import kr.ac.jbnu.se.tetris.game.TetrisGameManager;

public class SelectLevel extends JFrame {

    private Select home;

    private final int LevelNumber = 5;

    private int btnWidth = 120;
    private int btnHeight = 35;

    private CustomButton[] level;

    private CustomButton backselect;

    private Backgrounds backgrounds;

    public SelectLevel(Select home){
        this.home = home;

        level = new CustomButton[LevelNumber];
        for(int i = 1; i <= LevelNumber; i++){
            level[i - 1] = new CustomButton(new ImageIcon("image/buttons/level" + i + ".png")); // 레벨 이미지 지정 필요
        }

        backselect = new CustomButton(new ImageIcon("image/buttons/back.png"));

        backgrounds = new Backgrounds("image/Background.jpg", home.getWidth(), home.getHeight());

        setFrame();
    }

    private void setFrame() {
        setSize(home.getWidth(), home.getHeight());
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setButton();

        for(int i = 0; i < LevelNumber; i++){
            add(level[i]);
        }

        add(backselect);
        add(backgrounds.getPane());
    }

    private void setButton() {
        for(int i = 0; i < LevelNumber; i++){
            level[i].setBounds(10 + (30 * (i + 1)) + i * btnWidth, home.getHeight() - 150, btnWidth, btnHeight);
        }
        backselect.setBounds(20, 20, 70, 58);
        addButtonAction();
    }

    private void addButtonAction() {
        for(int i = 0; i < LevelNumber; i++) {
            int index = i;
            level[index].addActionListener(e->{
                dispose();
                    
                TetrisGameManager.level = index + 1; // level 설정
                BoardAI.moveDelay = 160 - TetrisGameManager.level * 20;  // level에 따른 ai 속도 조정
                
                TetrisGameManager game = new TetrisGameManager();
                game.start(true);
            });
        }
        backselect.addActionListener(e->{
            dispose();
            home.setVisible(true);
        });
    }   
}
