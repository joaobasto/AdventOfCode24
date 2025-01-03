package org.example.Day23;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        //this is a graph problem known in the literature as "clique"
        //for a general problem it is NP-hard
        //my first approach will be to use dynamic programming and try to "prune" the tree
        //as much as possible for it to be calculated in a reasonable ammount of time

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
            for (Node adjNode1 : node.adjacentNodes) {
                for (Node adjNode2 : node.adjacentNodes) {
                    if (adjNode1.adjacentNodes.contains(adjNode2)) {
                        validSets.add(Set.of(node, adjNode1, adjNode2));
                    }
                }
            }
        }

        Set<Node> validNodes = validSets.stream().flatMap(Collection::stream).collect(Collectors.toSet());

        //now we apply the dynamic programming on the group of valid nodes only
        List<Node> validNodesList = new ArrayList<>(validNodes);
        Set<Node> solutionSet = calculateSize(validNodesList, 0, new HashSet<>());
        List<String> solution = solutionSet.stream().map(node -> node.name).sorted().collect(Collectors.toList());
        for(String nodeName : solution) {
            System.out.print(nodeName + ",");
        }
        System.out.println();
        return 0;
    }

    private Set<Node> calculateSize(List<Node> validNodesList, int index, Set<Node> includedNodes) {
        //check for terminal cases
        if (index == validNodesList.size()) {
            return includedNodes;
        }

        //check if including this node will still provide a valid solution
        Node currentNode = validNodesList.get(index);
        //if not, then the only possible solution from here is not including it
        if (!currentNode.adjacentNodes.containsAll(includedNodes)) {
            return calculateSize(validNodesList, index + 1, includedNodes);
        }
        //if yes, then the solution is the maximum between the two possible decisions
        //(including this node or not)
        Set<Node> newNodes = new HashSet<>(includedNodes);
        newNodes.add(validNodesList.get(index));
        Set<Node> solution1 = calculateSize(validNodesList, index + 1, includedNodes);
        Set<Node> solution2 = calculateSize(validNodesList, index + 1, newNodes);
        return solution1.size() > solution2.size() ? solution1 : solution2;
    }
}
