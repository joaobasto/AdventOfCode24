package org.example.Day9;

public class FreeSpace {
    private long startPosition;
    private long size;

    public FreeSpace(long startPosition, long size) {
        this.startPosition = startPosition;
        this.size = size;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
