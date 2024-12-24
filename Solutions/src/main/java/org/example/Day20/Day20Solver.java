package org.example.Day20;

import org.example.AbstractSolver;
import org.example.Day20.Node;
import org.example.utils.Direction2D;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day20Solver extends AbstractSolver {
    //we can use the method that solves exercise 2 to solve exercise 1 if we change this value to 2
    protected static long MAXIMUM_CHEAT_LENGTH = 20;
    protected static long BIG_DISTANCE = 1000000000000L;
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long y = 0;
        Node startNode = null;
        Node endNode = null;
        Map<Node, Node> nodes = new HashMap<>();
        Map<Node, Map<Node, Long>> distances = new HashMap<>();
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character currentCharacter = characters[(int) x];
                Node currentNode = null;
                if (currentCharacter.equals('#')) {
                    currentNode = new Node(x, y, true);
                } else {
                    currentNode = new Node(x, y, false);
                }
                if (currentCharacter.equals('S')) {
                    startNode = currentNode;
                }
                if (currentCharacter.equals('E')) {
                    endNode = currentNode;
                }

                nodes.put(currentNode, currentNode);
                distances.put(currentNode, new HashMap<>());
                //check if node should be connected to left and up node
                Node leftNode = nodes.get(new Node(x - 1, y, false));
                if (x > 0 && leftNode != null) {
                    currentNode.addAdjacentNodes(leftNode);
                    leftNode.addAdjacentNodes(currentNode);
                    if (!currentNode.wall && !leftNode.wall) {
                        distances.get(currentNode).put(leftNode, 1L);
                        distances.get(leftNode).put(currentNode, 1L);
                    } else {
                        distances.get(currentNode).put(leftNode, BIG_DISTANCE);
                        distances.get(leftNode).put(currentNode, BIG_DISTANCE);
                    }
                }
                Node upNode = nodes.get(new Node(x, y - 1, false));
                if (y > 0 && upNode != null) {
                    currentNode.addAdjacentNodes(upNode);
                    upNode.addAdjacentNodes(currentNode);
                    if (!currentNode.wall && !upNode.wall) {
                        distances.get(currentNode).put(upNode, 1L);
                        distances.get(upNode).put(currentNode, 1L);
                    } else {
                        distances.get(currentNode).put(upNode, BIG_DISTANCE);
                        distances.get(upNode).put(currentNode, BIG_DISTANCE);
                    }
                }
            }
            y++;
        }

        //Dijkstra
        Map<Node, Long> nodeDistances = new HashMap<>();
        for (Node node : nodes.values()) {
            node.setDistance(BIG_DISTANCE);
        }
        startNode.setDistance( 0L);
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> Math.toIntExact(node.getDistance())));
        pq.add(startNode);
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            for(Node adjacentNode : currentNode.getAdjacentNodes()) {
                long newDistance = currentNode.getDistance() + distances.get(currentNode).get(adjacentNode);
                if (newDistance < adjacentNode.getDistance()) {
                    adjacentNode.setDistance(newDistance);
                    pq.add(adjacentNode);
                }
            }
        }

        //get list of wall nodes that have multiple adjacent nodes belonging to path.
        List<Cheat> cheats = new ArrayList<>();
        for (Node node : nodes.values()) {
            if (!node.wall) {
                continue;
            }
            List<Node> adjNodes = node.getAdjacentNodes().stream()
                    .filter(adjNode -> !adjNode.wall)
                    .sorted((adjNode1, adjNode2) -> Math.toIntExact(adjNode1.getDistance() - adjNode2.getDistance()))
                    .collect(Collectors.toList());
            if (adjNodes.size() < 2) {
                continue;
            }
            for (int i = 0; i < adjNodes.size(); i++) {
                for (int j = i + 1; j < adjNodes.size(); j++) {
                    cheats.add(new Cheat(node, adjNodes.get(i), adjNodes.get(j)));
                }
            }
        }

        List<Cheat> timeSavingCheats = cheats.stream()
                .filter(cheat -> cheat.distanceSaved >= 100).collect(Collectors.toList());

        return timeSavingCheats.size();
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        long y = 0;
        Node startNode = null;
        Node endNode = null;
        Map<Node, Node> nodes = new HashMap<>();
        Map<Node, Map<Node, Long>> distances = new HashMap<>();
        List<Node> pathNodes = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character currentCharacter = characters[(int) x];
                Node currentNode = null;
                if (currentCharacter.equals('#')) {
                    currentNode = new Node(x, y, true);
                } else {
                    currentNode = new Node(x, y, false);
                    pathNodes.add(currentNode);
                }
                if (currentCharacter.equals('S')) {
                    startNode = currentNode;
                }
                if (currentCharacter.equals('E')) {
                    endNode = currentNode;
                }

                nodes.put(currentNode, currentNode);
                distances.put(currentNode, new HashMap<>());
                //check if node should be connected to left and up node
                Node leftNode = nodes.get(new Node(x - 1, y, false));
                if (x > 0 && leftNode != null) {
                    currentNode.addAdjacentNodes(leftNode);
                    leftNode.addAdjacentNodes(currentNode);
                    if (!currentNode.wall && !leftNode.wall) {
                        distances.get(currentNode).put(leftNode, 1L);
                        distances.get(leftNode).put(currentNode, 1L);
                    } else {
                        distances.get(currentNode).put(leftNode, BIG_DISTANCE);
                        distances.get(leftNode).put(currentNode, BIG_DISTANCE);
                    }
                }
                Node upNode = nodes.get(new Node(x, y - 1, false));
                if (y > 0 && upNode != null) {
                    currentNode.addAdjacentNodes(upNode);
                    upNode.addAdjacentNodes(currentNode);
                    if (!currentNode.wall && !upNode.wall) {
                        distances.get(currentNode).put(upNode, 1L);
                        distances.get(upNode).put(currentNode, 1L);
                    } else {
                        distances.get(currentNode).put(upNode, BIG_DISTANCE);
                        distances.get(upNode).put(currentNode, BIG_DISTANCE);
                    }
                }
            }
            y++;
        }

        //Dijkstra
        Map<Node, Long> nodeDistances = new HashMap<>();
        for (Node node : nodes.values()) {
            node.setDistance(BIG_DISTANCE);
        }
        startNode.setDistance( 0L);
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> Math.toIntExact(node.getDistance())));
        pq.add(startNode);
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            for(Node adjacentNode : currentNode.getAdjacentNodes()) {
                long newDistance = currentNode.getDistance() + distances.get(currentNode).get(adjacentNode);
                if (newDistance < adjacentNode.getDistance()) {
                    adjacentNode.setDistance(newDistance);
                    pq.add(adjacentNode);
                }
            }
        }

        List<Cheat> timeSavingCheats = new ArrayList<>();
        for (int i = 0; i < pathNodes.size(); i++) {
            for (int j = i + 1; j < pathNodes.size(); j++) {
                Node node1 = pathNodes.get(i).distance < pathNodes.get(j).getDistance() ?
                        pathNodes.get(i) : pathNodes.get(j);
                Node node2 = pathNodes.get(i).distance < pathNodes.get(j).getDistance() ?
                        pathNodes.get(j) : pathNodes.get(i);

                //get manhattan distance
                long xDiff = Math.abs(node1.getPosition2D().getX() - node2.getPosition2D().getX());
                long yDiff = Math.abs(node1.getPosition2D().getY() - node2.getPosition2D().getY());
                long manhattanDistance = xDiff + yDiff;
                if (manhattanDistance <= MAXIMUM_CHEAT_LENGTH) {
                    if ((node2.getDistance() - node1.getDistance()) - manhattanDistance >= 100) {
                        timeSavingCheats.add(new Cheat(null, node1, node2));
                    }
                }
            }
        }

        return timeSavingCheats.size();
    }
}
