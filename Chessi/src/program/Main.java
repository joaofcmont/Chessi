package program;

import ChessLayer.ChessException;
import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.ChessPosition;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ChessMatch chessMatch = new ChessMatch();

        LauchPage launchPage = new LauchPage();
        SwingUtilities.invokeLater(LauchPage::new);

    }

}