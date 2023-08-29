package com.rodak.tictactoeinfinite.lib.Showing;

import com.rodak.tictactoeinfinite.lib.Data.Piece;
import processing.core.PApplet;

public class PieceShower {

    public static void showPiece(PApplet sketch, byte piece, float x, float y, float size) {
        if (piece == Piece.EMPTY) return;
        sketch.pushMatrix();
        sketch.translate(x, y);
        float padding = size * 0.2f;
        if (piece == Piece.CIRCLE) {
            showCircle(sketch, size, padding);
        } else {
            showCross(sketch, size, padding);
        }
        sketch.popMatrix();
    }

    private static void showCircle(PApplet sketch, float size, float padding) {
        sketch.noFill();
        sketch.stroke(Palette.Circle);
        sketch.circle(size * 0.5f, size * 0.5f, size - padding * 2);
    }

    private static void showCross(PApplet sketch, float size, float padding) {
        sketch.noFill();
        sketch.stroke(Palette.Cross);
        float lineSize = size - padding;
        sketch.line(padding, lineSize, lineSize, padding);
        sketch.line(padding, padding, lineSize, lineSize);
    }
}
