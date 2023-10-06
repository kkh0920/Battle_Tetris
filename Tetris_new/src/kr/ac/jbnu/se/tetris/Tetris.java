package kr.ac.jbnu.se.tetris;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class Tetris extends JPanel {

    private TetrisGameManager parent;

    final int Frame_X = 330, Frame_Y = 460, Status_X = 60, Status_Y = 30;
    
    // 체력바
    private JProgressBar healthBar;

    // 보드
    private Board board;

    // Next 블록 프리뷰
    private BlockPreview blockPreview;

    // 점수 패널
    private JPanel statusPanel;
    private JLabel statusbar;

    public Tetris(TetrisGameManager parent, boolean isComputer) {
        this.parent = parent;    
        setTetrisLayout(isComputer);
    }

    // -------------------------------------- get 메소드 --------------------------------------

    public TetrisGameManager gameManager(){
        return parent;
    }
    public JLabel getStatusBar() {
        return statusbar;
    }
    public Board getBoard() {
        return board;
    }
    public JProgressBar getHealthBar() {
        return healthBar;
    }
    public BlockPreview getBlockPreview(){
        return blockPreview;
    }
    public int frameX(){
        return Frame_X;
    }
    public int frameY(){
        return Frame_Y;
    }

    // -------------------------------------- 컴포넌트 레이아웃 설정 --------------------------------------

    private void setTetrisLayout(boolean isComputer) {
        setPreferredSize(new Dimension(Frame_X, Frame_Y));
        setBackground(new Color(210, 210, 210));
        setLayout(null);

        board = isComputer ? new BoardAI(this) : new BoardPlayer(this); // 1. 보드판
        blockPreview =  new BlockPreview(board); // 2. Next 블록 프리뷰
        setHealthBar(); // 3. 체력바
        setScorePanel(); // 4. 스코어 패널

        setLayoutLocation(); // 각 컴포넌트 위치 조정

        addComponent(); // 각 컴포넌트 배치
    }

    private void setHealthBar() {
        healthBar = new JProgressBar(0, 100); // 최소 값과 최대 값을 설정
        healthBar.setValue(100); // 초기 체력 설정
        healthBar.setStringPainted(true); // 백분율 표시 활성화
        healthBar.setForeground(new Color(220, 120, 120)); // 체력 바의 색상 변경
        healthBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionForeground() {
                return Color.WHITE; // 글자 색 변경
            }
        });
    }
    
    private void setScorePanel(){
        statusbar = new JLabel("0");
        statusPanel = new JPanel();
        statusPanel.add(statusbar, BorderLayout.CENTER);
    }
    
    private void setLayoutLocation() {
        healthBar.setBounds(20, 15, board.panelWidth(), 15);
        board.setBounds(20, 40, board.panelWidth(), board.panelHeight());
        blockPreview.setBounds(35 + board.panelWidth(), 40, blockPreview.panelWidth(), blockPreview.panelHeight());
        statusPanel.setBounds(35 + board.panelWidth(), 40 + board.panelHeight() - Status_Y, Status_X, Status_Y);
    }

    private void addComponent(){
        add(board);
        add(blockPreview);
        add(healthBar);
        add(statusPanel);
    }
}

