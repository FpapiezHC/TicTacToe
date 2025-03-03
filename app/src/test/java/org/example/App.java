package org.example;

import java.util.Scanner;

public class App {
    private static final char X = 'X';
    private static final char O = '0';
    private static final int SIZE = 3;

    private char[][] board;
    private char currentPlayer;
    private boolean isGameActive;

    public App() {
        board = new char[SIZE][SIZE];
        initializeBoard();
        currentPlayer = X;
        isGameActive = true;
    }
    public void initializeBoard() {
        int cell = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = (char) ('0' + cell++);
            }
        }
    }
    public char[][] getBoard() {
        return board;
    }
    private void showBoard() {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < SIZE - 1) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }
    public boolean isValidMove(int move) {
        if (move < 1 || move > 9) {
            return false;
        }
        int row = (move - 1) / SIZE;
        int col = (move - 1) % SIZE;
        return board[row][col] != X && board[row][col] != O;
    }
    public void placeMove(int move) {
        int row = (move - 1) / SIZE;
        int col = (move - 1) % SIZE;
        board[row][col] = currentPlayer;
    }
    public boolean hasWinner() {
        for (int i = 0; i < SIZE; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
               (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }
    public boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell != X && cell != O) {
                    return false;
                }
            }
        }
        return true;
    }
    public void switchTurn() {
        currentPlayer = (currentPlayer == X) ? O : X;
    }
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        while (isGameActive) {
            showBoard();
            System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and 9.");
                scanner.next(); 
            }
            int move = scanner.nextInt();
            if (!isValidMove(move)) {
                System.out.println("That is not a valid move! Try again.");
                continue;
            }
            placeMove(move);
            if (hasWinner()) {
                showBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                isGameActive = false;
            } else if (isDraw()) {
                showBoard();
                System.out.println("It's a draw!");
                isGameActive = false;
            } else {
                switchTurn();
            }
        }
        scanner.nextLine();
        System.out.print("Would you like to play again (yes/no)? ");
        String response = scanner.nextLine().trim().toLowerCase();
        while (!response.equals("yes") && !response.equals("no")) {
            System.out.println("That is not a valid entry!");
            System.out.print("Would you like to play again (yes/no)? ");
            response = scanner.nextLine().trim().toLowerCase();
        }
        if (response.equals("yes")) {
            restartGame();
            startGame();
        } else {
            System.out.println("Goodbye!");
            scanner.close();
        }
    }
    public void restartGame() {
        initializeBoard();
        currentPlayer = X;
        isGameActive = true;
    }
    public static void main(String[] args) {
        AppTest game = new AppTest();
        game.startGame();
    }
}
