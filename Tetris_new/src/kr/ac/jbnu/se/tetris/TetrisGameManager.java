package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class TetrisGameManager extends JFrame {
    
    static public int p1_up = 38, p1_down = 40, p1_left = 39, p1_right = 37,
                        p2_up = 119, p2_down = 115, p2_left = 97, p2_right = 100,

                        p2_up_upper = p2_up - 32, p2_down_upper = p2_down - 32, 
                        p2_left_upper = p2_left - 32, p2_right_upper = p2_right-32,
                        
                        p2_dropDown = KeyEvent.VK_SHIFT;

    Tetris player1Panel;
    Tetris player2Panel;

    boolean isPaused = false;

    public TetrisGameManager(boolean isComputer) throws CloneNotSupportedException{
        settingFrame();

        settingOpponent(isComputer);

        addKeyListener(new PlayerKeyListener());
    }

    public void settingFrame() {
        setTitle("Tetris");
        setSize(500, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
    }

    public void settingOpponent(boolean isComputer) throws CloneNotSupportedException {
        player1Panel = new Tetris(false);
        player2Panel = new Tetris(isComputer); // 생성자에서 객체를 생성하면 합성 관계

        Board p1Board = player1Panel.getBoard();
        Board p2Board = player2Panel.getBoard(); 

        p1Board.setOpponent(p2Board);
        p2Board.setOpponent(p1Board);

        add(player1Panel, BorderLayout.WEST);
        add(player2Panel, BorderLayout.EAST);
    }

    public void pause() {
        Board p1Board = player1Panel.getBoard();
        Board p2Board = player2Panel.getBoard();
        
        if (!p1Board.isStarted || !p2Board.isStarted)
            return;

        isPaused = !isPaused;
        if (isPaused) {
            p1Board.timer.stop();
            p2Board.timer.stop();

            p1Board.setTextPause();
            p2Board.setTextPause();
        } else {
            p1Board.start();
            p2Board.start();

            p1Board.setTextResume();
            p2Board.setTextResume();
        }
    }

    public class PlayerKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            Board p1Board = player1Panel.getBoard();
            Board p2Board = player2Panel.getBoard();
            
            /*
            
            세가지 경우에서 키 입력을 제한

            1. 게임이 시작되지 않았을 때
            2. 떨어지고 있는 블록이 없을 때
            3. pause 상태일 때

            */

            if (!p1Board.isStarted || !p2Board.isStarted || 
                    p1Board.curPiece.getShape() == Tetrominoes.NoShape ||
                                p2Board.curPiece.getShape() == Tetrominoes.NoShape) {
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

            Shape p1CurPiece = p1Board.getCurPiece();

            if (keycode == KeyEvent.VK_LEFT) {
                if (p1Board.tryMove(p1CurPiece, p1CurPiece.curX() - 1, p1CurPiece.curY()))
                    p1Board.move(p1CurPiece, p1CurPiece.curX() - 1, p1CurPiece.curY());

            }
            if (keycode == KeyEvent.VK_RIGHT) {
                if (p1Board.tryMove(p1CurPiece, p1CurPiece.curX() + 1, p1CurPiece.curY()))
                    p1Board.move(p1CurPiece, p1CurPiece.curX() + 1, p1CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_UP) {
                Shape leftRotated = p1CurPiece.rotateLeft();
                if (p1Board.tryMove(leftRotated, p1CurPiece.curX(), p1CurPiece.curY()))
                    p1Board.move(leftRotated, p1CurPiece.curX(), p1CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_DOWN) {
                Shape rightRotated = p1CurPiece.rotateRight();
                if (p1Board.tryMove(rightRotated, p1CurPiece.curX(), p1CurPiece.curY()))
                    p1Board.move(rightRotated, p1CurPiece.curX(), p1CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_SPACE) {
                p1Board.dropDown();
            }
            if (keycode == 25) {
                p1Board.oneLineDown();
            }

            // player2 키 입력
            
            if(player2Panel.isComputer())
                return;

            Shape p2CurPiece = p2Board.getCurPiece();

            if (keycode == 'a' || keycode == 'A') {
                if (p2Board.tryMove(p2CurPiece, p2CurPiece.curX() - 1, p2CurPiece.curY()))
                    p2Board.move(p2CurPiece, p2CurPiece.curX() - 1, p2CurPiece.curY());
            }
            if (keycode == 'd' || keycode == 'D') {
                if (p2Board.tryMove(p2CurPiece, p2CurPiece.curX() + 1, p2CurPiece.curY()))
                    p2Board.move(p2CurPiece, p2CurPiece.curX() + 1, p2CurPiece.curY());
            }     
            if (keycode == 'w' || keycode == 'W') {
                Shape leftRotated = p2CurPiece.rotateLeft();
                if (p2Board.tryMove(leftRotated, p2CurPiece.curX(), p2CurPiece.curY()))
                    p2Board.move(leftRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == 's' || keycode == 'S') {
                Shape rightRotated = p2CurPiece.rotateRight();
                if (p2Board.tryMove(rightRotated, p2CurPiece.curX(), p2CurPiece.curY()))
                    p2Board.move(rightRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == KeyEvent.VK_SHIFT) {
                p2Board.dropDown();
            }
            if (keycode == KeyEvent.VK_CONTROL) {
                p2Board.oneLineDown();
            }
        }
    }

}
