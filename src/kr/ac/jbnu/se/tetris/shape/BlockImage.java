package kr.ac.jbnu.se.tetris.shape;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlockImage {

    private BufferedImage image;

    public BlockImage(Tetrominoes shape) {
        image = getImage(getImageFile(shape));
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 예외 발생 시 null 반환
    }

    public String getImageFile(Tetrominoes shape) {
        String imgPath = "";
        switch (shape) {
            case NO_SHAPE:
                imgPath = "image/blocks/Block0.png";
                break;
            case Z_SHAPE:
                imgPath = "image/blocks/Block1.png";
                break;
            case S_SHAPE:
                imgPath = "image/blocks/Block2.png";
                break;
            case LINE_SHAPE:
                imgPath = "image/blocks/Block3.png";
                break;
            case T_SHAPE:
                imgPath = "image/blocks/Block4.png";
                break;
            case SQUARE_SHAPE:
                imgPath = "image/blocks/Block5.png";
                break;
            case L_SHAPE:
                imgPath = "image/blocks/Block6.png";
                break;
            case MIRRORED_L_SHAPE:
                imgPath = "image/blocks/Block7.png";
                break;
            case BOMB_BLOCK:
                imgPath = "image/blocks/BombIcon.png";
                break;
            case LOCK_BLOCK:
                imgPath = "image/blocks/lockBlock.png";
                break;
        }
        return imgPath;
    }
}
