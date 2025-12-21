package org.example.Day2511;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day2511Solver extends AbstractSolver {

    private static final String INITIAL_NODE_ID = "svr";

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        Map<String, Node> nodesById = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] auxString = line.split(": ");
            String currentNodeId = auxString[0];
            String[] adjacentNodesIds = auxString[1].split(" ");
            Node currentNode = nodesById.getOrDefault(currentNodeId, new Node(currentNodeId));
            for (String adjacentNodeId : adjacentNodesIds) {
                Node adjacentNode = nodesById.getOrDefault(adjacentNodeId, new Node(adjacentNodeId));
                currentNode.getAdjacentNodes().add(adjacentNode);
                nodesById.put(adjacentNodeId, adjacentNode);
            }
            nodesById.put(currentNodeId, currentNode);
        }

        Node startNode = nodesById.get(INITIAL_NODE_ID);
        List<List<Node>> solutions = new ArrayList<>();

        //solve(nodesById, youNode, solutions, List.of(youNode));
        Map<Node, Set<Node>> graph = new HashMap<>();
        nodesById.values().stream().forEach(value -> graph.put(value, value.getAdjacentNodes()));


        return allPaths(graph, startNode, nodesById.get("out"));
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        while ((line = br.readLine()) != null) {
        }
        return result;
    }

    public static long allPaths(
            Map<Node, Set<Node>> graph,
            Node start,
            Node end
    ) {
        Map<State, Long> memo = new HashMap<>();
        return dfsCount(graph, start, end, false, false, memo);
    }

    private static long dfsCount(
            Map<Node, Set<Node>> graph,
            Node current,
            Node end,
            boolean seenDac,
            boolean seenFft,
            Map<State, Long> memo
    ) {
        // Update state when entering node
        if (current.getId().equals("dac")) seenDac = true;
        if (current.getId().equals("fft")) seenFft = true;

        if (current.equals(end)) {
            return (seenDac && seenFft) ? 1L : 0L;
        }

        State state = new State(current, seenDac, seenFft);
        if (memo.containsKey(state)) {
            return memo.get(state);
        }

        long total = 0L;
        for (Node next : graph.getOrDefault(current, Collections.emptySet())) {
            total += dfsCount(graph, next, end, seenDac, seenFft, memo);
        }

        memo.put(state, total);
        return total;
    }

}
