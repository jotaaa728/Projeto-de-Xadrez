package chess;

import board.Position;
import board.Board;

public class Pawn extends ChessPiece {
    private ChessMatch chessMatch;

    public Pawn(Board board, Color color) {
        this(board, color, null);
    }

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
        Position p = new Position(0,0);
        int dir = (getColor() == Color.WHITE) ? -1 : 1;

        // forward one
        p.setValues(position.getRow() + dir, position.getColumn());
        if (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // forward two
        p.setValues(position.getRow() + 2*dir, position.getColumn());
        Position p1 = new Position(position.getRow() + dir, position.getColumn());
        if (board.positionExists(p) && board.piece(p) == null && board.piece(p1) == null && getMoveCount()==0) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // capture left
        p.setValues(position.getRow() + dir, position.getColumn() - 1);
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // capture right
        p.setValues(position.getRow() + dir, position.getColumn() + 1);
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // en passant
        int enPassantRow = (getColor() == Color.WHITE) ? 3 : 4;
        if (chessMatch != null && position.getRow() == enPassantRow) {
            Position left = new Position(position.getRow(), position.getColumn() - 1);
            if (board.positionExists(left)) {
                ChessPiece leftPiece = (ChessPiece) board.piece(left);
                if (leftPiece != null
                        && leftPiece.getColor() != getColor()
                        && leftPiece == chessMatch.getEnPassantVulnerable()) {
                    mat[left.getRow() + dir][left.getColumn()] = true;
                }
            }

            Position right = new Position(position.getRow(), position.getColumn() + 1);
            if (board.positionExists(right)) {
                ChessPiece rightPiece = (ChessPiece) board.piece(right);
                if (rightPiece != null
                        && rightPiece.getColor() != getColor()
                        && rightPiece == chessMatch.getEnPassantVulnerable()) {
                    mat[right.getRow() + dir][right.getColumn()] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() { return "P"; }
}
