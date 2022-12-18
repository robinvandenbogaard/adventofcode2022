package nl.rockstars.robin;

import nl.rockstars.robin.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Day14 implements DayProcessor {

    List<Wall> walls = new ArrayList<>();

    int maxY = 0, maxX = 0, minX = 800;
    private World world;

    public static void main(String[] args) {
        new Day14().go();
    }

    @Override
    public void process(String line) {
        var coords = line.split(" -> ");

        var start = coords[0];
        for (int i = 1; i < coords.length; i++) {
            var end = coords[i];
            var wall = Wall.of(start, end);
            walls.add(wall);
            start = end;
            maxY = Math.max(maxY, Math.max(wall.start.y(), wall.end.y()));
            maxX = Math.max(maxX, Math.max(wall.start.x(), wall.end.x()));
            minX = Math.min(minX, Math.min(wall.start.x(), wall.end.x()));
        }
    }

    @Override
    public void afterInput() {
        world = World.of(minX, maxX, maxY);
        world.addWalls(walls);
    }

    @Override
    public Result getResult() {
        int settled = 0;
        while (world.addSettledSand()) {
            settled++;
            //world.print();
        }
        return new Result(settled);
    }

    record Wall(Point start, Point end) {
        public static Wall of(String start, String end) {
            return new Wall(Point.of(start), Point.of(end));
        }

        public int x1() {
            return Math.min(start.x(), end.x());
        }
        public int x2() {
            return Math.max(start.x(), end.x());
        }

        public int y1() {
            return Math.min(start.y(), end.y());
        }
        public int y2() {
            return Math.max(start.y(), end.y());
        }
    }

    static final class Sand {
        private Point location;

        Sand(Point location) {
            this.location = location;
        }

        public static Sand of(Point location) {
            return new Sand(location);
        }

        public boolean inAbyss(int height) {
            return location.y() == height;
        }

        public int x() {
            return location.x();
        }

        public int y() {
            return location.y();
        }

        public boolean fallFurther(World world) {
            var spot = world.getFreeSpot(location);
            if (spot != null) {
                location = spot;
                return true;
            }
            return false;
        }
    }

    private static class World {
        private final int[][] world;
        private final Point spawn;
        private final int offSet;

        public World(int width, int height, int offSet) {
            this.offSet = offSet;
            this.spawn = Point.of(500 - offSet, 0);
            this.world = new int[width][height];
        }

        public static World of(int minX, int maxX, int maxY) {
            return new World((maxX - minX) + 4, maxY + 4, minX-2);
        }

        public void addWalls(List<Wall> walls) {
            for (var w : walls) {
                for (int x = w.x1(); x <= w.x2(); x++) {
                    for (int y = w.y1(); y <= w.y2(); y++) {
                        world[x - offSet][y] = 1;
                    }
                }
            }
        }

        private boolean addSettledSand() {
            Sand sand = Sand.of(Point.of(spawn.x(), spawn.y()));
            while (sand.fallFurther(this)) {
                //continue falling;
            }
            world[sand.x()][sand.y()] = 2;
            return !sand.inAbyss(world[0].length-1);
        }

        public Point getFreeSpot(Point location) {
            var left = location.left().down();
            var bottom = location.down();
            var right = location.right().down();
            if (isAvailable(bottom))
                return bottom;
            else if (isAvailable(left))
                return left;
            else if (isAvailable(right))
                return right;
            else
                return null;
        }

        private boolean isAvailable(Point p) {
            return p.y() < world[0].length && world[p.x()][p.y()] == 0;
        }

        public void print() {
            System.out.println();
            System.out.println();
            for (int y = 0; y < world[0].length; y++) {
                for (int x = 0; x < world.length; x++) {
                    System.out.print(world[x][y]);
                }
                System.out.println();
            }
        }
    }
}

