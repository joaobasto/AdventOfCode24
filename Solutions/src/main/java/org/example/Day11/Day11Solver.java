package org.example.Day11;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11Solver extends AbstractSolver {

    private static final long N_BLINKS = 75;
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line = br.readLine();

        String[] numbersAsStrings = line.split("\\s+");
        List<Long> numbers = Arrays.stream(numbersAsStrings)
                .map(Long::valueOf).collect(Collectors.toList());

        for (long i = 0; i < N_BLINKS; i++) {
            processBlink(numbers);
        }

        long result = numbers.size();
        return result;
    }

    private void processBlink(List<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals(0L)) {
                numbers.set(i, 1L);
            } else {
                long numberOfDigits = (long) (Math.log10(numbers.get(i)) + 1);
                if (numberOfDigits% 2 == 0) {
                    long halfOfNumberOfDigits = numberOfDigits / 2;
                    long firstNumber = numbers.get(i) / (long) Math.pow(10, halfOfNumberOfDigits);
                    long secondNumber = numbers.get(i) % (long) Math.pow(10, halfOfNumberOfDigits);
                    numbers.set(i, secondNumber);
                    numbers.add(i, firstNumber);
                    i++;
                } else {
                    numbers.set(i, numbers.get(i) * 2024);
                }
            }
        }
    }

    private List<Long> processBlink(Long number) {
        List<Long> result = new ArrayList<>();
        if (number.equals(0L)) {
            result.add(1L);
            return result;
        } else {
            long numberOfDigits = (long) (Math.log10(number) + 1);
            if (numberOfDigits% 2 == 0) {
                long halfOfNumberOfDigits = numberOfDigits / 2;
                long firstNumber = number / (long) Math.pow(10, halfOfNumberOfDigits);
                long secondNumber = number % (long) Math.pow(10, halfOfNumberOfDigits);
                result.add(firstNumber);
                result.add(secondNumber);
                return result;
            } else {
                result.add(number * 2024L);
                return result;
            }
        }
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line = br.readLine();

        String[] numbersAsStrings = line.split("\\s+");
        Map<Long, Long> countByNumber = Arrays.stream(numbersAsStrings)
                .map(Long::valueOf).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (long i = 0; i < N_BLINKS; i++) {
            Map<Long, Long> newCountByNumber = new HashMap<>();
            for (Map.Entry<Long, Long> entry : countByNumber.entrySet()) {
                List<Long> newNumbers = processBlink(entry.getKey());
                for (Long newNumber : newNumbers) {
                    newCountByNumber.merge(newNumber, entry.getValue(), Long::sum);
                }
            }
            countByNumber = newCountByNumber;
            System.out.println("Processed blink " + (i+1));
        }

        return countByNumber.values().stream().mapToLong(a -> a.longValue()).sum();
    }


}
