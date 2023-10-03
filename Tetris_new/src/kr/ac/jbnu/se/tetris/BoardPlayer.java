package kr.ac.jbnu.se.tetris;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.Timer;

public class BoardPlayer extends Board {

    private Player player;
    
    public BoardPlayer(Tetris parent) {
        super(parent);
        //TODO Auto-generated constructor stub 
        
        player = new Player();
        timer = new Timer(800, this);
        
        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {
            isFallingFinished = false;
            if (!newPiece()) {
                if(opponent.isStarted){
                    // 상대 플레이어 혹은 ai가 승리한 경우
                }
                else{
                    // 싱대 플레이어 혹은 ai가 패배한 경우
                }
                gameOver();
            }
        } 
        else {
            try {
                oneLineDown();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
