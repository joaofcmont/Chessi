package program;

import ChessLayer.ChessMatch;

public class Main {
    public static void main(String[] args) {


        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());

    }
}