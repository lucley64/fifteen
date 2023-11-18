package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Grid grid;
        do {
//            grid =  new Grid(3);
            grid =  new Grid(List.of(List.of(4, 3, 8), List.of(1, 6, 2), List.of(5, 7, 0)));
        } while (!grid.isDoable());

        Move start = new Move(grid, 0);
        System.out.println(start.actual);
        System.out.println(start.actual.g);
        System.out.println(start.h);
        System.out.println(start.f);

        while (!start.actual.equals(Grid.finalState(3))){
            start = AStarGridSolver.doBestMove(start);
            System.out.println(start.actual);
            System.out.println(start.actual.g);
            System.out.println(start.h);
            System.out.println(start.f);
        }
    }
}