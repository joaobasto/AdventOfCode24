package org.example.Day23;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day23Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        Map<String, Node> nodesByName = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] names = line.split("-");
            String node1Name = names[0];
            String node2Name = names[1];
            Node node1 = nodesByName.computeIfAbsent(node1Name, unused -> new Node(node1Name));
            Node node2 = nodesByName.computeIfAbsent(node2Name, unused -> new Node(node2Name));
            node1.adjacentNodes.add(node2);
            node2.adjacentNodes.add(node1);
        }

        Set<Set<Node>> validSets = new HashSet<>();
        for (Node node : nodesByName.values()) {
            if(!node.name.startsWith("t")) {
                continue;
            }
            for (Node adjNode1 : node.adjacentNodes) {
                for (Node adjNode2 : node.adjacentNodes) {
                    if (adjNode1.adjacentNodes.contains(adjNode2)) {
                        validSets.add(Set.of(node, adjNode1, adjNode2));
                    }
                }
            }
        }

        return validSets.size();
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        return 0;
    }
}
