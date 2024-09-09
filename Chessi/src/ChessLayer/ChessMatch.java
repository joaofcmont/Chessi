package ChessLayer;

import BoardLayer.Board;
import BoardLayer.Piece;
import BoardLayer.Position;

import ChessLayer.pieces.*;

import java.util.ArrayList;
import java.util.List;


public class ChessMatch {

    private final Board board;
    private int turn;
    private Color currentPlayer;
    private ChessPiece enPassantVulnerable;
    private final List<Piece> piecesOnTheBoard = new ArrayList<>();
    private final List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch(){
        board = new Board(8,8);
        turn = 1;
        currentPlayer=Color.WHITE;
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] match = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                match[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return match;
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer== Color.WHITE)? Color.BLACK : Color.WHITE;
    }


    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece,new ChessPosition(column,row).toPosition());
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on the source position");
        }
        if(currentPlayer!=((ChessPiece) board.piece(position)).getColor()){
            throw new ChessException("The chosen piece is not yours");
        }

        // Check if the selected piece has any possible moves
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible move for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        // Check if the source and target positions are the same
        if (source.equals(target)) {
            return; // No validation needed if the piece is not moving
        }

        // Check if the target move is valid
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece cannot move to the Target position");
        }
    }


    public boolean[][] possibleMoves(ChessPosition p){
        Position position = p.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        // Validate moves
        validateSourcePosition(source);
        validateTargetPosition(source, target);

        // Get the moving piece
        ChessPiece movedPiece = (ChessPiece) board.piece(source);

        // Perform castling if applicable
        if (movedPiece instanceof King) {
            if (target.getColumn() == source.getColumn() + 2) {
                // Kingside castling
                Position kingsideRookPosition = new Position(source.getRow(), source.getColumn() + 3);
                if (canCastle(source, target)) {
                    performCastling(source, target);
                    return null; // No piece captured in castling
                }
            } else if (target.getColumn() == source.getColumn() - 2) {
                // Queenside castling
                Position queensideRookPosition = new Position(source.getRow(), source.getColumn() - 4);
                if (canCastle(source, target)) {
                    performCastling(source, target);
                    return null; // No piece captured in castling
                }
            }
        }

        // Make the move
        Piece capturedPiece = makeMove(source, target);

        // Special move en passant
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }
        nextTurn();
        return (ChessPiece) capturedPiece;
    }



    private void performCastling(Position source, Position target) {
        ChessPiece king = (ChessPiece) board.removePiece(source);
        king.increaseMoveCount();

        // Castling to the right (kingside)
        if (target.getColumn() == source.getColumn() + 2) {
            Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
            rook.increaseMoveCount();
            board.placePiece(king, target);
            board.placePiece(rook, rookTarget);
        }
        // Castling to the left (queenside)
        else if (target.getColumn() == source.getColumn() - 2) {
            Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
            rook.increaseMoveCount();
            board.placePiece(king, target);
            board.placePiece(rook, rookTarget);
        }
    }

    public boolean canCastle(Position source, Position target) {
        ChessPiece king = (ChessPiece) board.piece(source);

        // Check if the king has moved
        if (king.getMoveCount() > 0) {
            return false;
        }

        // Determine if castling is kingside or queenside
        int direction = (target.getColumn() > source.getColumn()) ? 1 : -1;
        Position rookPosition = (direction == 1) ? new Position(source.getRow(), source.getColumn() + 3) : new Position(source.getRow(), source.getColumn() - 4);

        // Check if the rook position is within bounds
        if (!board.positionExists(rookPosition)) {
            return false;
        }

        ChessPiece rook = (ChessPiece) board.piece(rookPosition);

        // Check if the rook has moved
        if (rook == null || rook.getMoveCount() > 0) {
            return false;
        }

        // Ensure no pieces between the king and rook and no squares are under attack
        int start = Math.min(source.getColumn(), rookPosition.getColumn()) + 1;
        int end = Math.max(source.getColumn(), rookPosition.getColumn()) - 1;
        for (int i = start; i <= end; i++) {
            Position intermediatePosition = new Position(source.getRow(), i);

            // Check if the intermediate position is within bounds
            if (!board.positionExists(intermediatePosition) || board.thereIsAPiece(intermediatePosition) || isSquareUnderAttack(intermediatePosition, king.getColor())) {
                return false;
            }
        }

        // Check that the king is not in check and does not pass through or land in check
        Position kingPassThroughPosition = new Position(source.getRow(), source.getColumn() + direction);
        return !isSquareUnderAttack(source, king.getColor()) && !isSquareUnderAttack(kingPassThroughPosition, king.getColor()) && !isSquareUnderAttack(target, king.getColor());
    }


    private boolean isSquareUnderAttack(Position position, Color color) {
        // Loop through all opponent pieces to check if any can attack the given position
        for (Piece piece : piecesOnTheBoard) {
            ChessPiece chessPiece = (ChessPiece) piece;
            if (chessPiece.getColor() != color && piece.possibleMove(position)) {
                return true;
            }
        }
        return false;
    }




    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        // Remove captured piece from the board if it's an en passant capture
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition = (p.getColor() == Color.WHITE) ?
                        new Position(target.getRow() + 1, target.getColumn()) :
                        new Position(target.getRow() - 1, target.getColumn());

                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        // Update captured pieces and pieces on the board
        if (capturedPiece != null) {
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }

        return capturedPiece;
    }




    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE,this));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bisp(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE,this));
        placeNewPiece('f', 1, new Bisp(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE,this));

        placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK,this));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bisp(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK,this));
        placeNewPiece('f', 8, new Bisp(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK,this));

        placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));
    }
}
