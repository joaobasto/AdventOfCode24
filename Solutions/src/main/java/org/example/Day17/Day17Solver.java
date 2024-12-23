package org.example.Day17;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Day17Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long registerA = Long.parseLong(br.readLine().split(": ")[1]);
        long registerB = Long.parseLong(br.readLine().split(": ")[1]);
        long registerC = Long.parseLong(br.readLine().split(": ")[1]);
        br.readLine();
        String[] valuesString = br.readLine().split(": ")[1].split(",");
        List<Long> values = Arrays.stream(valuesString)
                .map(Long::valueOf).collect(Collectors.toList());

        List<Long> result = createOutput(registerA, registerB, registerC, values);
        System.out.println(result);
        return 0;
    }

    private List<Long> createOutput(long registerA, long registerB, long registerC, List<Long> values) {
        List<Long> result = new ArrayList<>();

        long instructionCounter = 0;
        while (true) {
            if (instructionCounter >= values.size()) {
                break;
            }
            long opcode = values.get((int) instructionCounter);
            long operand = values.get((int) instructionCounter + 1);
            long comboOperand = operand;
            if (operand == 4) {
                comboOperand = registerA;
            }
            if (operand == 5) {
                comboOperand = registerB;
            }
            if (operand == 6) {
                comboOperand = registerC;
            }
            if (opcode == 0) {
                registerA = (long) (registerA / Math.pow(2, comboOperand));
            } else if (opcode == 1) {
                registerB = registerB ^ operand;
            } else if (opcode == 2) {
                registerB = comboOperand % 8;
            } else if (opcode == 3) {
                if (registerA != 0) {
                    instructionCounter = operand;
                    continue;
                }
            } else if (opcode == 4) {
                registerB = registerB ^ registerC;
            } else if (opcode == 5) {
                result.add(comboOperand % 8);
            } else if (opcode == 6) {
                registerB = (long) (registerA / Math.pow(2, comboOperand));
            } else if (opcode == 7) {
                registerC = (long) (registerA / Math.pow(2, comboOperand));
            }
            instructionCounter += 2;
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long registerA = Long.parseLong(br.readLine().split(": ")[1]);
        long registerB = Long.parseLong(br.readLine().split(": ")[1]);
        long registerC = Long.parseLong(br.readLine().split(": ")[1]);
        br.readLine();
        String[] valuesString = br.readLine().split(": ")[1].split(",");
        List<Long> values = Arrays.stream(valuesString)
                .map(Long::valueOf).collect(Collectors.toList());
        Collections.reverse(values);


        List<PartialSolution> partialSolutions = new ArrayList<>();
        partialSolutions.add(new PartialSolution(0, 0));

        long result = 0;
        while (!partialSolutions.isEmpty()) {
            PartialSolution currentPartialSolution = partialSolutions.remove(0);
            int valueIterator = currentPartialSolution.getValueIterator();
            result = currentPartialSolution.getResult();
            if (valueIterator == values.size()) {
                break;
            }
            for (long i = 0; i < 8; i++) {
                long value1 = i ^ 5;
                long value2 = result << 3;
                value2 = value2 + i;
                value2 = value2 >> value1;
                value1 = value1 ^ 6;
                value2 = value2 % 8;
                value1 = value1 ^ value2;
                if (value1 == values.get(valueIterator)) {
                    partialSolutions.add(new PartialSolution(result * 8 + i, valueIterator + 1));
                }
            }
        }
        return result;
    }

    protected long createSolutionExercise2Temp(BufferedReader br) throws IOException {
        long registerA = Long.parseLong(br.readLine().split(": ")[1]);
        long registerB = Long.parseLong(br.readLine().split(": ")[1]);
        long registerC = Long.parseLong(br.readLine().split(": ")[1]);
        br.readLine();
        String[] valuesString = br.readLine().split(": ")[1].split(",");
        List<Long> values = Arrays.stream(valuesString)
                .map(Long::valueOf).collect(Collectors.toList());
        Collections.reverse(values);

        long result = 0;
        int valueIterator = 0;

        while(valueIterator < values.size()) {
            for (long i = 0; i < 8; i++) {
                long value1 = i ^ 5;
                long value2 = result << 3;
                value2 = value2 + i;
                value2 = value2 >> value1;
                value1 = value1 ^ 6;
                value2 = value2 % 8;
                value1 = value1 ^ value2;
                if (value1 == values.get(valueIterator)) {
                    result = result * 8 + i;
                    valueIterator++;
                    break;
                }
            }
        }
        return result;
    }
}
