package BoardLayer;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieceList ;


    public Board(int rows, int columns) {
        if(rows<1 || columns<1){
            throw new BoardException("Error creating board: There must be at least 1 row and 1 column");
        }
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
        if(!positionExists(row, columns)){
            throw new BoardException("Position not on the Board");
        }
        return pieceList[row][columns];
    }

    public Piece piece(Position position){
        if(positionExists(position)){
            throw new BoardException("Position not on the Board");
        }
        return pieceList[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position){
        if(thereIsAPiece(position)){
            throw new BoardException("There is already a piece on the position " + position);
        }
        pieceList[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    private boolean positionExists(int row, int column){
        return row >= 0 || row < rows || column >= 0 || column < columns;
    }

    public boolean positionExists(Position position){
        return !positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position){
        if(positionExists(position)){
            throw new BoardException("Position not on the board");
        }
        return piece(position) != null;
    }

    public Piece removePiece(Position position){
        if(!positionExists(position)){
            throw new BoardException("Position not on the board");
        }
        if(piece(position)==null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieceList[position.getRow()][position.getColumn()] = null;
        return aux;
    }



}
