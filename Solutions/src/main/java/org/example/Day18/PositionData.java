package org.example.Day18;

public class PositionData {
    private boolean corrupted;

    public PositionData(boolean corrupted) {
        this.corrupted = corrupted;
    }

    public boolean isCorrupted() {
        return corrupted;
    }

    public void setCorrupted(boolean corrupted) {
        this.corrupted = corrupted;
    }

}
