package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private int[] board = new int[16];

    private class TileCoordinates {
        public int x;
        public int y;

        TileCoordinates(int x, int y) {
              this.x = x;
              this.y = y;
        }
    }

    private final TileCoordinates[] Coordinates = new TileCoordinates[16];

    public Board() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i * 4 + j] = i * 4 + j + 1;
                if (i != 3 || j != 3) Coordinates[i * 4 + j + 1] = new TileCoordinates(j + 1, i + 1);
            }
        }
        board[15] = 0;
        Coordinates[0] = new TileCoordinates(4, 4);
    }

    public Board(int[] board) {
        if (board.length != 16) {
            throw new IllegalArgumentException("Board's length is not 16");
        }
        boolean[] wasPlaced = new boolean[16];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (wasPlaced[board[i * 4 + j]]) throw new IllegalArgumentException("Board contains repeating elements");
                else wasPlaced[board[i * 4 + j]] = true;
                Coordinates[board[i * 4 + j]] = new TileCoordinates(j + 1, i + 1);
            }
        }



        this.board = board.clone();

        if(!isSolvable()) throw new IllegalArgumentException("Entered board is not solvable!");
    }

    public int[] getBoard() {
        return board.clone();
    }

    public boolean moveUp() {
        if (Coordinates[0].y > 1) {
            int movedTile = board[(Coordinates[0].y - 2) * 4 + (Coordinates[0].x - 1)];
            Coordinates[movedTile].y++;
            Coordinates[0].y--;
            board[(Coordinates[0].y - 1) * 4 + (Coordinates[0].x - 1)] = 0;
            board[(Coordinates[movedTile].y - 1) * 4 + (Coordinates[movedTile].x - 1)] = movedTile;
        }
        else return false;

        return true;
    }

    public boolean moveDown() {
        if (Coordinates[0].y < 4) {
            int movedTile = board[(Coordinates[0].y) * 4 + (Coordinates[0].x - 1)];
            Coordinates[movedTile].y--;
            Coordinates[0].y++;
            board[(Coordinates[0].y - 1) * 4 + (Coordinates[0].x - 1)] = 0;
            board[(Coordinates[movedTile].y - 1) * 4 + (Coordinates[movedTile].x - 1)] = movedTile;
        }
        else return false;

        return true;
    }

    public boolean moveLeft() {
        if (Coordinates[0].x > 1) {
            int movedTile = board[(Coordinates[0].y - 1) * 4 + (Coordinates[0].x - 2)];
            Coordinates[movedTile].x++;
            Coordinates[0].x--;
            board[(Coordinates[0].y - 1) * 4 + (Coordinates[0].x - 1)] = 0;
            board[(Coordinates[movedTile].y - 1) * 4 + (Coordinates[movedTile].x - 1)] = movedTile;
        }
        else return false;

        return true;
    }

    public boolean moveRight() {
        if (Coordinates[0].x < 4) {
            int movedTile = board[(Coordinates[0].y - 1) * 4 + (Coordinates[0].x)];
            Coordinates[movedTile].x--;
            Coordinates[0].x++;
            board[(Coordinates[0].y - 1) * 4 + (Coordinates[0].x - 1)] = 0;
            board[(Coordinates[movedTile].y - 1) * 4 + (Coordinates[movedTile].x - 1)] = movedTile;
        }
        else return false;

        return true;
    }

    public void shuffle () {
        Random rand = new Random();
//        for (int i = 0; i < 2000; i++) {
//            int temp = rand.nextInt(4);
//            if (temp == 3) moveRight();
//            else if (temp == 2) moveLeft();
//            else if (temp == 1) moveDown();
//            else if (temp == 0) moveUp();
//        }
//        int checkSum = 1;
        int[] shuffledBoard = new int[16];

        do {
            ArrayList<Integer> poll = new ArrayList<>();
            for (int i = 0; i < 16; i++) poll.add(i);

            for (int i = 0; i < 16; i++) {
                shuffledBoard[i] = poll.remove(rand.nextInt(poll.size()));
            }

            board = shuffledBoard.clone();
        } while (!isSolvable());
    }

    public boolean isSolvable() {
        int checkSum = 0;

        int zeroPlace = 0;
        for (int i = 0; i < 16; i++) {
            if (board[i] == 0) zeroPlace = (i / 4) + 1;
        }
        //checkSum = zeroPlace;

        for (int i = 0; i < 16; i++) {
            if (board[i] == 0) {
                zeroPlace = (i / 4) + 1;
                continue;
            }
            for (int j = i + 1; j < 16; j++) {
                if (board[j] == 0) continue;
                if (board[j] < board[i]) checkSum++;
            }
        }
        checkSum += zeroPlace;
        return (checkSum % 2 == 0);
    }

    public Solver getSolver() {
        return new Solver(board.clone());
    }

    public ArrayList<int[]> getMoves() {
        ArrayList<int[]> moves = new ArrayList<>();
        if (moveUp()) {
            moves.add(board.clone());
            moveDown();
        }
        if (moveDown()) {
            moves.add(board.clone());
            moveUp();
        }
        if (moveLeft()) {
            moves.add(board.clone());
            moveRight();
        }
        if (moveRight()) {
            moves.add(board.clone());
            moveLeft();
        }



        return moves;
    }

    public String toString() {
        StringBuilder board = new StringBuilder();

        board.append(" ═".repeat(9));
        board.append(System.getProperty("line.separator"));

        for (int i = 0; i < 4; i++) {
            board.append("‖");
            for (int j = 0; j < 4; j++) {
               if(this.board[i * 4 + j] / 10 == 0){
                   board.append(" ");
                   board.append(" ");
                   if(this.board[i * 4 + j] == 0) board.append(" ");
                   else board.append(this.board[i * 4 + j]);
                   board.append(" ");
               } else {
                   board.append(" ");
                   board.append(this.board[i * 4 + j]);
                   board.append(" ");
               }


                board.append("‖");
            }
            board.append(System.getProperty("line.separator"));
            //board.append(Character.charCount(13));
            board.append(" ═".repeat(9));
            board.append(System.getProperty("line.separator"));
        }
        return board.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return Arrays.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
