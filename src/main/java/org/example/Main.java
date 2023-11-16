package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Grid grid = new Grid(2);
        Grid grid = new Grid(List.of(List.of(1, 8, 2), List.of(0, 4, 3), List.of(7, 6, 5)));


        System.out.println(grid);
        System.out.println(grid.isDoable());
        System.out.println(Arrays.toString(grid.toArray()));
    }
}