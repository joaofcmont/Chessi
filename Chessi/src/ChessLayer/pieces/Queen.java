package ChessLayer.pieces;

import BoardLayer.Board;
import BoardLayer.Position;
import ChessLayer.ChessPiece;
import ChessLayer.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        // Check if position is initialized
        if (position == null) {
            System.out.println("Error: Queen position is null.");
            return mat;
        }

        Position p = new Position(0, 0);

        // Define move directions (dx, dy)
        int[][] directions = {
                {-1, 0}, // Up
                {1, 0},  // Down
                {0, -1}, // Left
                {0, 1},  // Right
                {-1, -1}, // Diagonal top-left
                {-1, 1},  // Diagonal top-right
                {1, 1},   // Diagonal bottom-right
                {1, -1}   // Diagonal bottom-left
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int x = position.getRow();
            int y = position.getColumn();

            // Move in the given direction
            while (true) {
                x += dx;
                y += dy;
                p.setValues(x, y);

                if (!getBoard().positionExists(p)) {
                    break;
                }

                if (getBoard().thereIsAPiece(p)) {
                    if (isThereOpponentPiece(p)) {
                        mat[p.getRow()][p.getColumn()] = true;
                    }
                    break;
                }

                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }
}
