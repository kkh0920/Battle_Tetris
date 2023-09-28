package kr.ac.jbnu.se.tetris;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class BoardPlayer extends Board {

    private Player player;
    
    public BoardPlayer(Tetris parent) {
        super(parent);
        //TODO Auto-generated constructor stub 
        
        player = new Player();
        timer = new Timer(400, this);
        
        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {
            isFallingFinished = false;
            if (!newPiece()) {
                if(opponent.isStarted){
                    // 플레이어 혹은 ai가 승리한 경우

                }
                opponent.isStarted = false;
                opponent.timer.stop();
                
                isStarted = false;
                timer.stop();

                parent.gameManager().gameOverDialog().setVisible(true);
            }
        } 
        else {
            oneLineDown();
        }
    }
}
