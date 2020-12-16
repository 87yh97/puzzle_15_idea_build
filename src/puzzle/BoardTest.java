package puzzle;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @org.junit.jupiter.api.Test
    void Board() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,15,14,0};
        assertThrows(IllegalArgumentException.class, () -> new Board(b1));
        int[] b2 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,0};
        assertThrows(IllegalArgumentException.class, () -> new Board(b2));
    }

    @org.junit.jupiter.api.Test
    void moveUp() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,0,10,11,12,13,15,14};
        Board board1 = new Board(b1);
        int[] b2 = {1,2,3,4,5,0,7,8,9,6,10,11,12,13,15,14};
        Board board2 = new Board(b2);
        assertNotEquals(board1, board2);
        board1.moveUp();
        assertEquals(board1, board2);
    }

    @org.junit.jupiter.api.Test
    void moveDown() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,0,10,11,12,13,15,14};
        Board board1 = new Board(b1);
        int[] b2 = {1,2,3,4,5,6,7,8,9,13,10,11,12,0,15,14};
        Board board2 = new Board(b2);
        assertNotEquals(board1, board2);
        board1.moveDown();
        assertEquals(board1, board2);
    }

    @org.junit.jupiter.api.Test
    void moveLeft() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,0,10,11,12,13,15,14};
        Board board1 = new Board(b1);
        int[] b2 = {1,2,3,4,5,6,7,8,0,9,10,11,12,13,15,14};
        Board board2 = new Board(b2);
        assertNotEquals(board1, board2);
        board1.moveLeft();
        assertEquals(board1, board2);
    }

    @org.junit.jupiter.api.Test
    void moveRight() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,0,10,11,12,13,15,14};
        Board board1 = new Board(b1);
        int[] b2 = {1,2,3,4,5,6,7,8,9,10,0,11,12,13,15,14};
        Board board2 = new Board(b2);
        assertNotEquals(board1, board2);
        board1.moveRight();
        assertEquals(board1, board2);
    }

    @org.junit.jupiter.api.Test
    void shuffle() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,0,10,11,12,13,15,14};
        Board board1 = new Board(b1);
        int[] b2 = {1,2,3,4,5,6,7,8,9,0,10,11,12,13,15,14};
        Board board2 = new Board(b2);
        assertEquals(board1, board2);
        board1.shuffle();
        assertNotEquals(board1, board2);
    }

    @org.junit.jupiter.api.Test
    void isSolvable() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,15,14,0};
        assertThrows(IllegalArgumentException.class, () -> new Board(b1));
        int[] b2 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,15};
        Board board2 = new Board(b2);
        assertTrue(board2.isSolvable());
    }

    @org.junit.jupiter.api.Test
    void equals() {
        int[] b1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,15};
        Board board1 = new Board(b1);
        Board board2 = new Board(b1);

        assertEquals(board1, board2);
        assertEquals(board2, board1);
    }

    @org.junit.jupiter.api.Test
    void solve() {
        Board board1 = new Board();
        board1.shuffle();
        Board board2 = new Board();
        Solver solver = board1.getSolver();
        ArrayList<Board> solution = solver.solve();
        assertEquals(solution.get(0), board2);
    }
}