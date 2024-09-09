package ChessLayer.pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Pawn extends ChessPiece {

    private final ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);
        int direction = (getColor() == Color.WHITE) ? -1 : 1;

        // Move one square forward
        p.setValues(position.getRow() + direction, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;

            // Move two squares forward on first move
            if (getMoveCount() == 0) {
                p.setValues(position.getRow() + 2 * direction, position.getColumn());
                Position p2 = new Position(position.getRow() + direction, position.getColumn());
                if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) &&
                        getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                    mat[p.getRow()][p.getColumn()] = true;
                }
            }
        }

        // Diagonal captures
        p.setValues(position.getRow() + direction, position.getColumn() - 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + direction, position.getColumn() + 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // En passant special move
        // Determine the row where en passant is valid based on color
        int enPassantRow = (getColor() == Color.WHITE) ? 3 : 4;
        if (position.getRow() == enPassantRow) {
            Position left = new Position(position.getRow(), position.getColumn() - 1);
            if (getBoard().positionExists(left) && isThereOpponentPiece(left) &&
                    getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                mat[left.getRow() + direction][left.getColumn()] = true;
            }
            Position right = new Position(position.getRow(), position.getColumn() + 1);
            if (getBoard().positionExists(right) && isThereOpponentPiece(right) &&
                    getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                mat[right.getRow() + direction][right.getColumn()] = true;
            }
        }

        return mat;
    }

}
