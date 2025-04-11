package org.example;
import java.io.IOException;
import java.io.PrintWriter;

public class TicTacToeUtils {
    public static void printLog(int xWins, int oWins, int ties) {
        System.out.println("\nFinal Game Log:");
        System.out.println("Player X Wins: " + xWins);
        System.out.println("Player O Wins: " + oWins);
        System.out.println("Ties: " + ties);
    }

    public static void saveLog(int xWins, int oWins, int ties) {
        try (PrintWriter writer = new PrintWriter("game.txt")) {
            writer.println("Final Game Log:");
            writer.println("Player X Wins: " + xWins);
            writer.println("Player O Wins: " + oWins);
            writer.println("Ties: " + ties);
        } catch (IOException e) {
            System.out.println("Error writing game log: " + e.getMessage());
        }
    }
}
