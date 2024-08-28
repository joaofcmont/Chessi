package ChessLayer;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.pieces.King;
import ChessLayer.pieces.Pawn;
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

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece,new ChessPosition(column,row).toPosition());
    }

    public void initialSetup(){
        placeNewPiece('b', 6 ,new Rook(board,Color.WHITE));
        placeNewPiece('a', 2 ,new Pawn(board,Color.WHITE));
        placeNewPiece('b', 2 ,new Pawn(board,Color.WHITE));
        placeNewPiece('c', 2 ,new Pawn(board,Color.WHITE));
        placeNewPiece('d', 2 ,new Pawn(board,Color.WHITE));
        placeNewPiece('e', 2 ,new Pawn(board,Color.WHITE));
        placeNewPiece('f', 2 ,new Pawn(board,Color.WHITE));
        placeNewPiece('g', 2 ,new Pawn(board,Color.WHITE));
        placeNewPiece('h', 2 ,new Pawn(board,Color.WHITE));

        placeNewPiece('a', 7 ,new Pawn(board,Color.BLACK));
        placeNewPiece('b', 7 ,new Pawn(board,Color.BLACK));
        placeNewPiece('c', 7 ,new Pawn(board,Color.BLACK));
        placeNewPiece('d', 7 ,new Pawn(board,Color.BLACK));
        placeNewPiece('e', 7 ,new Pawn(board,Color.BLACK));
        placeNewPiece('f', 7 ,new Pawn(board,Color.BLACK));
        placeNewPiece('g', 7 ,new Pawn(board,Color.BLACK));
        placeNewPiece('h', 7 ,new Pawn(board,Color.BLACK));


    }
}
