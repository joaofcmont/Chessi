package program;

import BoardLayer.Position;
import ChessLayer.ChessException;
import ChessLayer.ChessMatch;
import ChessLayer.ChessPiece;
import ChessLayer.ChessPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class NewWindow {

    JFrame frame = new JFrame();
    JPanel boardPanel = new JPanel(new GridLayout(8, 8));
    JButton[][] boardButtons = new JButton[8][8];
    private ChessMatch chessMatch;

    NewWindow() {
        chessMatch = new ChessMatch();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 480);
        frame.setLayout(new BorderLayout());

        boardPanel = new JPanel(new GridLayout(8, 8));
        boardButtons = new JButton[8][8];

        initializeBoard();

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    private void initializeBoard() {
        // Define the colors for the board
        Color lightSquareColor = new Color(238, 238, 210); // Light green-beige
        Color darkSquareColor = new Color(118, 150, 86);   // Dark green

        // Set up the chessboard with the specified colors
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(64, 64));
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setOpaque(true);
                button.setBackground((row + col) % 2 == 0 ? lightSquareColor : darkSquareColor);

                final int finalRow = row;
                final int finalCol = col;
                button.addActionListener(e -> handleButtonClick(finalRow, finalCol));

                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }
        updateBoardGUI();
    }

    private void updateBoardGUI() {
        ChessPiece[][] pieces = chessMatch.getPieces();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = pieces[row][col];
                if (piece != null) {
                    setPieceIcon(row, col, getPieceImageName(piece));
                } else {
                    boardButtons[row][col].setIcon(null);
                }
            }
        }
    }
    private String getPieceImageName(ChessPiece piece) {
        String color = piece.getColor().equals(ChessLayer.Color.WHITE) ? "white" : "black";
        String pieceName = piece.getClass().getSimpleName().toLowerCase();
        return color + "-" + pieceName;
    }

    private void setPieceIcon(int row, int col, String pieceName) {
        JButton button = boardButtons[row][col];
        String fileName = "images/" + pieceName + ".png";
        ImageIcon icon = null;

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
            if (is != null) {
                byte[] imageBytes = is.readAllBytes();
                ImageIcon originalIcon = new ImageIcon(imageBytes);
                Image image = originalIcon.getImage();

                // Scale the image to fit the button
                int buttonSize = Math.min(button.getWidth(), button.getHeight());
                if (buttonSize <= 0) buttonSize = 60; // Default size if button dimensions are not set
                Image scaledImage = image.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);

                icon = new ImageIcon(scaledImage);
                ((InputStream) is).close();
            } else {
                System.err.println("Couldn't find file: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + fileName);
            e.printStackTrace();
        }

        if (icon != null) {
            button.setIcon(icon);
        } else {
            button.setText(pieceName); // Fallback to display piece name as text
        }
    }

    private Position selectedPosition = null;

    private void handleButtonClick(int row, int col) {
        if (selectedPosition == null) {
            selectedPosition = new Position(row, col);
            highlightPossibleMoves(row, col);
        } else {
            try {
                ChessPosition source = ChessPosition.fromPosition(selectedPosition);
                ChessPosition target = ChessPosition.fromPosition(new Position(row, col));
                chessMatch.performChessMove(source, target);
                updateBoardGUI();
            } catch (ChessException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Chess Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                clearHighlights();
                selectedPosition = null;
            }
        }
    }

    private void highlightPossibleMoves(int row, int col) {
        ChessPosition position = ChessPosition.fromPosition(new Position(row, col));
        boolean[][] possibleMoves = chessMatch.possibleMoves(position);
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (possibleMoves[r][c]) {
                    boardButtons[r][c].setBackground(Color.YELLOW);
                }
            }
        }
    }

    private void clearHighlights() {
        Color lightSquareColor = new Color(238, 238, 210);
        Color darkSquareColor = new Color(118, 150, 86);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardButtons[row][col].setBackground((row + col) % 2 == 0 ? lightSquareColor : darkSquareColor);
            }
        }
    }




}
