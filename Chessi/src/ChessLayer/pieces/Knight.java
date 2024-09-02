package ChessLayer.pieces;

import BoardLayer.Board;
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
        return mat;
    }
}
