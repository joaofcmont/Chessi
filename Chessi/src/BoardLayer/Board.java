package BoardLayer;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieceList ;


    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieceList = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int columns){
        return pieceList[row][columns];
    }

    public Piece piece(Position position){
        return pieceList[position.getRow()][position.getColumn()];
    }

}
