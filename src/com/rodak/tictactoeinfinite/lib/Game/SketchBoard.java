package com.rodak.tictactoeinfinite.lib.Game;

import com.rodak.tictactoeinfinite.lib.Controls.Collision;
import com.rodak.tictactoeinfinite.lib.Data.Board;
import com.rodak.tictactoeinfinite.lib.Data.Game;
import com.rodak.tictactoeinfinite.lib.Showing.Palette;
import com.rodak.tictactoeinfinite.lib.Showing.PieceShower;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class SketchBoard {

    private final PApplet sketch;

    public final float width;
    public final float x;
    public final float y;

    public final Board board;

    private final SketchBoard[] sketchCells;

    public SketchBoard(PApplet sketch, float x, float y, float width, Board board) {
        this.sketch = sketch;
        this.width = width;
        this.x = x;
        this.y = y;
        this.board = board;

        if (board.isCompleted()) {
            sketchCells = null;
        } else {
            sketchCells = new SketchBoard[board.w * board.w];
            float cellW = width / board.w;
            for (int i = 0; i < board.w; i++) {
                for (int j = 0; j < board.w; j++) {
                    Board cell = board.getCell(i, j);
                    float cellX = x + cellW * i;
                    float cellY = y + cellW * j;
                    sketchCells[i + j * board.w] = new SketchBoard(sketch, cellX, cellY, cellW, cell);
                }
            }
        }
    }

    public void show() {
//        if (Collision.mouseOverSquare(sketch, x, y, width)) {
//            sketch.rectMode(PConstants.CORNER);
//            sketch.fill(Palette.GridOverHighlight);
//            sketch.noStroke();
//            sketch.rect(x, y, width, width);
//        }
        sketch.rectMode(PConstants.CORNER);
        sketch.noFill();
        sketch.stroke(Palette.Grid);
        sketch.strokeWeight(PApplet.map(board.depth, 0, board.maxDepth + 0.5f, 6, 0));
        sketch.rect(x, y, width, width);
        if (board.isCompleted()) {
            byte piece = board.getPiece();
            PieceShower.showPiece(sketch, piece, x, y, width);
            return;
        }
        for (SketchBoard sketchBoard : sketchCells) {
            sketchBoard.show();
        }
    }

    public void showHighlight() {
        sketch.rectMode(PConstants.CORNER);
        sketch.fill(Palette.GridHighlight);
        sketch.noStroke();
        sketch.rect(x, y, width, width);
    }

    public Board getCellOverMouse() {
        if (!Collision.mouseOverSquare(sketch, x, y, width)) {
            return null;
        }
        if (board.isCompleted()) return board;
        for (SketchBoard sketchBoard : sketchCells) {
            Board over = sketchBoard.getCellOverMouse();
            if (over != null) {
                return over;
            }
        }
        return null;
    }

    public void addPlayableCells(ArrayList<SketchBoard> playable, Game game) {
        if (board.isDone()) return;
        if (board.isCompleted()) {
            if (game.canPlayAt(board)) {
                playable.add(this);
            }
        } else {
            for (SketchBoard sketchBoard : sketchCells) {
                sketchBoard.addPlayableCells(playable, game);
            }
        }
    }
}
