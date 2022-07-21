package de.michm.vin.lib;

public class Point {
    private final long x;
    private final long y;

    Point(String cords) {
        cords = cords.replace(", ", ",");

        int separatorIndex = cords.indexOf(',');
        String pointX = cords.substring(0, separatorIndex);
        String pointY = cords.substring(separatorIndex + 1);

        x = Long.parseLong(pointX);
        y = Long.parseLong(pointY);
    }

    Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }
    public long getY() {
        return y;
    }

    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }
}
