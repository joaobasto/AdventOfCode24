package org.example.Day17;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
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
                System.out.print(comboOperand % 8 + ",");
            } else if (opcode == 6) {
                registerB = (long) (registerA / Math.pow(2, comboOperand));
            } else if (opcode == 7) {
                registerC = (long) (registerA / Math.pow(2, comboOperand));
            }
            instructionCounter += 2;
        }
        System.out.println();
        return 0;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
