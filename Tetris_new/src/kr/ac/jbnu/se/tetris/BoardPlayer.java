package kr.ac.jbnu.se.tetris;

import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class BoardPlayer extends Board {

    Player player;
    
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
                isStarted = false;
                timer.stop();
            }
        } 
        else {
            oneLineDown();
        }
    }
}
