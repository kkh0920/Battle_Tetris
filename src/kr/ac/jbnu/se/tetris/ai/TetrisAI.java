package kr.ac.jbnu.se.tetris.ai;

import java.util.LinkedList;
import java.util.Queue;

import kr.ac.jbnu.se.tetris.board.BoardAI;
import kr.ac.jbnu.se.tetris.shape.Shape;
import kr.ac.jbnu.se.tetris.shape.Tetrominoes;

public class TetrisAI {

    private BoardAI board;

    private Queue<Shape> shapeQueue;
    private Queue<String> routeQueue;

    private boolean[][][] visited;
    
    private Shape curPiece;

    // 0 : 왼쪽 / 1 : 오른쪽 / 2 : 아래 / 3 : right 회전
    private String bestRoute;

    private int maxWeight;
    
    private int[] dx = { -1, 1, 0, 0 };
    private int[] dy = { 0, 0, -1, 1 };
    
    public TetrisAI(BoardAI board) {
        this.board = board;
    }

    public String getBestRoute() {
        initFindRoute();

        while(!shapeQueue.isEmpty()) {
            try {
                Shape piece = (Shape) shapeQueue.poll().clone();

                int curX = piece.curX();
                int curY = piece.curY();

                String curRoute = routeQueue.poll();

                findRouteMove(piece, curRoute, curX, curY);
                fineRouteRotate(piece, curRoute, curX, curY);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            } 
        }
        
        return bestRoute;
    }

    private void initFindRoute() {
        shapeQueue = new LinkedList<>();
        routeQueue = new LinkedList<>();
        
        maxWeight = -10;
        bestRoute = "";
        visited = new boolean[board.height()][board.width()][4];

        try {
            curPiece = (Shape) board.getCurPiece().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 4; i++){
            visited[curPiece.curY()][curPiece.curX()][curPiece.getRotateIndex()] = true;

            routeQueue.add(bestRoute);
            bestRoute += "3";
            
            shapeQueue.add(curPiece);
            curPiece = curPiece.rotateRight();
        }
    }

    private void findRouteMove(Shape piece, String curRoute, int curX, int curY) {
        for(int i = 0; i < 3; i++) {
            try {
                Shape nPiece = (Shape) piece.clone();
                int nX = curX + dx[i];
                int nY = curY + dy[i];
                
                if(nX < 0 || nX >= board.width() || nY < 0 || nY >= board.height())
                    continue;
                if(visited[nY][nX][nPiece.getRotateIndex()])
                    continue;
                if(!board.tryMove(nPiece, nX, nY)){
                    int weight = getWeight(nPiece);
                    if(maxWeight <= weight){
                        bestRoute = curRoute;
                        maxWeight = weight;
                    }
                    continue;
                }

                nPiece.moveTo(nX, nY);
                visited[nY][nX][nPiece.getRotateIndex()] = true;

                routeQueue.add(curRoute + Integer.toString(i));
                shapeQueue.add(nPiece);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    private void fineRouteRotate(Shape piece, String curRoute, int curX, int curY) {
        Shape rPiece = piece;
        for(int i = 0; i < 3; i++) {
            rPiece = rPiece.rotateRight();
        
            if(visited[curY][curX][rPiece.getRotateIndex()])
                continue;
            if(!board.tryMove(rPiece, curX, curY)){
                rPiece = rPiece.rotateLeft();
                int weight = getWeight(rPiece);
                if(maxWeight < weight){
                    bestRoute = curRoute;
                    maxWeight = weight;
                }
                break;
            }

            visited[curY][curX][rPiece.getRotateIndex()] = true;

            routeQueue.add(curRoute + "3");
            shapeQueue.add(rPiece);
        }
    }

    private int getWeight(Shape nPiece) {
        boolean[][] isCurPiece = new boolean[board.height()][board.width()];
        
        for(int i = 0; i < 4; i++){
            int blockX = nPiece.curX() + nPiece.x(i);
            int blockY = nPiece.curY() - nPiece.y(i);            
            isCurPiece[blockY][blockX] = true;
        }

        int weight = 0;

        for(int i = 0; i < 4; i++){
            int blockX = nPiece.curX() + nPiece.x(i);
            int blockY = nPiece.curY() - nPiece.y(i);
            
            for(int j = 0; j < 4; j++){ 
                int x = blockX + dx[j];
                int y = blockY + dy[j];

                if(x + 1 == 0 || x == board.width() || y + 1 == 0 || y == board.height()) {
                    weight++;
                    continue;
                }

                if(board.shapeAt(x, y) == Tetrominoes.NoShape) {
                    weight--;
                }
                else if(!isCurPiece[y][x] && board.shapeAt(x, y) != Tetrominoes.NoShape) {
                    weight++;
                }
            }
        }
        
        return weight;
    }
}