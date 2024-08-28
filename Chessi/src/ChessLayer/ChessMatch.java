package ChessLayer;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.pieces.King;
import ChessLayer.pieces.Rook;

public class ChessMatch {

    private final Board board;

    public ChessMatch(){
        board = new Board(8,8);
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

    public void initialSetup(){
        board.placePiece(new Rook(board,Color.WHITE), new Position(2,1));
        board.placePiece(new King(board,Color.BLACK), new Position(1,4));
        board.placePiece(new King(board,Color.WHITE), new Position(7,4));

    }
}
