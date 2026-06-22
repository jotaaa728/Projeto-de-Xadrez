package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.printBoard(chessMatch.getPieces());
                System.out.print("Source (e.g. e2): ");
                ChessPosition source = UI.readChessPosition(sc);
                System.out.print("Target (e.g. e4): ");
                ChessPosition target = UI.readChessPosition(sc);
                ChessPiece captured = chessMatch.performChessMove(source, target);
                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (Q/R/B/N): ");
                    String type = sc.nextLine().toUpperCase();
                    chessMatch.replacePromotedPiece(type);
                }
                if (captured != null) {
                    System.out.println("Captured: " + captured);
                }
                if (chessMatch.getCheck()) {
                    System.out.println("CHECK!");
                }
                if (chessMatch.getCheckMate()) {
                    System.out.println("CHECKMATE!");
                    break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }
}
