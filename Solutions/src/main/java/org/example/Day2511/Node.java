package org.example.Day2511;

import java.util.*;

public class Node {

    private final String id;

    private Set<Node> adjacentNodes = new HashSet<>();

    public Node(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Set<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Set<Node> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
