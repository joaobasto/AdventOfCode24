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
        String line;
        Map<Integer, List<Integer>> successorsByPage = new HashMap<>();
        Map<Integer, List<Integer>> predecessorsByPage = new HashMap<>();
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            String[] numbersAsStrings = line.split("\\|");
            List<Integer> numbers = Arrays.stream(numbersAsStrings)
                    .map(Integer::valueOf).collect(Collectors.toList());
            successorsByPage.computeIfAbsent(numbers.get(0), k -> new ArrayList<>()).add(numbers.get(1));
            predecessorsByPage.computeIfAbsent(numbers.get(1), k -> new ArrayList<>()).add(numbers.get(0));
        }

        long result = 0;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            String[] numbersAsStrings = line.split(",");
            List<Integer> pageNumbers = Arrays.stream(numbersAsStrings)
                    .map(Integer::valueOf).collect(Collectors.toList());
            if (!isValidOrdering(pageNumbers, successorsByPage)) {
                List<Integer> correctedPageNumbers = correct(pageNumbers, predecessorsByPage);
                result += correctedPageNumbers.get(pageNumbers.size()/2);
            }
        }

        return result;
    }

    private List<Integer> correct(List<Integer> pageNumbers, Map<Integer, List<Integer>> predecessorsByPage) {
        //create a map that will hold only the predecessors for the page numbers in this list
        //it will have an empty list in case the page number does not have predecessors
        Map<Integer, List<Integer>> tempPredecessorsByPage = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : predecessorsByPage.entrySet()) {
            if (pageNumbers.contains(entry.getKey())) {
                List<Integer> tempPredecessorPageNumbers = new ArrayList<>();
                for (Integer tempPageNumber : entry.getValue()) {
                    if (pageNumbers.contains(tempPageNumber)) {
                        tempPredecessorPageNumbers.add(tempPageNumber);
                    }
                }
                tempPredecessorsByPage.put(entry.getKey(), tempPredecessorPageNumbers);
            }
        }

        for (Integer pageNumber : pageNumbers) {
            if (!tempPredecessorsByPage.containsKey(pageNumber)) {
                tempPredecessorsByPage.put(pageNumber, new ArrayList<>());
            }
        }

        List<Integer> correctPageNumbers = new ArrayList<>();
        while (!tempPredecessorsByPage.isEmpty()) {
            Integer nextPageNumber = null;
            for (Integer pageNumber : tempPredecessorsByPage.keySet()) {
                if (tempPredecessorsByPage.get(pageNumber).isEmpty()) {
                    nextPageNumber = pageNumber;
                    break;
                }
            }
            correctPageNumbers.add(nextPageNumber);
            tempPredecessorsByPage.remove(nextPageNumber);
            for (Integer pageNumber : tempPredecessorsByPage.keySet()) {
                if (tempPredecessorsByPage.get(pageNumber).contains(nextPageNumber)) {
                    tempPredecessorsByPage.get(pageNumber).remove(nextPageNumber);
                }
            }
        }
        return correctPageNumbers;
    }
}
