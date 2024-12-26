package org.example.Day24;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day24Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        Map<String, Node> nodesByName = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            String[] values = line.split(": ");
            String wireName = values[0];
            boolean output = Integer.parseInt(values[1]) == 1;
            nodesByName.put(wireName, new Node(wireName, true, output));
        }
        while ((line = br.readLine()) != null) {
            String[] values = line.split(" ");
            String childNodeName1 = values[0];
            String childNodeName2 = values[2];
            Operation operation = null;
            if (values[1].equals("AND")) {
                operation = Operation.AND;
            } else if (values[1].equals("OR")) {
                operation = Operation.OR;
            } else {
                operation = Operation.XOR;
            }
            String nodeName = values[4];
            Node node = nodesByName.computeIfAbsent(nodeName, unused -> new Node(nodeName, false));
            node.operation = operation;
            Node childNode1 = nodesByName.computeIfAbsent(childNodeName1, unused -> new Node(childNodeName1, false));
            Node childNode2 = nodesByName.computeIfAbsent(childNodeName2, unused -> new Node(childNodeName2, false));
            node.childNodes.add(childNode1);
            node.childNodes.add(childNode2);
        }

        long bitIndex = 0;
        long result = 0;
        while (true) {
            String key = "z" + (bitIndex < 10 ? "0" : "") + bitIndex;
            Node node = nodesByName.get(key);
            if (node == null) {
                break;
            }
            if (node.getValue()) {
                result += (1L << bitIndex);
            }
            bitIndex++;
        }
        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
