package org.example.Day19;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        line = br.readLine();
        List<String> patterns = List.of(line.split(", "));
        br.readLine();
        long result = 0;
        Map<String, Boolean> cache = new HashMap<>();
        while ((line = br.readLine()) != null) {
            if (isFeasible(line, patterns, cache)) {
                result++;
            }
        }
        return result;
    }

    private boolean isFeasible(String line, List<String> patterns, Map<String, Boolean> cache) {
        if (cache.containsKey(line)) {
            return cache.get(line);
        }
        boolean result = false;
        for (String pattern : patterns) {
            if (line.startsWith(pattern)) {
                if(pattern.length() == line.length()) {
                    cache.put(line, true);
                    return true;
                }
                result = isFeasible(line.substring(pattern.length()), patterns, cache);
                if (result) {
                    break;
                }
            }
        }
        cache.put(line, result);
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        line = br.readLine();
        List<String> patterns = List.of(line.split(", "));
        br.readLine();
        long result = 0;
        Map<String, Long> cache = new HashMap<>();
        while ((line = br.readLine()) != null) {
            result += calculateNumberOfSolutions(line, patterns, cache);
        }
        return result;
    }

    private long calculateNumberOfSolutions(String line, List<String> patterns, Map<String, Long> cache) {
        if (cache.containsKey(line)) {
            return cache.get(line);
        }
        long result = 0L;
        for (String pattern : patterns) {
            if (line.startsWith(pattern)) {
                if(pattern.length() == line.length()) {
                    result++;
                } else {
                    result += calculateNumberOfSolutions(line.substring(pattern.length()), patterns, cache);
                }
            }
        }
        cache.put(line, result);
        return result;
    }
}
