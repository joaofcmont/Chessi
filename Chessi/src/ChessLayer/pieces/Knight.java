package ChessLayer.pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }
    @Override
    public String toString(){
        return "Kn";
    }

    @Override
    public boolean[][] PossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(position.getRow(), position.getColumn());

        // Possible Knight moves
        int[][] moves = {
                {-2, -1}, {-2, 1}, // Move two up and one left/right
                {2, -1}, {2, 1},   // Move two down and one left/right
                {-1, -2}, {-1, 2}, // Move one up and two left/right
                {1, -2}, {1, 2}    // Move one down and two left/right
        };

        for (int[] move : moves) {
            p.setValues(p.getRow() + move[0], p.getColumn() + move[1]);
            if (getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p) || isThereOpponentPiece(p))) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }


        return mat;
    }
}
