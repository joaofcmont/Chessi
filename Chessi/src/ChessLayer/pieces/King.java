package ChessLayer.pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }
    @Override
    public String toString(){
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(position.getRow(),position.getColumn());

        //check above
        p.setValues(position.getRow()-1,position.getColumn());
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow()-1);
        }

        //check down
        p.setValues(position.getRow()+1,position.getColumn());
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow()+1);
        }
        //check left
        p.setValues(position.getRow(),position.getColumn()-1);
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn()-1);
        }

        //check right
        p.setValues(position.getRow(),position.getColumn()+1);
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn()+1);
        }

        //check diagonal top-left
        p.setValues(position.getRow()-1,position.getColumn()-1);
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow()-1, p.getColumn()-1);
        }

        //diagonal top-right
        p.setValues(position.getRow()-1,position.getColumn()+1);
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow()-1, p.getColumn()+1);
        }


        //check diagonal down-right
        p.setValues(position.getRow()+1,position.getColumn()+1);
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow()+1, p.getColumn()+1);
        }

        //check diagonal down-left
        p.setValues(position.getRow()+1,position.getColumn()-1);
        if(getBoard().positionExists(p) && (!getBoard().thereIsAPiece(p)||isThereOpponentPiece(p) )){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow()+1, p.getColumn()-1);
        }

        //special move to Rook

        return mat;
    }


}
