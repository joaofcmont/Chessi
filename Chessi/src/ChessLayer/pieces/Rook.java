package ChessLayer.pieces;

import BoardLayer.Board;
import BoardLayer.Piece;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "R";
    }

    @Override
    public boolean[][] PossibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
