package chess;

import board.Position;
import board.Board;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
        Position p = new Position(0,0);

        // NW
        p.setValues(position.getRow() -1, position.getColumn() -1);
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() -1, p.getColumn() -1);
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // NE
        p.setValues(position.getRow() -1, position.getColumn() +1);
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() -1, p.getColumn() +1);
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // SE
        p.setValues(position.getRow() +1, position.getColumn() +1);
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() +1, p.getColumn() +1);
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // SW
        p.setValues(position.getRow() +1, position.getColumn() -1);
        while (board.positionExists(p) && board.piece(p) == null) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() +1, p.getColumn() -1);
        }
        if (board.positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }

    @Override
    public String toString() { return "B"; }
}
