package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class TetrisGameManager extends JFrame {
    
    // 메인 화면 클래스 추가

    // 게임 2개를 관리
    Tetris player1Panel;
    Tetris player2Panel;

    public TetrisGameManager(){
        settingFrame();
    }

    public void settingFrame(){
        setTitle("Tetris");
        setSize(500, 460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        setFocusable(true);

        // 코드 수정해야함 (playerNumber 수에 따라 Tetris 객체 생성)

        player1Panel = new Tetris();
        player2Panel = new Tetris();

        player1Panel.getBoard().setOtherBoard(player2Panel.getBoard());
        player2Panel.getBoard().setOtherBoard(player1Panel.getBoard());

        add(player1Panel, BorderLayout.WEST);
        add(player2Panel, BorderLayout.EAST);

        addKeyListener(new PlayerKeyListener());
    }
    
    public static void main(String[] args){
        TetrisGameManager gameManager = new TetrisGameManager();
        gameManager.setVisible(true);
    }


    // 키 입력 처리
    public class PlayerKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            // 코드 수정해야함 (playerNumber 수에 따라 생성)
            Board player1Board = player1Panel.getBoard();
            Board player2Board = player2Panel.getBoard();
            
            /*
             
             * 세가지 경우에서 키 입력을 제한

             1. 게임이 시작되지 않았을 때
             2. 떨어지고 있는 블록이 없을 때
             3. pause 상태일 때

             */

            if (!player1Board.isStarted && !player2Board.isStarted ||
                        player1Board.curPiece.getShape() == Tetrominoes.NoShape ||
                            player2Board.curPiece.getShape() == Tetrominoes.NoShape) {
                return;
            }

            int keycode = e.getKeyCode();

            if (keycode == 'p' || keycode == 'P') {
                player1Board.pause();
                player2Board.pause();
                return;
            }
            if (player1Board.isPaused || player2Board.isPaused)
                return;
        

            // player1 입력

            Shape p1CurPiece = player1Board.getCurPiece();
    
            if(keycode == KeyEvent.VK_LEFT){
                p1CurPiece.tryMove(player1Board, p1CurPiece.curX() - 1, p1CurPiece.curY());
            }
            if(keycode == KeyEvent.VK_RIGHT){
                p1CurPiece.tryMove(player1Board, p1CurPiece.curX() + 1, p1CurPiece.curY());
            }
            if(keycode == KeyEvent.VK_UP){
                Shape leftRotated = p1CurPiece.rotateLeft();
                leftRotated.tryMove(player1Board, p1CurPiece.curX(), p1CurPiece.curY());
            }
            if(keycode == KeyEvent.VK_DOWN){
                Shape rightRotated = p1CurPiece.rotateRight();
                rightRotated.tryMove(player1Board, p1CurPiece.curX(), p1CurPiece.curY());
            }
            if(keycode == KeyEvent.VK_SPACE){
                player1Board.dropDown();
            }
            if(keycode == 'l' || keycode == 'L'){
                player1Board.oneLineDown();
            }


            // player2 입력

            Shape p2CurPiece = player2Board.getCurPiece();

            if(keycode == 'a' || keycode == 'A'){
                p2CurPiece.tryMove(player2Board, p2CurPiece.curX() - 1, p2CurPiece.curY());
            }
            if(keycode == 'd' || keycode == 'D'){
                p2CurPiece.tryMove(player2Board, p2CurPiece.curX() + 1, p2CurPiece.curY());
            }     
            if(keycode == 'w' || keycode == 'W'){
                Shape leftRotated = p2CurPiece.rotateLeft();
                leftRotated.tryMove(player2Board, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if(keycode == 's' || keycode == 'S'){
                Shape rightRotated = p2CurPiece.rotateRight();
                rightRotated.tryMove(player2Board, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if(keycode == KeyEvent.VK_SHIFT){
                player2Board.dropDown();
            }
            if(keycode == 'z' || keycode == 'Z'){
                player2Board.oneLineDown();
            }
        }
    }
}
