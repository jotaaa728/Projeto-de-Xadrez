package chess;

import board.Piece;
import board.Position;
import board.Board;

public abstract class ChessPiece extends Piece {
    private Color color;
    private int moveCount;
    protected Board board;

    public ChessPiece(Board board, Color color) {
        this.board = board;
        this.color = color;
        this.moveCount = 0;
    }

    public Color getColor() { return color; }
    public int getMoveCount() { return moveCount; }

    protected void increaseMoveCount() { moveCount++; }
    protected void decreaseMoveCount() { moveCount--; }

    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(board.Position position) {
        if (!board.positionExists(position)) return false;
        ChessPiece cp = (ChessPiece) board.piece(position);
        return cp != null && cp.getColor() != color;
    }

    @Override
    public abstract boolean[][] possibleMoves();
}
