package org.example.Day16;

import org.example.AbstractSolver;
import org.example.utils.Direction2D;
import org.example.utils.Grid;
import org.example.utils.MathUtils;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day16Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        long y = 0;
        Node startNode = null;
        Position2D endPosition = null;
        Map<Node, Node> nodes = new HashMap<>();
        Map<Node, Map<Node, Long>> distances = new HashMap<>();
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character currentCharacter = characters[(int) x];
                if (currentCharacter.equals('#')) {
                    continue;
                }

                Node newNodeNorth = new Node(x, y, Direction2D.NORTH);
                Node newNodeEast = new Node(x, y, Direction2D.EAST);
                nodes.put(newNodeNorth, newNodeNorth);
                distances.put(newNodeNorth, new HashMap<>());
                nodes.put(newNodeEast, newNodeEast);
                distances.put(newNodeEast, new HashMap<>());
                //check if node should be connected to left and up node
                Node leftNode = nodes.get(new Node(x - 1, y, Direction2D.EAST));
                if (x > 0 && leftNode != null) {
                    newNodeEast.addAdjacentNodes(leftNode);
                    leftNode.addAdjacentNodes(newNodeEast);
                    distances.get(newNodeEast).put(leftNode, 1L);
                    distances.get(leftNode).put(newNodeEast, 1L);
                }
                Node upNode = nodes.get(new Node(x, y - 1, Direction2D.NORTH));
                if (y > 0 && upNode != null) {
                    newNodeNorth.addAdjacentNodes(upNode);
                    upNode.addAdjacentNodes(newNodeNorth);
                    distances.get(newNodeNorth).put(upNode, 1L);
                    distances.get(upNode).put(newNodeNorth, 1L);
                }
                //connect nodes of this position between each other
                newNodeNorth.addAdjacentNodes(newNodeEast);
                newNodeEast.addAdjacentNodes(newNodeNorth);
                distances.get(newNodeNorth).put(newNodeEast, 1000L);
                distances.get(newNodeEast).put(newNodeNorth, 1000L);

                if (currentCharacter.equals('S')) {
                    startNode = newNodeEast;
                }
                if (currentCharacter.equals('E')) {
                    endPosition = new Position2D(x, y);
                }
            }
            y++;
        }

        //Dijkstra
        Map<Node, Long> nodeDistances = new HashMap<>();
        for (Node node : nodes.values()) {
            node.setDistance(Long.MAX_VALUE);
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

        return Math.min( nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.NORTH)).getDistance(),
                nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.EAST)).getDistance());
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        long y = 0;
        Node startNode = null;
        Position2D endPosition = null;
        Map<Node, Node> nodes = new HashMap<>();
        Map<Node, Map<Node, Long>> distances = new HashMap<>();
        while ((line = br.readLine()) != null) {
            char[] characters = line.toCharArray();
            for(long x = 0; x < characters.length; x++) {
                Character currentCharacter = characters[(int) x];
                if (currentCharacter.equals('#')) {
                    continue;
                }

                Node newNodeNorth = new Node(x, y, Direction2D.NORTH);
                Node newNodeEast = new Node(x, y, Direction2D.EAST);
                nodes.put(newNodeNorth, newNodeNorth);
                distances.put(newNodeNorth, new HashMap<>());
                nodes.put(newNodeEast, newNodeEast);
                distances.put(newNodeEast, new HashMap<>());
                //check if node should be connected to left and up node
                Node leftNode = nodes.get(new Node(x - 1, y, Direction2D.EAST));
                if (x > 0 && leftNode != null) {
                    newNodeEast.addAdjacentNodes(leftNode);
                    leftNode.addAdjacentNodes(newNodeEast);
                    distances.get(newNodeEast).put(leftNode, 1L);
                    distances.get(leftNode).put(newNodeEast, 1L);
                }
                Node upNode = nodes.get(new Node(x, y - 1, Direction2D.NORTH));
                if (y > 0 && upNode != null) {
                    newNodeNorth.addAdjacentNodes(upNode);
                    upNode.addAdjacentNodes(newNodeNorth);
                    distances.get(newNodeNorth).put(upNode, 1L);
                    distances.get(upNode).put(newNodeNorth, 1L);
                }
                //connect nodes of this position between each other
                newNodeNorth.addAdjacentNodes(newNodeEast);
                newNodeEast.addAdjacentNodes(newNodeNorth);
                distances.get(newNodeNorth).put(newNodeEast, 1000L);
                distances.get(newNodeEast).put(newNodeNorth, 1000L);

                if (currentCharacter.equals('S')) {
                    startNode = newNodeEast;
                }
                if (currentCharacter.equals('E')) {
                    endPosition = new Position2D(x, y);
                }
            }
            y++;
        }

        //Dijkstra
        Map<Node, Long> nodeDistances = new HashMap<>();
        for (Node node : nodes.values()) {
            node.setDistance(Long.MAX_VALUE);
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


        Set<Node> pathNodes = new HashSet<>();
        Set<Node> toExploreNodes = new HashSet<>();
        long minDistance = Math.min( nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.NORTH)).getDistance(),
                nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.EAST)).getDistance());
        if (minDistance == nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.NORTH)).getDistance()) {
            toExploreNodes.add(nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.NORTH)));
        }
        if (minDistance == nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.EAST)).getDistance()) {
            toExploreNodes.add(nodes.get(new Node(endPosition.getX(), endPosition.getY(), Direction2D.EAST)));
        }

        while (!toExploreNodes.isEmpty()) {
            Node currentNode = toExploreNodes.iterator().next();
            toExploreNodes.remove(currentNode);

            for (Node adjacentNode : currentNode.getAdjacentNodes()) {
                if (adjacentNode.getDistance() == currentNode.getDistance() - distances.get(currentNode).get(adjacentNode)) {
                    if (!pathNodes.contains(adjacentNode)) {
                        toExploreNodes.add(adjacentNode);
                    }
                }
            }
            toExploreNodes.remove(currentNode);
            pathNodes.add(currentNode);
        }

        Set<Position2D> pathPositions = new HashSet<>();
        for (Node pathNode : pathNodes) {
            pathPositions.add(pathNode.getPosition2D());
        }

        return pathPositions.size();
    }
}
