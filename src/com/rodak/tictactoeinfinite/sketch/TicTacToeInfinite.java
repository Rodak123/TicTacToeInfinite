package com.rodak.tictactoeinfinite.sketch;

import com.rodak.tictactoeinfinite.lib.Data.Board;
import com.rodak.tictactoeinfinite.lib.Data.Game;
import com.rodak.tictactoeinfinite.lib.Game.Turn;
import com.rodak.tictactoeinfinite.lib.Showing.Palette;
import com.rodak.tictactoeinfinite.lib.Game.SketchBoard;
import processing.core.PApplet;

import java.util.ArrayList;

public class TicTacToeInfinite extends PApplet {

    private static final int DEPTH = 2; // This variable controls how many sub grids will be in each cell

    public static void main(String[] args) {
        String[] sketchArgs = {"TicTacToe Infinite"};
        TicTacToeInfinite sketch = new TicTacToeInfinite();
        PApplet.runSketch(sketchArgs, sketch);
    }

    public void settings() {
        size(1200, 1200);
    }

    private SketchBoard sketchBoard;
    private Game game;

    private final ArrayList<SketchBoard> playable = new ArrayList<>();

    public void setup() {
        Board board = new Board(3, DEPTH);

        sketchBoard = new SketchBoard(this, 0, 0, width - 1, board);
        game = new Game(Turn.CIRCLE, board);

        updatePlayableCells();
    }

    private void updatePlayableCells() {
        playable.clear();
        sketchBoard.addPlayableCells(playable, game);
    }

    public void draw() {
        background(Palette.Background);

        sketchBoard.show();

        for (SketchBoard cell : playable) {
            cell.showHighlight();
        }
    }

    public void mouseReleased() {
        Board cell = sketchBoard.getCellOverMouse();
        boolean played = game.playTurnAt(cell);
        if (played) {
            updatePlayableCells();
        }
    }

}
