package ChessLayer;

import BoardLayer.Board;
import BoardLayer.Piece;
import BoardLayer.Position;

public abstract class ChessPiece extends Piece {

    private Color color;


    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected boolean isThereOpponentPiece(Position position){
        //Chess piece of the piece on the position "position"
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece != null && piece.getColor()!= this.color;
    }

    public abstract boolean[][] possibleMoves();
}
