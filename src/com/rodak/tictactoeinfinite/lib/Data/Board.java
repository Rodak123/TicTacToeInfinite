package com.rodak.tictactoeinfinite.lib.Data;

public class Board {

    public final Board parent;
    private final Board[] cells;
    public final int x;
    public final int y;
    public final int w;
    public final int depth;
    public final int maxDepth;

    private boolean completed;
    private byte piece;


    public Board(int w, int maxDepth) {
        this(w, 0, maxDepth, 0, 0, null);
    }

    private Board(int w, int depth, int maxDepth, int x, int y, Board parent) {
        this.maxDepth = maxDepth;
        this.depth = depth;
        this.w = w;
        this.x = x;
        this.y = y;
        this.parent = parent;
        piece = Piece.EMPTY;

        completed = depth >= maxDepth;
        if (completed) {
            cells = null;
        } else {
            cells = new Board[w * w];
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < w; j++) {
                    cells[i + j * w] = new Board(w, depth + 1, maxDepth, i, j, this);
                }
            }
        }
    }

    public boolean isDone() {
        return completed && piece != Piece.EMPTY;
    }

    public boolean isCompleted() {
        return completed;
    }

    public byte getPiece() {
        return piece;
    }

    public Board getCell(int i, int j) {
        if (completed || cells == null) return null;
        int index = i + j * w;
        return cells[index];
    }

    public void placePiece(byte piece) {
        completed = true;
        this.piece = piece;
    }

    public void updateWin() {
        byte winner = getWinner();
        if (winner == Piece.EMPTY) return;
        placePiece(winner);
        if (parent == null) return;
        parent.updateWin();
    }

    public byte getWinner() {
        if (cells == null || completed) return piece;

        byte winner = getDiagonalWinner();
        if (winner != Piece.EMPTY) {
            return winner;
        }

        winner = getHorizontalWinner();
        if (winner != Piece.EMPTY) {
            return winner;
        }

        return getVerticalWinner();
    }

    private byte getVerticalWinner() {
        byte winner = Piece.EMPTY;
        for (int i = 0; i < w; i++) {
            winner = pieceAt(i, 0);
            for (int j = 0; j < w; j++) {
                byte current = pieceAt(i, j);
                if (current != winner) {
                    winner = Piece.EMPTY;
                    break;
                }
            }
            if (winner != Piece.EMPTY) break;
        }
        return winner;
    }

    private byte getHorizontalWinner() {
        byte winner = Piece.EMPTY;
        for (int j = 0; j < w; j++) {
            winner = pieceAt(0, j);
            for (int i = 0; i < w; i++) {
                byte current = pieceAt(i, j);
                if (current != winner) {
                    winner = Piece.EMPTY;
                    break;
                }
            }
            if (winner != Piece.EMPTY) break;
        }
        return winner;
    }

    private byte getDiagonalWinner() {
        byte winner, current;
        int i = 0, j = 0;

        // \
        //   \
        //     \
        winner = pieceAt(i, j);
        if (winner != Piece.EMPTY) {
            do {
                i++;
                j++;
                if (i >= w || j >= w) break;
                current = pieceAt(i, j);
                if (current != winner) {
                    winner = Piece.EMPTY;
                    break;
                }
            }
            while (true);
            if (winner != Piece.EMPTY) return winner;
        }

        //     /
        //   /
        // /
        i = w - 1;
        j = 0;
        winner = pieceAt(i, j);
        if (winner != Piece.EMPTY) {
            do {
                i--;
                j++;
                if (i < 0 || j >= w) break;
                current = pieceAt(i, j);
                if (current != winner) {
                    winner = Piece.EMPTY;
                    break;
                }
            }
            while (true);
        }
        return winner;
    }

    private byte pieceAt(int i, int j) {
        assert cells != null : "Cells must not be null";
        Board cell = cells[i + j * w];
        if (!cell.isDone()) return Piece.EMPTY;
        return cell.piece;
    }
}
