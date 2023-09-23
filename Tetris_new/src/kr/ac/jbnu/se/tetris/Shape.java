package kr.ac.jbnu.se.tetris;

import java.util.Random;

public class Shape {

	private Tetrominoes pieceShape; // 모양

	private int[][][] coordsTable; 
	private int coords[][]; // 모양의 좌표값

	private int curX = 0;
	private int curY = 0; // 현재 위치

	public Shape() {
		coords = new int[4][2];
		setShape(Tetrominoes.NoShape);
	}

	public void setShape(Tetrominoes shape) {	
			
		coordsTable = new int[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
										{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, 
										{ { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
										{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, 
										{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
										{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, 
										{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
										{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; ++j) {
				coords[i][j] = coordsTable[shape.ordinal()][i][j];
			}
		}
		
		pieceShape = shape;
	}
	public Tetrominoes getShape() {
		return pieceShape;
	}

	private void setX(int index, int x) {
		coords[index][0] = x;
	}
	private void setY(int index, int y) {
		coords[index][1] = y;
	}
	public int x(int index) {
		return coords[index][0];
	}
	public int y(int index) {
		return coords[index][1];
	}

	public void setRandomShape() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1; 
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}

	public int minX() {
		int m = coords[0][0];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][0]);
		}
		return m;
	}
	public int minY() {
		int m = coords[0][1];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	public int curX(){
		return curX;
	}
	public int curY(){
		return curY;
	}
	public void moveTo(int x, int y){ // setCurX, setCurY
		curX = x;
		curY = y;
	}

	public Shape rotateLeft() {
		if (pieceShape == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i) {
			result.setX(i, y(i));
			result.setY(i, -x(i));
		}

		return result;
	}
	public Shape rotateRight() {
		if (pieceShape == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i) {
			result.setX(i, -y(i));
			result.setY(i, x(i));
		}

		return result;
	}
}