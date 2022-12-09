package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day8.Direction;
import static nl.rockstars.robin.Day8.Point;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day8Test {

    private Day8 day8;

    @BeforeEach
    void setUp() {
        day8 = new Day8();
    }

    @Test
    void inputTest() {
        var result = day8.go().output();
        assertThat(result, is("8"));
    }

    @Test
    void viewing() {
        assertThat(gridView(2,1, "30373 " +
                "25512 " +
                "65332 " +
                "33549 " +
                "35390", 5), is(1*1*2*2));

        assertThat(gridView(2,3, "30373 " +
                "25512 " +
                "65332 " +
                "33549 " +
                "35390", 5), is(2*2*1*2));
    }

    @Test
    void edgeTreesScoreZero() {
        assertThat(gridView(0,1,
                "30373 " +
                "25512 " +
                "65332 " +
                "33549 " +
                "35390", 5), is(0*1*4*2));
    }

    @Test
    void seesUp() {
        var top = day8.sees(Direction.up, new Point(1, 0), toGrid("111 111 111", 3),3);
        var center = day8.sees(Direction.up, new Point(1, 1), toGrid("101 111 111", 3),3);
        var bottom = day8.sees(Direction.up, new Point(1, 2), toGrid("101 101 111", 3),3);
        var partial = day8.sees(Direction.up, new Point(1, 1), toGrid("121 101 111", 3),3);
        assertThat(top, is(0));
        assertThat(center, is(1));
        assertThat(bottom, is(2));
        assertThat(partial, is(1));
    }

    private int gridView(int x, int y, String gridInput, int size) {
        return day8.viewCount(x, y, toGrid(gridInput, size), size);
    }

    private Integer[][] toGrid(String input, int size) {
        var grid = new Integer[size][size];
        var lines = input.split(" ");
        for (int y = 0; y < lines.length; y++) {
            var line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                grid[x][y] = Integer.parseInt(String.valueOf(line.charAt(x)));
            }
        }
        return grid;
    }
}
