package nl.rockstars.robin;

import java.util.Arrays;

public class Day8 implements DayProcessor {

    Integer[][] grid;
    int size;
    private int currrow;
    private int total;

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
        total += getEdgeTreeCount(size);
        total += visibleInnerTrees(grid, size);
    }

    int getEdgeTreeCount(int size) {
        return (size * 4) - 4;
    }

    int visibleInnerTrees(Integer[][] grid, int size) {
        var result = 0;
        for (int x = 1; x < size - 1; x++) {
            for (int y = 1; y < size - 1; y++) {
                result += Math.min(1,testH(x, y, grid, size)+testV(x, y, grid, size));
            }
        }
        return result;
    }

    int testH(int x, int y, Integer[][] grid, int size) {
        if (x == size-1 || y == size-1 || x <= 0 || y <= 0)
            throw new IllegalStateException("do not check edges");

        var height = grid[x][y];
        var blockedLeft = false;
        var blockedRight = false;
        for (int i = 0; i < size && (!blockedLeft || !blockedRight); i++) {
            if (x == i)
                continue;
            if (i < x)
                blockedLeft = grid[i][y] >= height || blockedLeft;
            if (i > x)
                blockedRight = grid[i][y] >= height || blockedRight;
        }
        return blockedLeft && blockedRight ? 0 : 1;
    }

    int testV(int x, int y, Integer[][] grid, int size) {
        if (x == size-1 || y == size-1 || x <= 0 || y <= 0)
            throw new IllegalStateException("do not check edges");

        var height = grid[x][y];
        var blockedLeft = false;
        var blockedRight = false;
        for (int i = 0; i < size && (!blockedLeft || !blockedRight); i++) {
            if (y == i)
                continue;
            if (i < y)
                blockedLeft = grid[x][i] >= height || blockedLeft;
            if (i > y)
                blockedRight = grid[x][i] >= height || blockedRight;
        }
        return blockedLeft && blockedRight ? 0 : 1;
    }

    @Override
    public Result getResult() {
        return new Result(total);
    }
}