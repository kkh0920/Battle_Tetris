package kr.ac.jbnu.se.tetris;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

public class Board extends JPanel {

    protected Tetris parentTetris; 

    protected boolean isFallingFinished;
    protected boolean isStarted = false;

    protected boolean isBlockOvered = false;

    protected final int BoardWidth = 10;
    protected final int BoardHeight = 22;

    protected final int PanelWidth = 220;
    protected final int PanelHeight = 440;

    // 공격 데미지 및 hp 회복량
    protected final int HealthRecover = 9;
    protected final int AttackDamage = 15;

    // 점수
    protected int numLinesRemoved;
    
    // 타이머
    protected Timer timer;

    // 보드판
    protected Tetrominoes[][] gridBoard;

    // 현재 떨어지는 도형 / 다음에 떨어질 도형
    protected Shape curPiece;
    protected Shape nextPiece;

    // 상대편 보드
    protected Board opponent;

    public Board(Tetris parent) {
        setPreferredSize(new Dimension(PanelWidth, PanelHeight));

        this.parentTetris = parent;

        gridBoard = new Tetrominoes[BoardHeight][BoardWidth];

        curPiece = new Shape(); // 생성자에서 객체 생성 (합성 관계)
        nextPiece = new Shape();

        isFallingFinished = true;
        numLinesRemoved = 0;
        clearBoard();

        nextPiece.setRandomShape();
    }

    public void setOpponent(Board opponent) {
        this.opponent = opponent;
    }

    public void start(){
        isStarted = true;
        timer.start();
    }

    public void gameOver() {
        Board player = this instanceof BoardPlayer ? this : opponent;

        TetrisGameManager manager = player.parentTetris.gameManager();

        if(manager.opponentIsComputer()) { // AI 대전이면서, 플레이어의 점수만 갱신
            MaxScorePanel maxScorePanel = manager.getMaxScorePanel();

            int prevMaxScore = maxScorePanel.getMaxScore();
            
            if(player.numLinesRemoved > prevMaxScore)
                maxScorePanel.fileWriter(player.numLinesRemoved);
        }

        opponent.isStarted = false;
        opponent.timer.stop();

        isStarted = false;
        timer.stop();

        manager.gameOverDialog().setVisible(true);
    }

    private void clearBoard() { // 보드 클리어
        for (int i = 0; i < BoardHeight; ++i) {
            for(int j = 0; j < BoardWidth; ++j) {
                gridBoard[i][j] = Tetrominoes.NoShape;
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
        parentTetris.getBlockPreview().setNextPiece(nextPiece);

        return true;
    }

    public void bombBlock() { // 폭탄 범위 내 블록 제거
        int x = curPiece.curX();
        int y = curPiece.curY();

        for(int i = y + 1; i >= y - 1; i--){
            for(int j = x - 1; j <= x + 1; j++){
                if(i < 0 || i >= BoardHeight || j < 0 || j >= BoardWidth)
                    continue;
                if(gridBoard[i][j] != Tetrominoes.NoShape)
                    gridBoard[i][j] = Tetrominoes.NoShape;
            }
        }
    }

    // -------------------------------- get 메소드 --------------------------------

    public int panelWidth(){
        return PanelWidth;
    }
    public int panelHeight(){
        return PanelHeight;
    }

    public int width(){
        return BoardWidth;
    }
    public int height(){
        return BoardHeight;
    }

    public Timer getTimer(){
        return timer;
    }

    public boolean isStarted(){
        return isStarted;
    }

    public Shape getNextPiece() { // 다음에 떨어질 도형
        return nextPiece;
    }
    public Shape getCurPiece() { // 현재 떨어지고 있는 도형
        return curPiece;
    }
    
    private int squareWidth() { // 블록 한칸(1 x 1) 가로 길이
        return getPreferredSize().width / BoardWidth;
    }
    private int squareHeight() { // 블록 한칸(1 x 1) 세로 길이
        return getPreferredSize().height / BoardHeight;
    }
    
    private Tetrominoes shapeAt(int x, int y) { // (x, y)에 있는 블럭의 Tetrominoes 타입
        return gridBoard[y][x];
    }

    // -------------------------------- 블록 이동, 점수 획득, 상대방 공격 --------------------------------

    public void move(Shape piece, int newX, int newY) { // 실제로 이동
        if(!tryMove(piece, newX, newY))
            return;
        curPiece = piece;
        curPiece.moveTo(newX, newY);
        repaint();
    }
    public boolean tryMove(Shape piece, int newX, int newY) { // 이동 가능 여부 체크
        for (int i = 0; i < 4; ++i) {
            int x = newX + piece.x(i);
            int y = newY - piece.y(i);
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
            gridBoard[y][x] = curPiece.getShape();
        }

        if(curPiece.getShape() == Tetrominoes.BombBlock) {
            bombBlock();
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
                        gridBoard[k][j] = shapeAt(j, k + 1);
                        if(k == BoardHeight - 2 && shapeAt(j, k + 1) != Tetrominoes.NoShape)
                            gridBoard[k + 1][j] = Tetrominoes.NoShape;
                    }
                }
            }
        }

        isFallingFinished = true;
        
        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            
            parentTetris.getStatusBar().setText(String.valueOf(numLinesRemoved)); // 0. 점수 갱신
            
