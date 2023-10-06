package kr.ac.jbnu.se.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class BoardPlayer extends Board implements ActionListener {

    public BoardPlayer(Tetris parent) {
        super(parent);
        //TODO Auto-generated constructor stub 
        timer = new Timer(500, this);        
        start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {
            isFallingFinished = false;
            if (!newPiece())
                gameOver();
        } 
        else {
            oneLineDown();
        }
    }
}
