package org.example;

import org.example.Day1.Day1Solver;

import java.io.*;

public abstract class AbstractSolver implements Solver {

    public void solveExercise1() throws IOException {
        solveExercise(1);
    }

    public void solveExercise2() throws IOException {
        solveExercise(2);
    }

    public String getSolverName() {
        return this.getClass().getSimpleName();
    }

    public void solveExercise(int challengeNumber) throws IOException {
        System.out.println("Using " + getSolverName() + "for Challenge "
                + String.valueOf(challengeNumber) + " : ");

        ClassLoader classLoader = Day1Solver.class.getClassLoader();
        File file = new File(classLoader.getResource("input" + getSolverName() + ".txt").getFile());

        BufferedReader br
                = new BufferedReader(new FileReader(file));

        String line;
        int result = 0;
        switch (challengeNumber) {
            case 1:
                result = createSolutionExercise1(br);
                break;
            case 2:
                result = createSolutionExercise2(br);
                break;
        }

        System.out.println("The result is: " + result);
    }

    protected abstract int createSolutionExercise1(BufferedReader br) throws IOException;

    protected abstract int createSolutionExercise2(BufferedReader br) throws IOException;


}
