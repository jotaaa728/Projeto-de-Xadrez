package chess;

import board.Position;
import board.Board;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
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

        // en passant capture
        board.Position left = new board.Position(position.getRow(), position.getColumn() - 1);
        if (board.positionExists(left)) {
            ChessPiece pleft = (ChessPiece) board.piece(left);
            if (pleft != null && pleft instanceof Pawn && pleft.getColor() != getColor() && ((ChessMatch) null instanceof ChessMatch)) {
                // placeholder, real check handled in ChessMatch when exposing enPassantVulnerable
            }
        }

        return mat;
    }

    @Override
    public String toString() { return "P"; }
}
