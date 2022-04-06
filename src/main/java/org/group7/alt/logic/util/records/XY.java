package org.group7.alt.logic.util.records;

public record XY(int x, int y) {
    public XY(double xd, double yd) {
        this((int)xd, (int)yd);
    }
}