package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

// 현재는 실행 즉시 게임이 진행되는 방식.

// 메인 화면 클래스를 만들고, 버튼 클릭에 따라 Tetris 객체를 생성하고 start() 함수 실행 여부를 결정 할 예정임

public class TetrisGameManager extends JFrame implements ActionListener{

    Tetris player1Panel;
    Tetris player2Panel;

    Board p1Board;
    Board p2Board;

    boolean isStarted = false;
	boolean isPaused = false;

    Timer timer;

    public TetrisGameManager(){
        settingFrame();

        addKeyListener(new PlayerKeyListener());

        timer = new Timer(400, this);
        
        start();
    }
    public void settingFrame(){
        setTitle("Tetris");
        setSize(500, 460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        setFocusable(true);

        player1Panel = new Tetris();
        player2Panel = new Tetris();

        p1Board = player1Panel.getBoard();
        p2Board = player2Panel.getBoard(); 

        add(player1Panel, BorderLayout.WEST);
        add(player2Panel, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (p1Board.isFallingFinished) {
			p1Board.isFallingFinished = false;
			if (!p1Board.newPiece()) {
                gameComplete();
                player1Panel.getStatusBar().setText("Lose..");
                player2Panel.getStatusBar().setText("Win!!");
            }
		} 
        else {
			p1Board.oneLineDown();
        }

        if (p2Board.isFallingFinished) {
            p2Board.isFallingFinished = false;
            if (!p2Board.newPiece()) {
                gameComplete();
                player1Panel.getStatusBar().setText("Win!!");
                player2Panel.getStatusBar().setText("Lose..");
            }
        } 
        else {
            p2Board.oneLineDown();
        }
    }

    public void gameComplete(){ // 누구 한명이 죽었을 때 실행
        timer.stop();
        isStarted = false;
        // p1Board.curPiece.setShape(Tetrominoes.NoShape);
        // p2Board.curPiece.setShape(Tetrominoes.NoShape);
    }

    public void start() {
		if (isPaused)
			return;
		isStarted = true;
		timer.start();
	}
    public void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			timer.stop();
			p1Board.setTextPause();
            p2Board.setTextPause();
		} else {
			timer.start();
			p1Board.setTextResume();
            p2Board.setTextResume();
        }
	}

    public static void main(String[] args){
        TetrisGameManager gameManager = new TetrisGameManager();
        gameManager.setVisible(true);
    }

    public class PlayerKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            Board player1Board = player1Panel.getBoard();
            Board player2Board = player2Panel.getBoard();
            
            /*
             
             세가지 경우에서 키 입력을 제한

             1. 게임이 시작되지 않았을 때
             2. 떨어지고 있는 블록이 없을 때
             3. pause 상태일 때

             */

            if (!isStarted || player1Board.curPiece.getShape() == Tetrominoes.NoShape ||
                                player2Board.curPiece.getShape() == Tetrominoes.NoShape) {
                return;
            }

            int keycode = e.getKeyCode();

            if (keycode == KeyEvent.VK_ESCAPE) {
                pause();
                return;
            }
            if (isPaused)
                return;

            // player1 키 입력

            Shape p1CurPiece = player1Board.getCurPiece();
    
            if (keycode == KeyEvent.VK_LEFT) {
                if (player1Board.tryMove(p1CurPiece, p1CurPiece.curX() - 1, p1CurPiece.curY()))
                    player1Board.move(p1CurPiece, p1CurPiece.curX() - 1, p1CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_RIGHT) {
                if (player1Board.tryMove(p1CurPiece, p1CurPiece.curX() + 1, p1CurPiece.curY()))
                    player1Board.move(p1CurPiece, p1CurPiece.curX() + 1, p1CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_UP) {
                Shape leftRotated = p1CurPiece.rotateLeft();
                if (player1Board.tryMove(leftRotated, p1CurPiece.curX(), p1CurPiece.curY()))
                    player1Board.move(leftRotated, p1CurPiece.curX(), p1CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_DOWN) {
                Shape rightRotated = p1CurPiece.rotateRight();
                if (player1Board.tryMove(rightRotated, p1CurPiece.curX(), p1CurPiece.curY()))
                    player1Board.move(rightRotated, p1CurPiece.curX(), p1CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_SPACE) {
                player1Board.dropDown();
            }
            if (keycode == 'l' || keycode == 'L') {
                player1Board.oneLineDown();
            }

            // player2 키 입력

            Shape p2CurPiece = player2Board.getCurPiece();

            if (keycode == 'a' || keycode == 'A') {
                if (player2Board.tryMove(p2CurPiece, p2CurPiece.curX() - 1, p2CurPiece.curY()))
                    player2Board.move(p2CurPiece, p2CurPiece.curX() - 1, p2CurPiece.curY());
            }
            if (keycode == 'd' || keycode == 'D') {
                if (player2Board.tryMove(p2CurPiece, p2CurPiece.curX() + 1, p2CurPiece.curY()))
                    player2Board.move(p2CurPiece, p2CurPiece.curX() + 1, p2CurPiece.curY());
            }     
            if (keycode == 'w' || keycode == 'W') {
                Shape leftRotated = p2CurPiece.rotateLeft();
                if (player2Board.tryMove(leftRotated, p2CurPiece.curX(), p2CurPiece.curY()))
                    player2Board.move(leftRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == 's' || keycode == 'S') {
                Shape rightRotated = p2CurPiece.rotateRight();
                if (player2Board.tryMove(rightRotated, p2CurPiece.curX(), p2CurPiece.curY()))
                    player2Board.move(rightRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_SHIFT) {
                player2Board.dropDown();
            }
            if (keycode == 'z' || keycode == 'Z') {
                player2Board.oneLineDown();
            }
        }
    }

}
