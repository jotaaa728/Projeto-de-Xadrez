package chess;

import board.Board;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        // queen = rook + bishop
        boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
        Rook r = new Rook(board, getColor());
        Bishop b = new Bishop(board, getColor());
        r.setPosition(this.getPosition());
        b.setPosition(this.getPosition());
        boolean[][] rm = r.possibleMoves();
        boolean[][] bm = b.possibleMoves();
        for (int i=0;i<mat.length;i++) {
            for (int j=0;j<mat[i].length;j++) {
                mat[i][j] = rm[i][j] || bm[i][j];
            }
        }
        return mat;
    }

    @Override
    public String toString() { return "Q"; }
}
