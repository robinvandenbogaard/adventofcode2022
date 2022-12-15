package nl.rockstars.robin;

import nl.rockstars.robin.util.Direction;
import nl.rockstars.robin.util.Point;

import java.util.HashSet;
import java.util.Set;

public class Day9 implements DayProcessor {
    private Snake snake;

    public static void main(String[] args) {
        new Day9().go();
    }

    @Override
    public void beforeInput() {
        this.snake = new Snake(10);
    }

    @Override
    public void process(String line) {
        var split = line.split(" ");
        var d = Direction.of(split[0]);
        var steps = Integer.parseInt(split[1]);

        System.out.println(line);
        for (int i = 0; i < steps; i++) {
            snake.move(d);
            draw(snake);
        }
    }

    @Override
    public Result getResult() {
        return new Result(snake.uniqueTailLocations());
    }

    static class Snake {
        final Head head;

        final Tail tail;

        public Snake(int length) {
            this.tail = Tail.start();
            BodyPart next = tail;
            for (int part = 2; part < length; part++) {
                next = new BodyPart(next);
            }
            this.head = new Head(0,0, next);
        }

        public int uniqueTailLocations() {
            return tail.uniqueLocations.size();
        }

        public void move(Direction d) {
            head.move(d);
        }

        public String partOn(Point point) {
            if (head.location.move(DRAWOFFSET).equals(point))
                return "H";
            else
                return head.partOn(point);
        }
    }

    static class BodyPart {
        protected Point location;
        protected Point previous;
        protected final BodyPart next;

        BodyPart(BodyPart next) {
            this.location = Point.of(0,0);
            this.next = next;
        }

        protected Point previousLocation() {
            return previous;
        }

        public Point follow(BodyPart target) {
            if (location.equals(target.location))
                return location;
            if (location.distance(target.location).lte(Point.of(1, 1)))
                return location;

            previous = location;
            location = target.previousLocation();

            letNextFollow();

            return location;
        }

        protected void letNextFollow() {
            if (next != null)
                next.follow(this);
        }

        public String partOn(Point point) {
            if (location.move(DRAWOFFSET).equals(point))
                return next != null ? "#" : "T";
            else if (next != null)
                return next.partOn(point);
            else
                return ".";
        }
    }

    static class Tail extends BodyPart {

        private final Set<Point> uniqueLocations;

        Tail(int x, int y) {
            super(null);
            this.location = Point.of(x, y);
            this.uniqueLocations = new HashSet<>();
            uniqueLocations.add(location);
        }

        public static Tail start() {
            return new Tail(0, 0);
        }

        public Point follow(BodyPart target) {
            super.follow(target);
            this.uniqueLocations.add(location);
            return location;
        }
    }

    static class Head extends BodyPart {

        Head(int x, int y, BodyPart next) {
            super(next);
            location = Point.of(x, y);
        }

        public static Head start() {
            return new Head(0, 0, Tail.start());
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

        public void move(Direction d) {
            previous = location;
            location = location.move(d);

            letNextFollow();
        }
    }

    private void draw(Snake snake) {
        int size = DRAWSIZE;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(snake.partOn(Point.of(x,y)));
            }
            System.out.println();
        }
    }

    private static final int DRAWSIZE = 26;
    public static final Point DRAWOFFSET = Point.of(DRAWSIZE/2, DRAWSIZE/2);
}