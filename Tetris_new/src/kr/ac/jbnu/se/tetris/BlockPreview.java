package kr.ac.jbnu.se.tetris;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BlockPreview extends JPanel {

    protected final int panelWidth = 60, panelHeight = 80, width = 3, height = 4;

    private Tetrominoes[][] nextPiece;

    private Shape piece;

    BlockPreview(Board board){
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        nextPiece = new Tetrominoes[height][width];
        initNextPiece();
    }
    
    private int squareWidth(){
        return (int) panelWidth / width;
    }
    private int squareHeight(){
        return (int) panelHeight / height;
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
            int y = 2 - piece.y(i);
            nextPiece[y][x] = piece.getShape();
        }
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 2; j++){
                Tetrominoes temp = nextPiece[j][i];
                nextPiece[j][i] = nextPiece[3 - j][i];
                nextPiece[3 - j][i] = temp;
            }
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
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
        BufferedImage blockImage = getImage(getImageFile(shape));
        
        int imageSize = squareWidth(); // 이미지 크기를 블록 크기에 맞게 조정합니다.

        g.drawImage(blockImage, x, y, imageSize, imageSize, null);
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
}
