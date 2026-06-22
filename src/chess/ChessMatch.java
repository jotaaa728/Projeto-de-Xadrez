package chess;

import board.Board;
import board.Position;
import board.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;

    private List<ChessPiece> piecesOnBoard;
    private List<ChessPiece> captured;

    private ChessPiece promoted;
    private ChessPiece enPassantVulnerable;

    public ChessMatch() {
        board = new Board(8,8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnBoard = new ArrayList<>();
        captured = new ArrayList<>();
        initialSetup();
    }

    public int getTurn() { return turn; }
    public Color getCurrentPlayer() { return currentPlayer; }
    public boolean getCheck() { return check; }
    public boolean getCheckMate() { return checkMate; }
    public ChessPiece getPromoted() { return promoted; }
    public ChessPiece getEnPassantVulnerable() { return enPassantVulnerable; }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i=0;i<board.getRows();i++) {
            for (int j=0;j<board.getColumns();j++) {
                Piece p = board.piece(new Position(i,j));
                mat[i][j] = (ChessPiece) p;
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(chess.ChessPosition sourcePosition) {
        Position pos = sourcePosition.toPosition();
        validateSourcePosition(pos);
        return board.piece(pos).possibleMoves();
    }

    public ChessPiece performChessMove(chess.ChessPosition sourcePosition, chess.ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        // promotion
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) ||
                (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promoted = movedPiece;
                // replace by queen by default
                ChessPiece queen = new Queen(board, movedPiece.getColor());
                queen.setPosition(movedPiece.getPosition());
                board.removePiece(target);
                board.placePiece(queen, target);
                piecesOnBoard.remove(movedPiece);
                piecesOnBoard.add(queen);
            }
        }

        check = testCheck(opponent(currentPlayer));

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        // en passant
        if (board.piece(target) instanceof Pawn &&
            Math.abs(target.getRow() - source.getRow()) == 2) {
            enPassantVulnerable = (ChessPiece) board.piece(target);
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        ((ChessPiece)p).increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        // special move castling rook
        if (p instanceof King) {
            if (target.getColumn() == source.getColumn() + 2) {
                // king side
                Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
                Position targetR = new Position(source.getRow(), source.getColumn() + 1);
                Piece rook = board.removePiece(sourceR);
                board.placePiece(rook, targetR);
                ((ChessPiece)rook).increaseMoveCount();
            } else if (target.getColumn() == source.getColumn() - 2) {
                // queen side
                Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
                Position targetR = new Position(source.getRow(), source.getColumn() - 1);
                Piece rook = board.removePiece(sourceR);
                board.placePiece(rook, targetR);
                ((ChessPiece)rook).increaseMoveCount();
            }
        }

        // special move en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getPosition().getRow() == source.getRow()) {
                    pawnPosition = new Position(source.getRow(), target.getColumn());
                } else {
                    pawnPosition = new Position(source.getRow(), target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                captured.add((ChessPiece) capturedPiece);
                piecesOnBoard.remove(capturedPiece);
            }
        }

        if (capturedPiece != null) {
            captured.add((ChessPiece) capturedPiece);
            piecesOnBoard.remove(capturedPiece);
        }

        piecesOnBoard.remove((ChessPiece)p);
        piecesOnBoard.add((ChessPiece)p);

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece p = board.removePiece(target);
        ((ChessPiece)p).decreaseMoveCount();
        board.placePiece(p, source);
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            captured.remove((ChessPiece) capturedPiece);
            piecesOnBoard.add((ChessPiece) capturedPiece);
        }

        // special move castling rook undo
        if (p instanceof King) {
            if (target.getColumn() == source.getColumn() + 2) {
                Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
                Position targetR = new Position(source.getRow(), source.getColumn() + 1);
                Piece rook = board.removePiece(targetR);
                board.placePiece(rook, sourceR);
                ((ChessPiece)rook).decreaseMoveCount();
            } else if (target.getColumn() == source.getColumn() - 2) {
                Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
                Position targetR = new Position(source.getRow(), source.getColumn() - 1);
                Piece rook = board.removePiece(targetR);
                board.placePiece(rook, sourceR);
                ((ChessPiece)rook).decreaseMoveCount();
            }
        }

        // special move en passant undo
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Piece pawn = board.removePiece(source);
                Position pawnPosition;
                if (((ChessPiece) p).getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public void validateSourcePosition(Position position) {
        if (!board.positionExists(position)) {
            throw new ChessException("Source position not on the board");
        }
        if (board.piece(position) == null) {
            throw new ChessException("There is no piece on source position");
        }
        if (!((ChessPiece)board.piece(position)).getColor().equals(currentPlayer)) {
            throw new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    public void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new Position(8 - row, column - 'a'));
        piecesOnBoard.add(piece);
    }

    private void initialSetup() {
        // White pieces
        placeNewPiece('a',1, new Rook(board, Color.WHITE));
        placeNewPiece('b',1, new Knight(board, Color.WHITE));
        placeNewPiece('c',1, new Bishop(board, Color.WHITE));
        placeNewPiece('d',1, new Queen(board, Color.WHITE));
        placeNewPiece('e',1, new King(board, Color.WHITE));
        placeNewPiece('f',1, new Bishop(board, Color.WHITE));
        placeNewPiece('g',1, new Knight(board, Color.WHITE));
        placeNewPiece('h',1, new Rook(board, Color.WHITE));
        for (char c='a'; c<='h'; c++) {
            placeNewPiece(c,2, new Pawn(board, Color.WHITE));
        }

        // Black pieces
        placeNewPiece('a',8, new Rook(board, Color.BLACK));
        placeNewPiece('b',8, new Knight(board, Color.BLACK));
        placeNewPiece('c',8, new Bishop(board, Color.BLACK));
        placeNewPiece('d',8, new Queen(board, Color.BLACK));
        placeNewPiece('e',8, new King(board, Color.BLACK));
        placeNewPiece('f',8, new Bishop(board, Color.BLACK));
        placeNewPiece('g',8, new Knight(board, Color.BLACK));
        placeNewPiece('h',8, new Rook(board, Color.BLACK));
        for (char c='a'; c<='h'; c++) {
            placeNewPiece(c,7, new Pawn(board, Color.BLACK));
        }
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        for (ChessPiece cp : piecesOnBoard) {
            if (cp instanceof King && cp.getColor() == color) {
                return cp;
            }
        }
        return null;
    }

    private boolean testCheck(Color color) {
    	Position kingPos = king(color).getPosition();
        for (ChessPiece p : piecesOnBoard) {
            if (p.getColor() != color) {
                boolean[][] mat = p.possibleMoves();
                if (mat[kingPos.getRow()][kingPos.getColumn()]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        for (ChessPiece p : piecesOnBoard) {
            if (p.getColor() == color) {
                boolean[][] mat = p.possibleMoves();
                for (int i=0;i<board.getRows();i++) {
                    for (int j=0;j<board.getColumns();j++) {
                        if (mat[i][j]) {
                            Position source = p.getPosition();
                            Position target = new Position(i,j);
                            Piece capturedPiece = makeMove(source, target);
                            boolean testCheck = testCheck(color);
                            undoMove(source, target, capturedPiece);
                            if (!testCheck) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new ChessException("There is no piece to be promoted");
        }
        Position pos = promoted.getPosition();
        Piece p = board.removePiece(pos);
        piecesOnBoard.remove(promoted);
        ChessPiece newPiece;
        if (type.equals("Q")) {
            newPiece = new Queen(board, promoted.getColor());
        } else if (type.equals("R")) {
            newPiece = new Rook(board, promoted.getColor());
        } else if (type.equals("B")) {
            newPiece = new Bishop(board, promoted.getColor());
        } else if (type.equals("N")) {
            newPiece = new Knight(board, promoted.getColor());
        } else {
            newPiece = new Queen(board, promoted.getColor());
        }
        board.placePiece(newPiece, pos);
        piecesOnBoard.add(newPiece);
        promoted = newPiece;
        return newPiece;
    }
}