package org.example.Day12;

import org.example.utils.Position2D;

import java.util.ArrayList;
import java.util.List;

public class Node {

    Position2D position2D;

    Character character;

    List<Node> adjacentNodes = new ArrayList<>();

    boolean visited = false;

    public Node(long x, long y, Character character) {
        this.position2D = new Position2D(x, y);
        this.character = character;
    }

    public boolean wasVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Character getCharacter() {
        return character;
    }

    public List<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void addAdjacentNodes(Node adjacentNode) {
        this.adjacentNodes.add(adjacentNode);
    }
}
