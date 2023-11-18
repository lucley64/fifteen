package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class AStarGridSolver {

    Move actual;

    public AStarGridSolver(Move actual) {
        this.actual = actual;
    }

    public static @NotNull Move doBestMove(@NotNull Move move) {
        Move up = move.up();
        Move down = move.down();
        Move left = move.left();
        Move right = move.right();

        Move best = up;
        best = down == Move.none || best != null && best.f < down.f ? best : down;
        best = left == Move.none  || best != null && best.f < left.f ? best : left;
        best = right == Move.none  || best != null && best.f < right.f ? best : right;


        if (move.f < best.f && move.previous != null) {

            if (move.actual.equals(move.previous.left.actual)){
                move.previous.left = Move.none;
            } else if (move.actual.equals(move.previous.right.actual)){
                move.previous.right = Move.none;
            } else if (move.actual.equals(move.previous.up.actual)){
                move.previous.up = Move.none;
            } else {
                move.previous.down = Move.none;
            }


            return doBestMove(move);
        } else {
            return best;
        }

    }

}
