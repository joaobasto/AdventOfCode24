package org.example.Day5;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day5Solver extends AbstractSolver {

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        Map<Integer, List<Integer>> successorsByPage = new HashMap<>();
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            String[] numbersAsStrings = line.split("\\|");
            List<Integer> numbers = Arrays.stream(numbersAsStrings)
                    .map(Integer::valueOf).collect(Collectors.toList());
            successorsByPage.computeIfAbsent(numbers.get(0), k -> new ArrayList<>()).add(numbers.get(1));
        }

        long result = 0;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            String[] numbersAsStrings = line.split(",");
            List<Integer> pageNumbers = Arrays.stream(numbersAsStrings)
                    .map(Integer::valueOf).collect(Collectors.toList());
            if (isValidOrdering(pageNumbers, successorsByPage)) {
                result += pageNumbers.get(pageNumbers.size()/2);
            }
        }

        return result;
    }

    private boolean isValidOrdering(List<Integer> pageNumbers, Map<Integer, List<Integer>> successorsByPage) {
        Set<Integer> seenPageNumbers = new HashSet<>();
        for (Integer pageNumber: pageNumbers) {
            for (Integer successorPageNumber: successorsByPage.getOrDefault(pageNumber, new ArrayList<>())) {
                if (seenPageNumbers.contains(successorPageNumber)) {
                    return false;
                }
            }
            seenPageNumbers.add(pageNumber);
        }
        return true;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        return result;
    }
}
