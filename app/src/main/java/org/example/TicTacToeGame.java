package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    private final char X = 'X';
    private final char O = 'O';

    private char currentPlayer;
    private char lastLoser = O;
    private int xWins = 0, oWins = 0, ties = 0;

    private final TicTacToeBoard board = new TicTacToeBoard();
    private final Scanner scanner = new Scanner(System.in);

    private boolean vsComputer = false;
    private boolean computerPlaysX = false;

    public void start() {
        System.out.println("Welcome to Tic-Tac-Toe!\n");
        chooseGameMode();
        boolean playing = true;

        while (playing) {
            board.print();
            if (isComputerTurn()) {
                System.out.println("Computer's move:");
                int move = getComputerMove(currentPlayer);
                board.makeMove(move, currentPlayer);
            } else {
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
            }

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

    private void chooseGameMode() {
        System.out.println("What kind of game would you like to play?\n");
        System.out.println("1. Human vs. Human");
        System.out.println("2. Human vs. Computer");
        System.out.println("3. Computer vs. Human\n");

        while (true) {
            System.out.print("What is your selection? ");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                vsComputer = false;
                currentPlayer = X;
                return;
            } else if (input.equals("2")) {
                vsComputer = true;
                computerPlaysX = false;
                currentPlayer = X;
                return;
            } else if (input.equals("3")) {
                vsComputer = true;
                computerPlaysX = true;
                currentPlayer = X;
                return;
            }
            System.out.println("Invalid choice. Please select 1, 2, or 3.");
        }
    }

    private boolean isComputerTurn() {
        return vsComputer && ((currentPlayer == X && computerPlaysX) || (currentPlayer == O && !computerPlaysX));
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

    private int getComputerMove(char symbol) {
        char opponent = (symbol == X) ? O : X;
        List<Integer> available = new ArrayList<>();
        for (int i = 1; i <= 9; i++) if (board.isValidMove(i)) available.add(i);

        // pick a corner
        if (available.size() == 9) {
            List<Integer> corners = Arrays.asList(1, 3, 7, 9);
            Collections.shuffle(corners);
            for (int corner : corners) if (board.isValidMove(corner)) return corner;
        }

        // center if available
        if (available.size() == 8 && board.isValidMove(5)) return 5;

        // Win if possible
        for (int move : available) {
            TicTacToeBoard temp = copyBoard();
            temp.makeMove(move, symbol);
            if (temp.checkWinner(symbol)) return move;
        }

        // Block opponent
        for (int move : available) {
            TicTacToeBoard temp = copyBoard();
            temp.makeMove(move, opponent);
            if (temp.checkWinner(opponent)) return move;
        }

        // Random fallback
        Collections.shuffle(available);
        return available.get(0);
    }

    private TicTacToeBoard copyBoard() {
        TicTacToeBoard copy = new TicTacToeBoard();
        for (int i = 1; i <= 9; i++) {
            if (!board.isValidMove(i)) {
                copy.makeMove(i, getSymbolAt(i));
            }
        }
        return copy;
    }

    private char getSymbolAt(int move) {
        int row = (move - 1) / 3;
        int col = (move - 1) % 3;
        try {
            java.lang.reflect.Field field = TicTacToeBoard.class.getDeclaredField("board");
            field.setAccessible(true);
            char[][] internalBoard = (char[][]) field.get(board);
            return internalBoard[row][col];
        } catch (Exception e) {
            return ' ';
        }
    }
}
