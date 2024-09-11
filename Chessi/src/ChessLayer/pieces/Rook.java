package ChessLayer.pieces;

import BoardLayer.Board;
import BoardLayer.Piece;
import BoardLayer.Position;
import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Rook extends ChessPiece {

    private ChessMatch chessMatch;

    public Rook(Board board, Color color,ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString(){
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(position.getRow(), position.getColumn());

        //check above
        p.setValues(position.getRow()-1,position.getColumn());
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow()-1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        //check down
        p.setValues(position.getRow()+1,position.getColumn());
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow()+1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        //check left
        p.setValues(position.getRow(),position.getColumn()-1);
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn()-1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        //check right
        p.setValues(position.getRow(),position.getColumn()+1);
        while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn()+1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Add castling moves if the rook has not moved yet and if the king is in the same row
        if (getMoveCount() == 0) {
            int row = position.getRow();
            // Kingside castling
            Position kingsideKingPosition = new Position(row, position.getColumn() + 3);
            if (chessMatch.canCastle(position, kingsideKingPosition)) {
                mat[row][position.getColumn() + 2] = true;
            }

            // Queenside castling
            Position queensideKingPosition = new Position(row, position.getColumn() - 4);
            if (chessMatch.canCastle(position, queensideKingPosition)) {
                mat[row][position.getColumn() - 2] = true;
            }else{
                return null;
            }
        }


        return mat;
    }
}
