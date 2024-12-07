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
        System.out.println("Using " + getSolverName() + " for Challenge "
                + challengeNumber + " : ");

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("input" + getSolverName() + ".txt");
        BufferedReader br
                = new BufferedReader(new InputStreamReader(inputStream));

        long result = 0;
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

    protected abstract long createSolutionExercise1(BufferedReader br) throws IOException;

    protected abstract long createSolutionExercise2(BufferedReader br) throws IOException;


}
