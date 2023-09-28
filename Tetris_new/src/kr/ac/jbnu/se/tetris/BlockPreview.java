package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
}
