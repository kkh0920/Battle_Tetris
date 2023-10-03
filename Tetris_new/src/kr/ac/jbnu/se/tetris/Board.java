package kr.ac.jbnu.se.tetris;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    protected final int BoardWidth = 10;
    protected final int BoardHeight = 22;

    protected final int PreferredSizeWidth = 200;
    protected final int PreferredSizeHeight = 400;

    protected Tetris parent;

    protected Tetrominoes[][] board;

    protected boolean isFallingFinished;
    protected boolean isStarted = false;

    protected Shape curPiece, nextPiece; 

    protected Timer timer;

    protected Board opponent;

    private int numLinesRemoved = 0;

    private BlockPreview blockPreview;

    public Board(Tetris parent) {
        setPreferredSize(new Dimension(PreferredSizeWidth, PreferredSizeHeight));
        
        this.parent = parent;

        board = new Tetrominoes[BoardHeight][BoardWidth];
        
        curPiece = new Shape(); // 생성자에서 객체 생성 (합성 관계)
        nextPiece = new Shape();

        isFallingFinished = true;
        numLinesRemoved = 0;
        clearBoard();

        blockPreview = new BlockPreview(this);
    
        nextPiece.setRandomShape();
    }

    public void setOpponent(Board opponent){
        this.opponent = opponent;
    }

    public void start(){
        isStarted = true;
        timer.start();
    }

    public void gameOver(){
        opponent.isStarted = false;
        opponent.timer.stop();

        isStarted = false;
        timer.stop();

        parent.gameManager().gameOverDialog().setVisible(true);
    }

    private void clearBoard() { // 보드 클리어
        for (int i = 0; i < BoardHeight; ++i) { 
            for(int j = 0; j < BoardWidth; ++j) {
                board[i][j] = Tetrominoes.NoShape;
            }
        }
    }

    public boolean newPiece() { // 새로운 떨어지는 블록 생성
        curPiece = nextPiece.copy();
    
        int initPosX = BoardWidth / 2;
        int initPosY = BoardHeight - 2 + curPiece.minY();

        if (!tryMove(curPiece, initPosX, initPosY))
            return false;

        move(curPiece, initPosX, initPosY);

        nextPiece.setRandomShape();
        blockPreview.setNextPiece(nextPiece);

        return true;
    }

    // -------------------------------- get 메소드 --------------------------------

    public BlockPreview getBlockPreview(){
        return blockPreview;
    }
    public Shape getNextPiece(){
        return nextPiece;
    }
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
        curPiece.setShape(Tetrominoes.NoShape);
        
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

        boolean isBlockOvered = false;

        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            parent.getStatusBar().setText(String.valueOf(numLinesRemoved));
            isBlockOvered = attackOpponent(numFullLines);
        }
        repaint();

        if(isBlockOvered)
            gameOver();
    }

    private boolean attackOpponent(int attackCount){
        boolean isBlockOvered = false;
        
        for (int i = BoardHeight - 1; i >= 0; i--) {
            for (int j = 0; j < BoardWidth; j++) {
                if(opponent.board[i][j] == Tetrominoes.NoShape)
                    continue;

                if (i + attackCount >= BoardHeight) {
                    isBlockOvered = true;
                } else {
                    opponent.board[i + attackCount][j] = opponent.board[i][j];
                }
                opponent.board[i][j] = Tetrominoes.NoShape;
            }
        }  

        Shape opponentPiece = opponent.curPiece;
        for(int i = 0; i < attackCount; i++){
            if(opponent.tryMove(opponentPiece, opponentPiece.curX(), opponentPiece.curY() + 1))
                opponent.move(opponentPiece, opponentPiece.curX(), opponentPiece.curY() + 1);
        }
        
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int x = r.nextInt(BoardWidth);

        for (int i = 0; i < attackCount; i++) {
            for (int j = 0; j < BoardWidth; j++) {
                opponent.board[i][j] = Tetrominoes.LockBlock;
            }
            opponent.board[i][x] = Tetrominoes.NoShape;
        }
        

        return isBlockOvered;
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
        
        // 블록이 떨어질 위치 표시
        if(curPiece.getShape() == Tetrominoes.NoShape)
            return;

        int nX = curPiece.curX();
        int nY = curPiece.curY();
        while(nY >= 0){
            if(!tryMove(curPiece, nX, nY))
                break;
            nY--;
        }
        nY++;
        for(int i = 0; i < 4; i++){
            int x = nX + curPiece.x(i);
            int y = nY - curPiece.y(i);
            drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
                    Tetrominoes.NoShape);
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        BufferedImage blockImage = getImage(getImageFile(shape));

        int imageSize = squareWidth(); // 이미지 크기를 블록 크기에 맞게 조정합니다.
        
        if(shape == Tetrominoes.NoShape){
            Graphics2D g2d = (Graphics2D) g;
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaComposite);
            g2d.drawImage(blockImage, x, y - 2, imageSize, imageSize, null);
            g2d.setComposite(AlphaComposite.SrcOver);
        }
        else
            g.drawImage(blockImage, x, y - 2, imageSize, imageSize, null);
    }

    public static BufferedImage getImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 예외 발생 시 null 반환
    }

    public static String getImageFile(Tetrominoes shape) {
        String imgPath = "";
        switch (shape) {
            case NoShape:
                imgPath = "image/blocks/Block0.png";
                break;
            case ZShape:
                imgPath = "image/blocks/Block1.png";
                break;
            case SShape:
                imgPath = "image/blocks/Block2.png";
                break;
            case LineShape:
                imgPath = "image/blocks/Block3.png";
                break;
            case TShape:
                imgPath = "image/blocks/Block4.png";
                break;
            case SquareShape:
                imgPath = "image/blocks/Block5.png";
                break;
            case LShape:
                imgPath = "image/blocks/Block6.png";
                break;
            case MirroredLShape:
                imgPath = "image/blocks/Block7.png";
                break;
            case LockBlock:
                imgPath = "image/blocks/lockBlock.png";
                break;
        }
        return imgPath;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}