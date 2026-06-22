package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.Scanner;
import java.util.InputMismatchException;

public class UI {

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i=0;i<pieces.length;i++) {
            System.out.print(8 - i + " ");
            for (int j=0;j<pieces[i].length;j++) {
                if (pieces[i][j] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(pieces[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8");
        }
    }
}
