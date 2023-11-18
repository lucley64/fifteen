package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Move {
    Move previous;
    Grid actual;
    int g;
    int h;
    int f;

    Move up;
    Move down;
    Move left;
    Move right;

    public Move(Grid actual, int g) {
        this.actual = actual;
        this.g = g;
        h = h(actual);
        f = f(actual);
        previous = null;
    }

    public Move(Grid actual, int g, Move previous) {
        this(actual, g);
        this.previous = previous;
    }

    public Move up() {
        if (up == null) {
            if (actual.zeroX < actual.size - 1) {
                up = new Move(new Grid(actual.exchange(actual.zeroX, actual.zeroY, actual.zeroX + 1, actual.zeroY)), (actual.g + 1), this);
            } else {
                up = none;
            }
        }
        return up;
    }

    public Move down() {
        if (down == null) {

            if (actual.zeroX > 0) {
                down = new Move(new Grid(actual.exchange(actual.zeroX, actual.zeroY, actual.zeroX - 1, actual.zeroY)), (actual.g + 1), this);
            } else {
                down = none;
            }
        }
        return down;
    }

    public Move left() {
        if (left == null) {
            if (actual.zeroY < actual.size - 1) {
                left = new Move(new Grid(actual.exchange(actual.zeroX, actual.zeroY, actual.zeroX, actual.zeroY + 1)), (actual.g + 1), this);
            } else {
                left = none;
            }
        }
        return left;
    }

    public Move right() {
        if (right == null) {
            if (actual.zeroY > 0) {
                right = new Move(new Grid(actual.exchange(actual.zeroX, actual.zeroY, actual.zeroX, actual.zeroY - 1)), (actual.g + 1), this);
            } else {
                right = none;
            }
        }
        return right;
    }

    public static int h(Grid grid) {
        return manhattanDistance(grid) + 2 * linearConflict(grid);
    }

    public int f(Grid grid) {
        return g + h(grid);
    }

    public static int manhattanDistance(@NotNull Grid grid) {

        int total = 0;

        for (int i = 0; i < grid.size; i++) {
            for (int j = 0; j < grid.size; j++) {
                if (grid.tiles.get(i).get(j) != 0 && grid.tiles.get(i).get(j) != i * grid.size + j + 1) {
                    total += Math.abs(((grid.tiles.get(i).get(j) - 1) % grid.size) - j) + Math.abs(((grid.tiles.get(i).get(j) - 1) / grid.size) - i);
                }
            }
        }

        return total;
    }

    public static int linearConflict(@NotNull Grid grid) {
        int total = 0;

        Map<Integer, Integer> pairs = new HashMap<>();

        for (int i = 0; i < grid.size; i++) {
            for (int j = 0; j < grid.size; j++) {
                int actual = grid.tiles.get(i).get(j);
                if (actual != 0) {
                    if ((actual - 1) % grid.size == j && (actual - 1) / grid.size != i) {
                        for (int k = 0; k < grid.size; k++) {
                            int other = grid.tiles.get(k).get(j);
                            if (other != 0 && other != actual && (!pairs.containsKey(other) || pairs.get(other) != actual) && (other - 1) % grid.size == j && i - k > 0 && k - ((actual - 1) / grid.size) > 0) {
                                total++;
                                pairs.put(actual, other);
                            }
                        }
                    } else if ((actual - 1) / grid.size == i && (actual - 1) % grid.size != j) {
                        for (int k = 0; k < grid.size; k++) {
                            int other = grid.tiles.get(i).get(k);
                            if (other != 0 && other != actual && (!pairs.containsKey(other) || pairs.get(other) != actual) && (other - 1) / grid.size == i && j - k > 0 && k - ((actual - 1) % grid.size) > 0) {
                                total++;
                                pairs.put(actual, other);
                            }
                        }
                    }
                }
            }
        }

        return total;
    }

    private Move() {
        f = Integer.MAX_VALUE;
    }

    public static Move none = new Move();
}
