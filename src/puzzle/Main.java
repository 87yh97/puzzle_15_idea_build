package puzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {
        System.out.print("Type the command: ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String inpStr;
        start: while(!(inpStr = input.readLine()).equals("exit")) {
            String[] inp = inpStr.split(" ");

            switch (inp[0]) {
                case "test": {
                    long maxTime = 0;
                    Board theMostDifficultBoard = null;
                    int testNum;
                    try {
                        testNum = Integer.parseInt(inp[1]);
                    } catch (IndexOutOfBoundsException | NumberFormatException e) {
                        System.out.println("Wrong input");
                        continue;
                    }
                    if (testNum < 1) {
                        System.out.println("Wrong input");
                        continue;
                    }
                    for (int p = 0; p < testNum; p++) {
                        Board board = new Board();
                        if (theMostDifficultBoard == null) theMostDifficultBoard = board;
                        board.shuffle();
                        System.out.println("=========================================================");
                        System.out.println(board.toString());
                        Solver solver = board.getSolver();
                        long start = System.currentTimeMillis();
                        ArrayList<Board> solution = solver.solve();
                        long stop = System.currentTimeMillis();
                        long time = (stop - start);
                        System.out.println("NUMBER OF STEPS: " + solution.size());
                        System.out.println("TIME: " + time / 1000 / 60 + ":" + time / 1000 % 60);
                        System.out.println("=========================================================");
                        System.out.println();
                        if (time > maxTime) {
                            theMostDifficultBoard = board;
                            maxTime = time;
                        }

                        System.gc();

                    }
                    System.gc();
                    System.out.println("MAX TIME: " + maxTime / 1000 / 60 + ":" + maxTime / 1000 % 60);
                    System.out.println("THE MOST DIFFICULT BOARD: ");
                    System.out.println(theMostDifficultBoard.toString());
                    System.out.println("ALL DONE! :)");
                    System.out.println();
                    System.out.print("Type the command: ");

                    break;
                }
                case "trace": {
                    Board board = new Board();
                    board.shuffle();
                    System.out.println("=========================================================");
                    System.out.println("INITIAL BOARD: ");
                    System.out.println(board.toString());
                    System.out.println("=========================================================");
                    Solver solver = board.getSolver();
                    long start = System.currentTimeMillis();
                    ArrayList<Board> solution = solver.solve();
                    long stop = System.currentTimeMillis();
                    long time = (stop - start);
                    for (int i = solution.size() - 1; i >= 0; i--) {
                        System.out.println("=========================================================");
                        System.out.println(solution.get(i).toString());
                        System.out.println();
                        System.out.println("STEP NUMBER: " + (solution.size() - i));
                        System.out.println("=========================================================");
                        System.out.println();
                    }

                    System.out.println("NUMBER OF STEPS: " + solution.size());
                    System.out.println("TIME: " + time / 1000 / 60 + ":" + time / 1000 % 60);
                    System.out.println();
                    System.out.println("ALL DONE! :)");
                    System.out.println();
                    System.out.print("Type the command: ");

                    break;
                }
                case "set_and_trace": {
                    ArrayList<Integer> userBoard = new ArrayList<>();
                    for (int i = 0; i < 16; i++) {
                        System.out.print("Type in [" + (i / 4 + 1) + "," + (i % 4 + 1) + "] element: ");

                        String element = input.readLine();
                        if (element.equals("exit")) return;
                        if (element.equals("restart")) {
                            System.out.print("Type the command: ");
                            continue start;
                        }
                        int testNum;
                        try {
                            testNum = Integer.parseInt(element);
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            System.out.println("Wrong input");
                            i--;
                            continue;
                        }
                        if (testNum < 0 || testNum > 15 || userBoard.contains(testNum)) {
                            System.out.println("Wrong input");
                            i--;
                            continue;
                        }

                        userBoard.add(i, testNum);
                    }
                    int[] userBoardArr = new int[16];
                    for (int i = 0; i < 16; i++) userBoardArr[i] = userBoard.get(i);
                    Board board = new Board(userBoardArr);
                    if (!board.isSolvable()) {
                        System.out.println("This board is not solvable");
                        System.out.print("Type the command: ");
                        continue;
                    }

                    System.out.println("=========================================================");
                    System.out.println("INITIAL BOARD: ");
                    System.out.println(board.toString());
                    System.out.println("=========================================================");
                    Solver solver = board.getSolver();
                    long start = System.currentTimeMillis();
                    ArrayList<Board> solution = solver.solve();
                    long stop = System.currentTimeMillis();
                    long time = (stop - start);
                    for (int i = solution.size() - 1; i >= 0; i--) {
                        System.out.println("=========================================================");
                        System.out.println(solution.get(i).toString());
                        System.out.println();
                        System.out.println("STEP NUMBER: " + (solution.size() - i));
                        System.out.println("=========================================================");
                        System.out.println();
                    }

                    System.out.println("NUMBER OF STEPS: " + solution.size());
                    System.out.println("TIME: " + time / 1000 / 60 + ":" + time / 1000 % 60);
                    System.out.println();
                    System.out.println("ALL DONE! :)");
                    System.out.println();
                    System.out.print("Type the command: ");
                    break;
                }
                default:
                    System.out.print("Type the command: ");
                    break;
            }
        }
    }
}
