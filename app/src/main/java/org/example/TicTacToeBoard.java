package org.example;

public class TicTacToeBoard {
    private final char[][] board;
    private final int SIZE = 3;
    private final char X = 'X';
    private final char O = 'O';

    public TicTacToeBoard() {
        board = new char[SIZE][SIZE];
        reset();
    }

    public void reset() {
        int cell = 1;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = (char) ('0' + cell++);
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("---+---+---");
        }
        System.out.println();
    }

    public boolean isValidMove(int move) {
        if (move < 1 || move > 9) return false;
        int row = (move - 1) / SIZE;
        int col = (move - 1) % SIZE;
        return board[row][col] != X && board[row][col] != O;
    }

    public void makeMove(int move, char player) {
        int row = (move - 1) / SIZE;
        int col = (move - 1) % SIZE;
        board[row][col] = player;
    }

    public boolean checkWinner(char player) {
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player))
                return true;
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    public boolean isFull() {
        for (char[] row : board)
            for (char cell : row)
                if (cell != X && cell != O)
                    return false;
        return true;
    }
}
