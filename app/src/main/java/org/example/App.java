package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class App {
    private static final char X = 'X';
    private static final char O = 'O';
    private static final int SIZE = 3;

    private char[][] board;
    private char currentPlayer;
    private boolean isGameActive;

    private int xWins = 0;
    private int oWins = 0;
    private int ties = 0;
    private char lastLoser = O;

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

    public void showBoard() {
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
        if (move < 1 || move > 9) return false;
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
            if ((board[i][0] == currentPlayer &&
                 board[i][1] == currentPlayer &&
                 board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer &&
                 board[1][i] == currentPlayer &&
                 board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][2] == currentPlayer) ||
               (board[0][2] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][0] == currentPlayer);
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

    public void restartGame() {
        initializeBoard();
        isGameActive = true;
        currentPlayer = lastLoser;
    }

    public void printLog() {
        System.out.println("\nCurrent Game Log:");
        System.out.println("Player X Wins: " + xWins);
        System.out.println("Player O Wins: " + oWins);
        System.out.println("Ties: " + ties);
    }

    public void saveGameLog() {
        try (PrintWriter writer = new PrintWriter("game.txt")) {
            writer.println("Final Game Log:");
            writer.println("Player X Wins: " + xWins);
            writer.println("Player O Wins: " + oWins);
            writer.println("Ties: " + ties);
        } catch (IOException e) {
            System.out.println("Error saving game log: " + e.getMessage());
        }
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        while (isGameActive) {
            showBoard();
            System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");
            String input = scanner.nextLine().trim();

            if (!input.matches("[1-9]")) {
                System.out.println("Invalid input! Please enter a number from 1 to 9.");
                continue;
            }

            int move = Integer.parseInt(input);
            if (!isValidMove(move)) {
                System.out.println("That spot is already taken! Try another.");
                continue;
            }

            placeMove(move);

            if (hasWinner()) {
                showBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                if (currentPlayer == X) xWins++; else oWins++;
                lastLoser = (currentPlayer == X) ? O : X;
                isGameActive = false;
            } else if (isDraw()) {
                showBoard();
                System.out.println("It's a draw!");
                ties++;
                lastLoser = (lastLoser == X) ? O : X;
                isGameActive = false;
            } else {
                switchTurn();
            }
        }

        printLog();

        System.out.print("\nWould you like to play again (yes/no)? ");
        String response = scanner.nextLine().trim().toLowerCase();
        while (!response.equals("yes") && !response.equals("no")) {
            System.out.println("Invalid response. Please type 'yes' or 'no'.");
            response = scanner.nextLine().trim().toLowerCase();
        }

        if (response.equals("yes")) {
            System.out.println("\nGreat! This time " + lastLoser + " will go first!");
            restartGame();
            startGame();
        } else {
            System.out.println("Writing the game log to disk...");
            saveGameLog();
            System.out.println("Game log saved to game.txt");
            System.out.println("Goodbye!");
            scanner.close();
        }
    }

    public static void main(String[] args) {
        App game = new App();
        game.startGame();
    }
}
