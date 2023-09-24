package kr.ac.jbnu.se.tetris;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

public class BoardAI extends Board {
    
    int[][] boardWeight;

    TetrisAI computer;

    String bestRoute = "";

    int index = 0;

    public BoardAI(Tetris parent) throws CloneNotSupportedException {
        super(parent);
        //TODO Auto-generated constructor stub

        boardWeight = new int[BoardHeight + 2][BoardWidth + 2];
        initBoardWeight();

        computer = new TetrisAI(this);

        bestRoute = computer.findBestRoute();

        timer = new Timer(100, this);
        
        start();
    }

    private void initBoardWeight() {
        int weight = 23;
        for(int i = 0; i < BoardHeight + 2; i++){
            for(int j = 0; j < BoardWidth + 2; j++){
                boardWeight[i][j] = weight;
            }
            System.out.println();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {

            isFallingFinished = false;
            index = 0;
            if (!newPiece()) {
                isStarted = false;
                timer.stop();
            }

            try {
                bestRoute = computer.findBestRoute();
            } catch (CloneNotSupportedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        } 
        else {
            moveToBestRoute(index++);
        }
    }

    private void moveToBestRoute(int i){
        if(i >= bestRoute.length()){
            dropDown();
            return;
        }

        if(bestRoute.charAt(i) == '0' && tryMove(curPiece, curPiece.curX() - 1, curPiece.curY()))
            move(curPiece, curPiece.curX() - 1, curPiece.curY());
        else if(bestRoute.charAt(i) == '1' && tryMove(curPiece, curPiece.curX() + 1, curPiece.curY()))
            move(curPiece, curPiece.curX() + 1, curPiece.curY());
        else if(bestRoute.charAt(i) == '2' && tryMove(curPiece, curPiece.curX(), curPiece.curY() - 1))
            move(curPiece, curPiece.curX(), curPiece.curY() - 1);
        else if(bestRoute.charAt(i) == '3' && tryMove(curPiece.rotateRight(), curPiece.curX(), curPiece.curY()))
            move(curPiece.rotateRight(), curPiece.curX(), curPiece.curY());    
    }
}
