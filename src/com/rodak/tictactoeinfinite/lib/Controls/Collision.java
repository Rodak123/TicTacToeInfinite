package com.rodak.tictactoeinfinite.lib.Controls;

import processing.core.PApplet;

public class Collision {

    private static boolean overRect(float px, float py, float x, float y, float w, float h) {
        return (px > x && px < x + w) &&
                (py > y && py < y + h);
    }

    public static boolean mouseOverSquare(PApplet sketch, float x, float y, float width) {
        return overRect(sketch.mouseX, sketch.mouseY, x, y, width, width);
    }
}
