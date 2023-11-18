package org.example;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;

public class Grid {
    int size;

    List<List<Integer>> tiles;

    int zeroX;
    int zeroY;

    int g = 0;

    public Grid(int size) {
        this.size = size;
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            integers.add(i);
        }

        tiles = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                int index = random.nextInt(integers.size());
                int tile = integers.remove(index);
                row.add(tile);
                if (tile == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
            tiles.add(row);
        }
    }

    public Grid(@NotNull List<List<Integer>> tiles) {
        this.tiles = tiles;
        size = tiles.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles.get(i).get(j) == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }
    }

    boolean isDoable() {

        int[] ints = toArray();
        int inverseCount = 0;
        for (int i = 0; i < ints.length; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if (ints[i] != 0 && ints[j] != 0 && ints[i] > ints[j]) {
                    inverseCount++;
                }
            }
        }

        int zeroIndexBottom = (size + 1) - (zeroX + 1);


        return size % 2 != 0 && inverseCount % 2 == 0 || size % 2 == 0 && (zeroIndexBottom % 2 == 0 && inverseCount % 2 != 0 || zeroIndexBottom % 2 != 0 && inverseCount % 2 == 0);
    }

    public int[] toArray() {
        return tiles.stream().flatMapToInt(integers -> integers.stream().flatMapToInt(IntStream::of)).toArray();
    }


    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n");

        tiles.forEach(row -> {
            StringJoiner stringJoiner1 = new StringJoiner("|");
            row.forEach(integer -> stringJoiner1.add(String.valueOf(integer)));
            stringJoiner.add("|" + stringJoiner1 + "|");
        });


        return stringJoiner.toString();
    }

    public @NotNull List<List<Integer>> exchange(int iaX, int iaY, int ibX, int ibY) {
        var aX = new ArrayList<>(tiles.get(iaX));
        var aY = aX.get(iaY);
        var bX = tiles.get(iaX) == tiles.get(ibX) ? aX : new ArrayList<>(tiles.get(ibX));
        var bY = bX.get(ibY);

        aX.set(iaY, bY);
        bX.set(ibY, aY);

        var tiles2 = new ArrayList<>(tiles);
        tiles2.set(iaX, aX);
        tiles2.set(ibX, bX);

        return tiles2;
    }


    @Contract("_ -> new")
    public static @NotNull Grid finalState(int size) {
        List<List<Integer>> finalState = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            finalState.add(new ArrayList<>());
            for (int j = 0; j < size; j++) {
                finalState.get(i).add(i * size + j + 1);
            }
        }

        finalState.getLast().set(size - 1, 0);
        return new Grid(finalState);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return Objects.equals(tiles, grid.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiles);
    }
}
