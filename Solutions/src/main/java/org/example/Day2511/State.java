package org.example.Day2511;

import java.util.Objects;

public class State {
    final Node node;
    final boolean seenDac;
    final boolean seenFft;

    public State(Node node, boolean seenDac, boolean seenFft) {
        this.node = node;
        this.seenDac = seenDac;
        this.seenFft = seenFft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return seenDac == state.seenDac &&
                seenFft == state.seenFft &&
                node.equals(state.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, seenDac, seenFft);
    }
}

