package org.example;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        Solver solver = new Day10Solver();
        solver.solveExercise1();
        solver.solveExercise2();
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Application took " + duration + " milliseconds.");
    }
}