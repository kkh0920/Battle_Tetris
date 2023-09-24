package kr.ac.jbnu.se.tetris;

import java.util.LinkedList;
import java.util.Queue;

public class TetrisAI {

    BoardAI board;

    Queue<Shape> shape;
    Queue<String> route;

    boolean[][][] visited;
    
    Shape curPiece;

    String bestRoute;
    
    int[] dx;
    int[] dy;
    
    public TetrisAI(BoardAI board){
        this.board = board;
        dx = new int[] { -1, 1, 0, 0 };
        dy = new int[] { 0, 0, -1, 1 };
    }

    public String findBestRoute() throws CloneNotSupportedException {

        initFindRoute();
        
        for(int i = 0; i < 4; i++){
            visited[curPiece.curY()][curPiece.curX()][curPiece.getRotateIndex()] = true;

            route.add(bestRoute);
            bestRoute += "3";
            
            shape.add(curPiece);
            curPiece = curPiece.rotateRight();
        }

        int maxWeight = 0;

        while(!shape.isEmpty()) {
            Shape piece = (Shape) shape.poll().clone();
            String curRoute = route.poll();

            int curX = piece.curX();
            int curY = piece.curY();

            for(int i = 0; i < 3; i++) {
                Shape nPiece = (Shape) piece.clone();
                int nX = curX + dx[i];
                int nY = curY + dy[i];
                
                if(nX < 0 || nX >= board.BoardWidth || nY < 0 || nY >= board.BoardHeight)
                    continue;
                if(visited[nY][nX][nPiece.getRotateIndex()])
                    continue;
                if(!board.tryMove(nPiece, nX, nY)){
                    int weight = getWeight(nPiece, curX, curY);
                    if(maxWeight <= weight){
                        bestRoute = curRoute;
                        maxWeight = weight;
                    }
                    continue;
                }

                nPiece.moveTo(nX, nY);
                visited[nY][nX][nPiece.getRotateIndex()] = true;

                route.add(curRoute + Integer.toString(i));
                shape.add(nPiece);
            }
        }

        return bestRoute;
    }

    private void initFindRoute() throws CloneNotSupportedException{
        shape = new LinkedList<>();
        route = new LinkedList<>();
    
        // 0 : 왼쪽 / 1 : 오른쪽 / 2 : 아래 / 3 : right 회전
        bestRoute = "";
        visited = new boolean[board.BoardHeight][board.BoardWidth][4];
        curPiece = (Shape) board.curPiece.clone();
    }

    private int getWeight(Shape nPiece, int curX, int curY){
        boolean[][] isCurPiece = new boolean[board.BoardHeight][board.BoardWidth];

        for(int i = 0; i < 4; i++){
            int blockX = curX + nPiece.x(i);
            int blockY = curY - nPiece.y(i);

            if(blockX < 0 || blockX >= board.BoardWidth || blockY < 0 || blockY >= board.BoardHeight)
                return -1;
            
            isCurPiece[blockY][blockX] = true;
        }

        int weight = 0;

        for(int i = 0; i < 4; i++){
            int blockX = curX + nPiece.x(i);
            int blockY = curY - nPiece.y(i);
            
            for(int j = 0; j < 4; j++){ 
                int x = blockX + dx[j];
                int y = blockY + dy[j];

                if(x + 1 < 0 || x > board.BoardWidth || y + 1 < 0 || y > board.BoardHeight - 1)
                    continue;

                if(x + 1 == 0 || x == board.BoardWidth || y + 1 == 0 || y == board.BoardHeight - 1) {
                    weight += board.boardWeight[y + 1][x + 1];
                    continue;
                }

                if(j == 2 && board.board[y][x] == Tetrominoes.NoShape) {
                    weight -= board.boardWeight[y + 1][x + 1];
                }
                else if(!isCurPiece[y][x] && board.board[y][x] != Tetrominoes.NoShape) {
                    weight += board.boardWeight[y + 1][x + 1];
                }
            }
        }
        
        return weight;
    }
}
