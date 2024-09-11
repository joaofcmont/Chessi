package ChessLayer.pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Pawn extends ChessPiece {

    private final ChessMatch chessMatch;
    int direction;

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

        // Ensure that 'position' is not null before proceeding
        if (position == null) {
            System.out.println("Error: Pawn position is null.");
            return mat;
        }


        Position p = new Position(0, 0);

        // Move one square forward
        p.setValues(position.getRow() -1, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;


            // Move two squares forward on first move
            if (getMoveCount() == 0) {
                Position p2 = new Position(position.getRow() - 2, position.getColumn());
                Position intermediate = new Position(position.getRow() + direction, position.getColumn());
                if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {
                    mat[p2.getRow()][p2.getColumn()] = true;
                }
            }
        }

        // Diagonal capture left
        p.setValues(position.getRow() -1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Diagonal capture right
        p.setValues(position.getRow() -1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // En passant special move
        int enPassantRow = (getColor() == Color.WHITE) ? 3 : 4; // Row for en passant
        if (position.getRow() == enPassantRow) {
            Position left = new Position(position.getRow(), position.getColumn() - 1);
            Position right = new Position(position.getRow(), position.getColumn() + 1);

            if (getBoard().positionExists(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                mat[left.getRow() + direction][left.getColumn()] = true;
            }

            if (getBoard().positionExists(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                mat[right.getRow() + direction][right.getColumn()] = true;
            }
        }

        return mat;
    }


}
