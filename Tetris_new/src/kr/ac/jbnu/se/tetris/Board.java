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

	Timer timer;
	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	int numLinesRemoved = 0;
	
	JLabel statusbar;
	Shape curPiece;
	Tetrominoes[] board;

	Board otherBoard;

	public Board(Tetris parent) {
		setPreferredSize(new Dimension(PreferredSizeWidth, PreferredSizeHeight));

		curPiece = new Shape();
		timer = new Timer(400, this);
		timer.start();

		statusbar = parent.getStatusBar();
		board = new Tetrominoes[BoardWidth * BoardHeight];

		clearBoard();
	}

	// Timer의 delay 마다 actionPerformed 수행(한 줄 씩 다운 || 새로운 블록 생성)
	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
			// 테트리스 ai : 이 시점에서 새로 생성된 newPiece에 대한 bfs 코드를 수행 (최대 가중치 값 탐색)

		} else {
			oneLineDown();
		}
	}

	public void setOtherBoard(Board otherBoard){
		this.otherBoard = otherBoard;
	}

	public Shape getCurPiece(){
		return curPiece;
	}

	// 아마도 board의 1 x 1	블록 비율? 
	// --> 블록의 비율을 유지하면서 창을 키우기 위해 preferredSize와 Board 모두 수정해야함
	int squareWidth() {
		return (int) getPreferredSize().width / BoardWidth;
	}
	int squareHeight() {
		return (int) getPreferredSize().height / BoardHeight;
	}

	// (x, y)에 있는 블럭의 Tetrominoes 타입
	Tetrominoes shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x];
	}

	// 새로운 떨어지는 블록 생성
	private void newPiece() {
		curPiece.setRandomShape();

		int initPosX = BoardWidth / 2 + 1;
		int initPosY = BoardHeight - 1 + curPiece.minY();

		if (!curPiece.tryMove(this, initPosX, initPosY)) {
			gameComplete();	
			if(otherBoard.isStarted){
				statusbar.setText("Lose...");

				otherBoard.gameComplete();
				otherBoard.statusbar.setText("Win!!");
			}
			else{
				statusbar.setText("Draw");
				otherBoard.statusbar.setText("Draw");
			}
		}
	}
	
	public void gameComplete(){
		curPiece.setShape(Tetrominoes.NoShape);
		timer.stop();
		isStarted = false;
	}


	public void start() {
		if (isPaused)
			return;

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();

		newPiece();
		timer.start();
	}
	public void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			timer.stop();
			statusbar.setText("paused");
		} else {
			timer.start();
			statusbar.setText(String.valueOf(numLinesRemoved));
		}
		repaint();
	}

	private void clearBoard() { // 보드 클리어
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	public void oneLineDown() { // curPiece 한줄 드롭
		if (!curPiece.tryMove(this, curPiece.curX(), curPiece.curY() - 1))
			pieceDropped();
	}

	public void dropDown() { // curPiece 한번에 드롭
		int newY = curPiece.curY();
		while (newY > 0) {
			if (!curPiece.tryMove(this, curPiece.curX(), newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	private void pieceDropped() { // 블록이 완전히 떨어지면, 해당 블록을 board에 그리는 식
		for (int i = 0; i < 4; ++i) {
			int x = curPiece.curX() + curPiece.x(i);
			int y = curPiece.curY() - curPiece.y(i);
			board[(y * BoardWidth) + x] = curPiece.getShape(); 
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	// 한 줄 제거 가능 여부 탐색(점수 획득)
	private void removeFullLines() {
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
					for (int j = 0; j < BoardWidth; ++j)
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
			statusbar.setText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		// 배치된 블록 paint
		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
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
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
			new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
			new Color(218, 170, 0) };
		
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