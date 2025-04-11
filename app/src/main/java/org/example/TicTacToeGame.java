package org.example;

import java.util.Scanner;

public class TicTacToeGame {
    private final char X = 'X';
    private final char O = 'O';

    private char currentPlayer = X;
    private char lastLoser = O;
    private int xWins = 0, oWins = 0, ties = 0;

    private final TicTacToeBoard board = new TicTacToeBoard();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean playing = true;
        System.out.println("Welcome to Tic-Tac-Toe!");

        while (playing) {
            board.print();
            System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");
            String input = scanner.nextLine().trim();

            if (!input.matches("[1-9]")) {
                System.out.println("Invalid input. Please enter a number from 1 to 9.");
                continue;
            }

            int move = Integer.parseInt(input);
            if (!board.isValidMove(move)) {
                System.out.println("That spot is already taken! Try another.");
                continue;
            }

            board.makeMove(move, currentPlayer);

            if (board.checkWinner(currentPlayer)) {
                board.print();
                System.out.println("Player " + currentPlayer + " wins!");
                if (currentPlayer == X) xWins++; else oWins++;
                lastLoser = (currentPlayer == X) ? O : X;
                playing = askReplay();
            } else if (board.isFull()) {
                board.print();
                System.out.println("It's a draw!");
                ties++;
                lastLoser = (lastLoser == X) ? O : X;
                playing = askReplay();
            } else {
                switchPlayer();
            }
        }

        TicTacToeUtils.printLog(xWins, oWins, ties);
        TicTacToeUtils.saveLog(xWins, oWins, ties);
        System.out.println("Game log saved. Goodbye!");
        scanner.close();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == X) ? O : X;
    }

    private boolean askReplay() {
        while (true) {
            System.out.print("Would you like to play again (yes/no)? ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("yes")) {
                board.reset();
                currentPlayer = lastLoser;
                System.out.println("\nNew game! " + currentPlayer + " goes first.");
                return true;
            } else if (answer.equals("no")) {
                return false;
            } else {
                System.out.println("Please answer 'yes' or 'no'.");
            }
        }
    }
}
