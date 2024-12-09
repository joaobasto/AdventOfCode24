package org.example.Day9;

public class FileSpace {
    private long startPosition;
    private long size;
    private Long id;

    public FileSpace(long startPosition, long size, Long id) {
        this.startPosition = startPosition;
        this.size = size;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
