package org.example;

import org.example.Day9.Day9Solver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        Solver solver = new Day9Solver();
        solver.solveExercise1();
        solver.solveExercise2();
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Application took " + duration + " milliseconds.");
    }
}