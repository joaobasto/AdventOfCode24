package org.example.Day11;

public class Element {

    private long value;
    private long blinkStage;

    public Element(long value, long blinkStage) {
        this.value = value;
        this.blinkStage = blinkStage;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getBlinkStage() {
        return blinkStage;
    }

    public void setBlinkStage(long blinkStage) {
        this.blinkStage = blinkStage;
    }
}
