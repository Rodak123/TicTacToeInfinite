package com.rodak.tictactoeinfinite.lib.Game;

import com.rodak.tictactoeinfinite.lib.Data.Piece;

public class Turn {
    public static final boolean CIRCLE = true;
    public static final boolean CROSS = false;

    public static byte getPiece(boolean turn) {
        return turn ? Piece.CIRCLE : Piece.CROSS;
    }
}
