package org.example.Day24;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        //I started by observing the problem and how the values are constructed
        //Here is the example for bit 27
//        dft XOR mjd -> z27
//        y27 XOR x27 -> dft
//        pvv OR ptc -> mjd (mjd is the value passing from 26)
//
//        y26 AND x26 -> pvv (if both inputs on 26 have value we will pass)
//        or if
//        fdw AND rrk -> ptc
//        x26 XOR y26 -> rrk (there is only one value in 26)
//        and
//        djr OR rvt -> fdw (a value passed from 25)
//        x25 AND y25 -> djr (a value passes from 25 when both inputs are true)
//        or
//        bwq AND qtj -> rvt...(recursive stuff)

        //Then I search and detected cases of calculating the z bits that were wrong:
//        kgr AND vrg -> z30
//        sgt OR bhb -> z05
//        y15 AND x15 -> z15
//        mgq OR nng -> z45 -> THIS ONE IS THE LAST BIT SO IT ISN'T ACTUALLY AN ISSUE

        //The observation allowed me to understand two of the pairs that need switching:
        //y15 AND x15 -> z15 switches with vhr XOR dvj -> dnt
        //kgr AND vrg -> z30 switches with kgr XOR vrg -> gwc
        //sgt OR bhb -> z05 switches with ggh XOR tvp -> jst

        //I am missing just one pair. I will try other strategies to find possible issues.
        //If that fails I will then move to another strategy of testing summing two numbers
        //and check the least significant bit that is wrong.
        //For now, I will try to find "OR" operations that don't have a child node
        //that is a AND between two wires (which is what the code below is for).
        //This returned a node, gvj.
        //Analysing this node, I understood the final switch
        //y10 XOR x10 -> gdf switches with x10 AND y10 -> mcm

        //So, the output wires that need to be switched are: z15, dnt, z30, gwc, z05, jst, gdf, mcm
        //Putting it in the output format: dnt,gdf,gwc,jst,mcm,z05,z15,z30
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

        List<Node> errorNode = nodesByName.values().stream()
                .filter(node -> node.operation == Operation.OR)
                .filter(node -> node.childNodes.stream()
                        .noneMatch(node2 -> node2.operation == Operation.AND
                                && node2.childNodes.get(0).isWire))
                .collect(Collectors.toList());
        return 0L;
    }
}
