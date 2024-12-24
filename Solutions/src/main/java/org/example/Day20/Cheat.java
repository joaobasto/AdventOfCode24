package org.example.Day20;

public class Cheat {

    Node wallNode;
    Node enterNode;
    Node exitNode;

    long distanceSaved;

    public Cheat(Node wallNode, Node enterNode, Node exitNode) {
        this.wallNode = wallNode;
        this.enterNode = enterNode;
        this.exitNode = exitNode;
        this.distanceSaved = exitNode.getDistance() - enterNode.getDistance() - 2;
    }
}
