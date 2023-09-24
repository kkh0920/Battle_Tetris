package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    final int BoardWidth = 10;
    final int BoardHeight = 22;

    final int PreferredSizeWidth = 200;
    final int PreferredSizeHeight = 400;

    boolean isFallingFinished = false;
    boolean isStarted = false;
    
    int numLinesRemoved = 0;

    Shape curPiece;
    JLabel statusbar;
    Tetrominoes[][] board;

    Timer timer;

    Board opponent;

    public Board(Tetris parent) {
        setPreferredSize(new Dimension(PreferredSizeWidth, PreferredSizeHeight));
        
        board = new Tetrominoes[BoardHeight][BoardWidth];
        statusbar = parent.getStatusBar();
        curPiece = new Shape();

        isFallingFinished = false;
        numLinesRemoved = 0;
        clearBoard();

        newPiece();
    }

    public void setOpponent(Board opponent){
        this.opponent = opponent;
    }

    public void start(){
        isStarted = true;
        timer.start();
    }

    private void clearBoard() { // 보드 클리어
        for (int i = 0; i < BoardHeight; ++i) { 
            for(int j = 0; j < BoardWidth; ++j) {
                board[i][j] = Tetrominoes.NoShape;
            }
        }
    }

    public boolean newPiece() { // 새로운 떨어지는 블록 생성
        curPiece.setRandomShape();

        int initPosX = BoardWidth / 2;
        int initPosY = BoardHeight - 2 + curPiece.minY();

        if (!tryMove(curPiece, initPosX, initPosY))
            return false;

        move(curPiece, initPosX, initPosY);

        return true;
    }

    // -------------------------------- get 메소드 --------------------------------

    public Shape getCurPiece(){ // 현재 떨어지고 있는 도형
        return curPiece;
    }
    int squareWidth() { // 블록 한칸(1 x 1) 가로 길이
        return (int) getPreferredSize().width / BoardWidth;
    }
    int squareHeight() { // 블록 한칸(1 x 1) 세로 길이
        return (int) getPreferredSize().height / BoardHeight;
    }
    Tetrominoes shapeAt(int x, int y) { // (x, y)에 있는 블럭의 Tetrominoes 타입
        return board[y][x];
    }


    // -------------------------------- 일시 정시 시 ---------------------------------

    public void setTextPause() {
        statusbar.setText("paused");
        repaint();
    }
    public void setTextResume(){
        statusbar.setText(String.valueOf(numLinesRemoved));
        repaint();
    }


    // -------------------------------- 블록 하강 및 점수 획득 --------------------------------

    public void move(Shape newPiece, int newX, int newY){
        curPiece = newPiece;
        curPiece.moveTo(newX, newY);
        repaint();
    }
    public boolean tryMove(Shape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
                return false;
            if (shapeAt(x, y) != Tetrominoes.NoShape)
                return false;
        }
        return true;
    }
    public void oneLineDown() { // curPiece 한줄 드롭
        if (!tryMove(curPiece, curPiece.curX(), curPiece.curY() - 1))
            pieceDropped();	
        else
            move(curPiece, curPiece.curX(), curPiece.curY() - 1);
    }
    public void dropDown() { // curPiece 한번에 드롭
        int newY = curPiece.curY();
        while (newY > 0) {
            if (!tryMove(curPiece, curPiece.curX(), --newY))
                break;
            move(curPiece, curPiece.curX(), newY);
        }
        pieceDropped();
    }
    private void pieceDropped() { // 블록이 완전히 떨어지면, 해당 블록을 board에 그리는 식
        for (int i = 0; i < 4; ++i) {
            int x = curPiece.curX() + curPiece.x(i);
            int y = curPiece.curY() - curPiece.y(i);
            board[y][x] = curPiece.getShape(); 
        }
        removeFullLines();
    }
    private void removeFullLines() { // 한 줄 제거 가능 여부 탐색(점수 획득)
        int numFullLines = 0;

        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;
            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BoardHeight - 1; ++k) {
                    for (int j = 0; j < BoardWidth; ++j){
                        board[k][j] = shapeAt(j, k + 1);
                        if(k == BoardHeight - 2 && shapeAt(j, k + 1) != Tetrominoes.NoShape)
                            board[k + 1][j] = Tetrominoes.NoShape;
                    }
                }
            }
        }

        isFallingFinished = true;

        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            statusbar.setText(String.valueOf(numLinesRemoved));
            curPiece.setShape(Tetrominoes.NoShape);
            repaint();
        }
    }


    // -------------------------------- 블록 페인트 --------------------------------

    public void paint(Graphics g) {
        super.paint(g);

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

        // 배치된 블록 paint
        for (int i = 0; i < BoardHeight; ++i) {
            for (int j = 0; j < BoardWidth; ++j) {
                Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
                if(shape != Tetrominoes.NoShape)
                    drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), shape);
            }
        }
        
        // 떨어지는 블록 paint
        if (curPiece.getShape() != Tetrominoes.NoShape) {
            for (int i = 0; i < 4; ++i) {
                int x = curPiece.curX() + curPiece.x(i);
                int y = curPiece.curY() - curPiece.y(i);
                drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }
    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        Color colors[] = { new Color(255, 255, 255), new Color(204, 102, 102), 
                            new Color(102, 204, 102), new Color(102, 102, 204), 
                            new Color(204, 204, 102), new Color(204, 102, 204), 
                            new Color(102, 204, 204), new Color(218, 170, 0) };
        
        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}