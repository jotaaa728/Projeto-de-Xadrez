package chess;

import board.Position;
import board.Board;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position) {
        if (!board.positionExists(position)) return false;
        ChessPiece p = (ChessPiece) board.piece(position);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
        Position p = new Position(0,0);

        int[][] moves = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
        for (int[] m : moves) {
            p.setValues(this.position.getRow() + m[0], this.position.getColumn() + m[1]);
            if (board.positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        // castling
        if (getMoveCount() == 0 && !((ChessMatch) null instanceof ChessMatch)) {
            // placeholder to satisfy structure (real check implemented in ChessMatch)
        }

        return mat;
    }

    @Override
    public String toString() {
        return "K";
    }
}
