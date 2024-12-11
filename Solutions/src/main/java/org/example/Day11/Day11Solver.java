package org.example.Day11;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day11Solver extends AbstractSolver {

    private static final long N_BLINKS = 25;
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line = br.readLine();

        String[] numbersAsStrings = line.split("\\s+");
        List<Long> numbers = Arrays.stream(numbersAsStrings)
                .map(Long::valueOf).collect(Collectors.toList());

        for (long i = 0; i < N_BLINKS; i++) {
            processBlink(numbers);
            System.out.println("Processed blink number " + (i + 1));
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

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        Map<CacheKey, List<Long>> cache = new HashMap<>();
        String line = br.readLine();

        String[] numbersAsStrings = line.split("\\s+");
        List<Element> elements = Arrays.stream(numbersAsStrings)
                .map(Long::valueOf)
                .map(value -> new Element(value, 0))
                .collect(Collectors.toList());

        while (!isFinished(elements)) {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getBlinkStage() == N_BLINKS) {
                    continue;
                }
                //TODO try to advance using cache

                if (elements.get(i).getValue() == 0L) {
                    elements.get(i).setValue(1L);
                    elements.get(i).setBlinkStage(elements.get(i).getBlinkStage() + 1);
                } else {
                    long numberOfDigits = (long) (Math.log10(elements.get(i).getValue()) + 1);
                    if (numberOfDigits% 2 == 0) {
                        long halfOfNumberOfDigits = numberOfDigits / 2;
                        long firstNumber = elements.get(i).getValue() / (long) Math.pow(10, halfOfNumberOfDigits);
                        long secondNumber = elements.get(i).getValue() % (long) Math.pow(10, halfOfNumberOfDigits);
                        elements.get(i).setValue(secondNumber);
                        elements.get(i).setBlinkStage(elements.get(i).getBlinkStage() + 1);
                        elements.add(i, new Element(firstNumber, elements.get(i).getBlinkStage()));
                        i++;
                    } else {
                        elements.get(i).setValue(elements.get(i).getValue() * 2024);
                        elements.get(i).setBlinkStage(elements.get(i).getBlinkStage() + 1);
                    }
                }
            }
        }

        return elements.size();
    }

    private boolean isFinished(List<Element> elements) {
        return elements.stream().allMatch(element -> element.getBlinkStage() == N_BLINKS);
    }


}
