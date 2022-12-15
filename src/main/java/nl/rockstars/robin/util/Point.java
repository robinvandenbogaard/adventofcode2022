package nl.rockstars.robin.util;

import java.util.Objects;

public final class Point {
    public static Point start = Point.of(0, 0);
    private final int x;
    private final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public Point move(Direction d) {
        return new Point(x + d.x(), y + d.y());
    }

    public Point move(Point p) {
        return new Point(x + p.x(), y + p.y());
    }

    public boolean lte(Point other) {
        return this.x <= other.x && this.y <= other.x;
    }

    public Point distance(Point target) {
        return Point.of(Math.abs(this.x - target.x), Math.abs(this.y - target.y));
    }

    public Point add(Direction d) {
        return move(d);
    }

    public boolean inBounds(int size) {
        return x>=0 && y>=0 && x<size && y<size;
    }

    public Point up() {
        return move(Direction.up);
    }

    public Point down() {
        return move(Direction.down);
    }

    public Point right() {
        return move(Direction.right);
    }

    public Point left() {
        return move(Direction.left);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Point) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

    public int manhattanDistance(Point other) {
        return Math.abs(x-other.x)+Math.abs(y-other.y);
    }
}
