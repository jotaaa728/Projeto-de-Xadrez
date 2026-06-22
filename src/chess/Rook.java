package chess;

import board.Position;
import board.Board;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
        Position p = new Position(0,0);

        // above
        p.setValues(position.getRow() - 1, position.getColumn());
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn());
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // left
        p.setValues(position.getRow(), position.getColumn() - 1);
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow(), p.getColumn() - 1);
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // right
        p.setValues(position.getRow(), position.getColumn() + 1);
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow(), p.getColumn() + 1);
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // below
        p.setValues(position.getRow() + 1, position.getColumn());
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn());
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }

    @Override
    public String toString() { return "R"; }
}
