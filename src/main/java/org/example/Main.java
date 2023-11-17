package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Grid grid;
        do {
            grid =  new Grid(3);
        } while (!grid.isDoable());

        System.out.println(grid);
        System.out.println(grid.isDoable());
        System.out.println(Arrays.toString(grid.toArray()));
    }
}