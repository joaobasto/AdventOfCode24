package org.example;


import org.example.Day7.Day7Solver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        Solver solver = new Day7Solver();
        solver.solveExercise1();
        solver.solveExercise2();
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Application took " + duration + " milliseconds.");
    }
}