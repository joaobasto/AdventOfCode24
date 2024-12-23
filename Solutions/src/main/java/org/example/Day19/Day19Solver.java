package org.example.Day19;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day19Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        line = br.readLine();
        List<String> patterns = List.of(line.split(", "));
        br.readLine();
        long result = 0;
        while ((line = br.readLine()) != null) {
            if (isFeasible(line, patterns)) {
                result++;
            }
            System.out.println("Line calculated");
        }
        return result;
    }

    private boolean isFeasible(String line, List<String> patterns) {
        if (line.isEmpty()) {
            return true;
        }
        boolean result = false;
        for (String pattern : patterns) {
            if (line.startsWith(pattern)) {
                if(pattern.length() == line.length()) {
                    return true;
                }
                result = isFeasible(line.substring(pattern.length()), patterns);
                if (result) {
                    break;
                }
            }
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
