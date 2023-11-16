package org.example;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class Grid {
    int size;

    List<List<Integer>> tiles;

    int zeroIndex;


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
                if (index < integers.size()) {
                    int tile = integers.remove(index);
                    row.add(tile);
                    if (tile == 0) {
                        zeroIndex = i;
                    }
                }
            }
            tiles.add(row);
        }
    }

    public Grid(@NotNull List<List<Integer>> tiles){
        this.tiles = tiles;
        size = tiles.size();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles.get(i).get(j) == 0){zeroIndex = i;}
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

        int zeroIndexBottom = zeroIndex + size - 1;


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
}
