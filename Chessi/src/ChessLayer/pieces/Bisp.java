package ChessLayer.pieces;

import BoardLayer.Board;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Bisp extends ChessPiece {
    public Bisp(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "B";
    }
}
