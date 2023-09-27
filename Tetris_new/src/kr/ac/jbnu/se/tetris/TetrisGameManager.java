package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

public class TetrisGameManager extends JFrame {
    
    public static int p2_up = 'w', p2_down = 's', p2_left = 'a', p2_right = 'd',
                        p2_up_upper = 'W', p2_down_upper = 'S', p2_left_upper = 'A', p2_right_upper = 'D',
                        p2_dropDown = KeyEvent.VK_SHIFT;

    private Tetris player1Panel;
    private Tetris player2Panel;

    private boolean isPaused = false;
    private boolean opponentIsComputer;

    private JFrame pauseFrame, gameOverFrame;

    public TetrisGameManager(Select select) throws CloneNotSupportedException {
        setFrame();
        setPauseFrame(select);
        setGameOverFrame(select);
        addKeyListener(new PlayerKeyListener());
    }

    public void setFrame() {
        setTitle("Tetris");
        setSize(670, 460);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFocusable(true);
    }

    public JFrame gameOverFrame(){
        return gameOverFrame;
    }

    public void start(boolean isComputer) throws CloneNotSupportedException {
        player1Panel = new Tetris(this, false);
        player2Panel = new Tetris(this, isComputer); 

        opponentIsComputer = isComputer;

        // 생성자에서 객체를 생성하면 합성 관계
        // 매개변수 등을 통해 생성하면 집합 관계

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
    
        pauseFrame.setVisible(isPaused);

        if (isPaused) {
            p1Board.timer.stop();
            p2Board.timer.stop();
        } else {
            p1Board.start();
            p2Board.start();
        }
    }

    public void setGameOverFrame(Select select){
        TetrisGameManager g = this;

        JButton button2 = new JButton("재시작");
        JButton button3 = new JButton("메인화면");

        button2.setSize(100, 30);
        button3.setSize(100, 30);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    g.dispose();

                    gameOverFrame.setVisible(false);
                    
                    TetrisGameManager game = new TetrisGameManager(select);
                    game.start(opponentIsComputer);
                    game.setVisible(true);
                } catch (CloneNotSupportedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.dispose();

                gameOverFrame.setVisible(false);

                select.setVisible(true);
            }
        });

        gameOverFrame = new JFrame();
        gameOverFrame.setUndecorated(true);
        gameOverFrame.setSize(150, 60);
        gameOverFrame.setLocationRelativeTo(null); 

        gameOverFrame.add(button2, BorderLayout.NORTH);
        gameOverFrame.add(button3, BorderLayout.SOUTH);
    }


    public void setPauseFrame(Select select) {
        TetrisGameManager g = this;
        
        JButton button1 = new JButton("계속하기");
        JButton button2 = new JButton("재시작");
        JButton button3 = new JButton("메인화면");

        button1.setSize(100, 30);  
        button2.setSize(100, 30);
        button3.setSize(100, 30);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                pause();
            }
            
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    g.dispose();
                    
                    pauseFrame.setVisible(false);

                    TetrisGameManager game = new TetrisGameManager(select);
                    game.start(opponentIsComputer);
                    game.setVisible(true);
                } catch (CloneNotSupportedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.dispose();

                select.setVisible(true);
                pauseFrame.setVisible(false);
            }
        });

        pauseFrame = new JFrame();
        pauseFrame.setUndecorated(true);
        pauseFrame.setSize(150, 90);
        pauseFrame.setLocationRelativeTo(null); 

        pauseFrame.add(button1, BorderLayout.NORTH);
        pauseFrame.add(button2, BorderLayout.CENTER);
        pauseFrame.add(button3, BorderLayout.SOUTH);
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
                    p1Board.getCurPiece().getShape() == Tetrominoes.NoShape ||
                                p2Board.getCurPiece().getShape() == Tetrominoes.NoShape) {
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
            if (keycode == 'm' || keycode == 'M') {
                p1Board.oneLineDown();
            }

            // player2 키 입력
            
            if(opponentIsComputer)
                return;

            Shape p2CurPiece = p2Board.getCurPiece();

            if (keycode == p2_left || keycode == p2_left_upper) {
                if (p2Board.tryMove(p2CurPiece, p2CurPiece.curX() - 1, p2CurPiece.curY()))
                    p2Board.move(p2CurPiece, p2CurPiece.curX() - 1, p2CurPiece.curY());
            }
            if (keycode == p2_right || keycode == p2_right_upper) {
                if (p2Board.tryMove(p2CurPiece, p2CurPiece.curX() + 1, p2CurPiece.curY()))
                    p2Board.move(p2CurPiece, p2CurPiece.curX() + 1, p2CurPiece.curY());
            }     
            if (keycode == p2_up || keycode == p2_up_upper) {
                Shape leftRotated = p2CurPiece.rotateLeft();
                if (p2Board.tryMove(leftRotated, p2CurPiece.curX(), p2CurPiece.curY()))
                    p2Board.move(leftRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == p2_down || keycode == p2_down_upper) {
                Shape rightRotated = p2CurPiece.rotateRight();
                if (p2Board.tryMove(rightRotated, p2CurPiece.curX(), p2CurPiece.curY()))
                    p2Board.move(rightRotated, p2CurPiece.curX(), p2CurPiece.curY());
            }
            if (keycode == p2_dropDown) {
                p2Board.dropDown();
            }
            if (keycode == KeyEvent.VK_CONTROL) {
                p2Board.oneLineDown();
            }
        }
    }

}
