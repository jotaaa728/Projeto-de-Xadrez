package chess;

import board.Position;
import board.Board;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
        Position p = new Position(0,0);

        int[][] moves = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
        for (int[] m : moves) {
            p.setValues(position.getRow() + m[0], position.getColumn() + m[1]);
            if (board.positionExists(p)) {
                if (board.piece(p) == null || isThereOpponentPiece(p)) {
                    mat[p.getRow()][p.getColumn()] = true;
                }
            }
        }
        return mat;
    }

    @Override
    public String toString() { return "N"; }
}
