package nl.rockstars.robin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day9 implements DayProcessor {

    private Head head;
    private Tail tail;

    public static void main(String[] args) {
        new Day9().go();
    }

    @Override
    public void beforeInput() {
        this.head = Head.start();
        this.tail = Tail.start();
    }

    @Override
    public void process(String line) {
        var split = line.split(" ");
        var d = Direction.of(split[0]);
        var steps = Integer.parseInt(split[1]);

        for (int i = 0; i < steps; i++) {
            head.move(d);
            tail.follow(head);
        }
    }

    @Override
    public Result getResult() {
        return new Result(tail.uniqueLocations.size());
    }

    static class Tail {

        private Point location;

        private final Set<Point> uniqueLocations;

        Tail(int x, int y) {
            this.location = Point.of(x, y);
            this.uniqueLocations = new HashSet<>();
            uniqueLocations.add(location);
        }

        public static Tail start() {
            return new Tail(0, 0);
        }

        public Point follow(Head target) {
            if (location.equals(target.current))
                return location;
            if (location.distance(target.current).lte(Point.of(1, 1)))
                return location;

            location = target.previousLocation();
            this.uniqueLocations.add(location);
            return location;
        }
    }

    static class Head {

        private Point current;
        private Point previous = null;

        Head(int x, int y) {
            current = Point.of(x, y);
        }

        public static Head start() {
            return new Head(0, 0);
        }

        public Head up() {
            move(Direction.up);
            return this;
        }

        public Head down() {
            move(Direction.down);
            return this;
        }

        public Head right() {
            move(Direction.right);
            return this;
        }

        public Head left() {
            move(Direction.left);
            return this;
        }

        public Point previousLocation() {
            return previous;
        }

        public void move(Direction d) {
            previous = current;
            current = current.move(d);
        }
    }

    static class Point {
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
            return new Point(x + d.x, y + d.y);
        }

        protected boolean lte(Point other) {
            return this.x <= other.x && this.y <= other.x;
        }

        protected Point distance(Point target) {
            return Point.of(Math.abs(this.x - target.x), Math.abs(this.y - target.y));
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

    }

    record Direction(int x, int y) {
        static Direction up = new Direction(0, -1);
        static Direction down = new Direction(0, 1);
        static Direction left = new Direction(-1, 0);
        static Direction right = new Direction(1, 0);

        public static Direction of(String key) {
            return switch (key) {
                case "U" -> up;
                case "D" -> down;
                case "R" -> right;
                case "L" -> left;
                default -> throw new IllegalArgumentException(key);
            };
        }
    }
}