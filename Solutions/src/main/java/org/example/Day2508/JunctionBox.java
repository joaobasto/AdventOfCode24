package org.example.Day2508;

import java.util.*;

public class JunctionBox {
    private long id;
    private long x;
    private long y;
    private long z;
    private Set<JunctionBox> connectedJunctionBoxes = new HashSet<>();

    public JunctionBox(long id, long x, long y, long z) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getId() {
        return id;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public Set<JunctionBox> getConnectedJunctionBoxes() {
        return connectedJunctionBoxes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JunctionBox that = (JunctionBox) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
