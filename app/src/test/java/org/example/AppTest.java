package org.example;

import java.util.Scanner;

public class AppTest {
    private static final char[] board = {'1','2','3','4','5','6','7','8','9'};
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            resetBoard();
            System.out.println("Welcome to Tic-Tac-Toe!");
            printBoard();
            char currentPlayer = PLAYER_X;
            boolean gameEnded = false;

            while (!gameEnded) {
                System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");
                String input = scanner.nextLine().trim();

                if (!isValidMove(input)) {
                    System.out.println("That is not a valid move! Try again.");
                    continue;
                }

                int move = Integer.parseInt(input) - 1;
                board[move] = currentPlayer;
                printBoard();

                if (checkWinner(currentPlayer)) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameEnded = true;
                } else if (isDraw()) {
                    System.out.println("It's a draw!");
                    gameEnded = true;
                } else {
                    currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
                }
            }

            if (!askPlayAgain(scanner)) {
                System.out.println("Goodbye!");
                break;
            }
        }
        scanner.close();
    }

    public static void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = (char) ('1' + i);
        }
    }

    public static void printBoard() {
        System.out.println("\n " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("---+---+---");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8] + "\n");
    }

    public static boolean isValidMove(String input) {
        if (!input.matches("[1-9]")) return false;
        int move = Integer.parseInt(input) - 1;
        return board[move] != PLAYER_X && board[move] != PLAYER_O;
    }

    public static boolean checkWinner(char player) {
        int[][] winPatterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, 
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, 
            {0, 4, 8}, {2, 4, 6}             
        };
        for (int[] pattern : winPatterns) {
            if (board[pattern[0]] == player &&
                board[pattern[1]] == player &&
                board[pattern[2]] == player) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDraw() {
        for (char c : board) {
            if (c != PLAYER_X && c != PLAYER_O) return false;
        }
        return true;
    }

    public static boolean askPlayAgain(Scanner scanner) {
        while (true) {
            System.out.print("Would you like to play again (yes/no)? ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes")) return true;
            if (response.equals("no")) return false;
            System.out.println("That is not a valid entry!");
        }
    }
}
