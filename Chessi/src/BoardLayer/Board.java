package BoardLayer;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int rows;
    private int columns;
    List<Piece> pieceList = new ArrayList<>();


    public Board(){}

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    public void setPieceList(List<Piece> pieceList) {
        this.pieceList = pieceList;
    }

}
