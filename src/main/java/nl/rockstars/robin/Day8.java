package nl.rockstars.robin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day8 implements DayProcessor {

    Integer[][] grid;
    int size;
    private int currrow;
    private int scenicScore;

    public static void main(String[] args) {
        new Day8().go();
    }

    @Override
    public void beforeInput() {
        grid = new Integer[100][100];
        currrow = 0;
    }

    @Override
    public void process(String line) {
        size = line.length();
        grid[currrow] = Arrays.asList(line.split("")).stream().map(Integer::parseInt).toList().toArray(new Integer[100]);
        currrow++;
    }

    @Override
    public void afterInput() {
        List<Integer> scores = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                scores.add(viewCount(x, y, grid, size));
            }
        }
        scenicScore = scores.stream().max(Comparator.naturalOrder()).orElseThrow();

    }

    public int viewCount(int x, int y, Integer[][] grid, int size) {
        int up=1, left=1, right=1, down=1;
        for (int i = 0; i < size; i++) {
            var p = new Point(x, y);
            up = sees(Direction.up, p, grid, size);
            left = sees(Direction.left, p, grid, size);
            right = sees(Direction.right, p, grid, size);
            down = sees(Direction.down, p, grid, size);
        }

        return up * left * right * down;
    }

    int sees(Direction d, Point p, Integer[][] grid, int size) {
        var seen = 0;
        for (int i = 1; i < size; i++) {

            var targetTree = p.add(d.multiply(i));
            if (!targetTree.inBounds(size) && !p.equals(targetTree))
                break;

            int height = grid[targetTree.x][targetTree.y];
            if (height >= grid[p.x][p.y]) {
                if (!p.equals(targetTree))
                    seen++;
                break;
            }
            seen++;
        }
        return seen;
    }

    @Override
    public Result getResult() {
        return new Result(scenicScore);
    }

    record Point(int x, int y) {

        public Point add(Direction d) {
            return new Point(x+d.x, y+d.y);
        }

        public boolean inBounds(int size) {
            return x>=0 && y>=0 && x<size && y<size;
        }
    }

    record Direction(int x, int y) {
        static Direction up = new Direction(0,-1);
        static Direction down = new Direction(0,1);
        static Direction left = new Direction(-1,0);
        static Direction right = new Direction(1,0);

        public Direction multiply(int multiplier) {
            return new Direction(x*multiplier, y*multiplier);
        }
    }
}