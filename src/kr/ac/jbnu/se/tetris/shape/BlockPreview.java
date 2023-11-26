package kr.ac.jbnu.se.tetris.shape;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BlockPreview extends JPanel {

    protected final int panelWidth = 60, panelHeight = 80, width = 3, height = 4;

    private Tetrominoes[][] nextPiece;

    private Shape piece;

    public BlockPreview() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        nextPiece = new Tetrominoes[height][width];
        initNextPiece();
    }
    
    public int panelWidth(){
        return panelWidth;
    }
    public int panelHeight(){
        return panelHeight;
    }

    private int squareWidth(){
        return panelWidth / width;
    }
    private int squareHeight(){
        return panelHeight / height;
    }

    private void initNextPiece(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                nextPiece[i][j] = Tetrominoes.NoShape;
            }
        }
    }

    public void setNextPiece(Shape piece){
        initNextPiece();
        this.piece = piece;
        for(int i = 0; i < 4; i++){
            int x = 1 + piece.x(i);
            int y = 1 + piece.y(i);
            nextPiece[y][x] = piece.getShape();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int boardTop = panelHeight - height * squareHeight();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(nextPiece[i][j] != Tetrominoes.NoShape){
                    drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), piece.getShape());
                }
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        BlockImage block = new BlockImage(shape);
        
        int imageSize = squareWidth(); // 이미지 크기를 블록 크기에 맞게 조정합니다.

        if(shape == Tetrominoes.BombBlock) {
            imageSize = 30;
            g.drawImage(block.getImage(), x - 2, y - 6, imageSize, imageSize, null);
        }
        else
            g.drawImage(block.getImage(), x, y, imageSize, imageSize, null);
    }
}
