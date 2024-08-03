package BoardLayer;


public class Piece {

    protected Position position;
    private Board board;

    public Piece(){}

    public Board getBoard() {
        return board;
    }

    public Piece(Position position, Board board) {
        this.position = position;
        this.board = board;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    //implement
    public boolean[][] possibleMoves(){

    }
    //implement

    public boolean possibleMove(Position position){
        return true;
    }
    //implement

    public boolean isThereAnyPossibleMove(){
        return true;
    }

}