            recoverHp(numFullLines); // 1. 내 hp 회복
            int remainCount = decreaseOtherHp(numFullLines); // 2. 상대 hp 감소
            stackLinesToOpponent(remainCount); // 3. 상대 보드에 장애물 생성
        }

        repaint();

        if(isBlockOvered)
            gameOver();
    }

    private void stackLinesToOpponent(int attackCount) { // 상대 보드에 장애물 블록 생성
        if(attackCount == 0)
            return;

        Shape opponentPiece = opponent.curPiece;
        for(int i = 0; i < attackCount; i++){
            opponent.move(opponentPiece, opponentPiece.curX(), opponentPiece.curY() + 1);
        }
    
        for (int i = BoardHeight - 1; i >= 0; i--) {
            for (int j = 0; j < BoardWidth; j++) {
                if(opponent.gridBoard[i][j] == Tetrominoes.NoShape)
                    continue;

                if (i + attackCount >= BoardHeight) {
                    isBlockOvered = true;
                } else {
                    opponent.gridBoard[i + attackCount][j] = opponent.gridBoard[i][j];
                }
                opponent.gridBoard[i][j] = Tetrominoes.NoShape;
            }
        }

        removeOneBlock(attackCount);
    }
    private void removeOneBlock(int linesHeight) {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int x = r.nextInt(BoardWidth);

        for (int i = 0; i < linesHeight; i++) {
            for (int j = 0; j < BoardWidth; j++) {
                opponent.gridBoard[i][j] = Tetrominoes.LockBlock;
            }
            opponent.gridBoard[i][x] = Tetrominoes.NoShape;
        }
    }

    private void recoverHp(int count){
        JProgressBar curHp = parentTetris.getHealthBar();
        int increasedHp = curHp.getValue() + count * HealthRecover;
        if(increasedHp > 100)
            increasedHp = 100;
        curHp.setValue(increasedHp);
    }
    private int decreaseOtherHp(int count) {
        int attackCount = 0;

        JProgressBar otherHp = opponent.parentTetris.getHealthBar();
        int decreasedHp = otherHp.getValue() - count * AttackDamage;
        if(decreasedHp < 0){
            attackCount += (-1 * decreasedHp) / AttackDamage;
            decreasedHp = 0;
        }
        otherHp.setValue(decreasedHp);

        return attackCount;
    }

    // -------------------------------- 블록 페인트 --------------------------------
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

        paintBoardPiece(g, boardTop); // 보드에 위치한 블록

        paintDroppingPiece(g, boardTop); // 현재 떨어지고 있는 블록

        paintShadow(g, boardTop); // 블록이 놓여질 위치
        
    }

    private void paintBoardPiece(Graphics g, int boardTop) {
        for (int i = 0; i < BoardHeight; ++i) {
            for (int j = 0; j < BoardWidth; ++j) {
                Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
                if(shape != Tetrominoes.NoShape)
                    drawSquare(g, j * squareWidth(), boardTop + i * squareHeight(), shape);
            }
        }
    }
    private void paintDroppingPiece(Graphics g, int boardTop) {
        if (curPiece.getShape() == Tetrominoes.NoShape || isFallingFinished) 
            return;

        for (int i = 0; i < 4; ++i) {
            int x = curPiece.curX() + curPiece.x(i);
            int y = curPiece.curY() - curPiece.y(i);
            drawSquare(g, x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(), curPiece.getShape());
        }
    }
    private void paintShadow(Graphics g, int boardTop) {
        if(curPiece.getShape() == Tetrominoes.NoShape || isFallingFinished)
            return;

        int nX = curPiece.curX();
        int nY = curPiece.curY();
        while(nY >= 0){
            if(!tryMove(curPiece, nX, nY))
                break;
            nY--;
        }
        nY++;

        if(curPiece.getShape() == Tetrominoes.BombBlock) {
            paintBombShadow(g, boardTop, nX, nY);
        } else {
            paintPieceShadow(g, boardTop, nX, nY);
        }
    }
    private void paintBombShadow(Graphics g, int boardTop, int nX, int nY){
        for(int i = nY - 1; i <= nY + 1; i++){
            for(int j = nX - 1; j <= nX + 1; j++){
                if(i < 0 || i >= BoardHeight || j < 0 || j >= BoardWidth)
                    continue;
                drawSquare(g, j * squareWidth(), boardTop + (BoardHeight - i - 1) * squareHeight(), Tetrominoes.NoShape);
            }
        }
    }
    private void paintPieceShadow(Graphics g, int boardTop, int nX, int nY){
        for(int i = 0; i < 4; i++){
            int x = nX + curPiece.x(i);
            int y = nY - curPiece.y(i);
            drawSquare(g, x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(), Tetrominoes.NoShape);
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        BlockImage block = new BlockImage(shape);

        int imageSize = squareWidth(); // 이미지 크기를 블록 크기에 맞게 조정합니다.
        
        if(shape == Tetrominoes.BombBlock) {
            imageSize = 30;
            g.drawImage(block.getImage(), x - 2, y - 8, imageSize, imageSize, null);
        }
        else if(shape == Tetrominoes.NoShape){
            Graphics2D g2d = (Graphics2D) g;
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaComposite);
            g2d.drawImage(block.getImage(), x, y - 2, imageSize, imageSize, null);
            g2d.setComposite(AlphaComposite.SrcOver);
        }
        else
            g.drawImage(block.getImage(), x, y - 2, imageSize, imageSize, null);
    }
}
