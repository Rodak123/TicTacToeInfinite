package com.rodak.tictactoeinfinite.lib.Data;

import com.rodak.tictactoeinfinite.lib.Game.Turn;

public class Game {

    private boolean turn;

    private final Board board;
    private Board lastCell;

    public Game(boolean startingTurn, Board board) {
        this.turn = startingTurn;
        lastCell = null;
        this.board = board;
    }

    public boolean nextTurn() {
        return !turn;
    }

    public boolean playTurnAt(Board cell) {
        if (!canPlayAt(cell)) return false;
        placePieceAt(cell);
        updateCellsParent(cell);
        return true;
    }

    private void updateCellsParent(Board cell) {
        Board parent = cell.parent;
        if (parent == null || parent.isDone()) return;
        parent.updateWin();
    }

    public boolean canPlayAt(Board cell) {
        if (cell == null || cell.isDone()) return false;

        if (lastCell != null) {
            Board parent = cell.parent;
            if (parent == null || parent.parent == null) return true;
            return lastCell.parent.isDone() || parent.x == lastCell.x && parent.y == lastCell.y;
        }
        return true;
    }

    private void placePieceAt(Board cell) {
        cell.placePiece(Turn.getPiece(turn));
        lastCell = cell;
        turn = nextTurn();
    }

}
