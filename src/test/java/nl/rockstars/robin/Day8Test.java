package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertThat(result, is("21"));
    }

    @Test
    void edges() {
        assertThat(day8.getEdgeTreeCount(5), is(16));
        assertThat(day8.getEdgeTreeCount(100), is(396));
    }

    @Test
    void visibleHorizontalTrees() {
        day8.go();
        assertThat(day8.testH(1,1, day8.grid, day8.size), is(1));
        assertThat(day8.testH(2,2, day8.grid, day8.size), is(0));
        assertThat(day8.testH(3,3, day8.grid, day8.size), is(0));
    }

    @Test
    void visibleVerticalTrees() {
        day8.go();
        assertThat(day8.testV(1,1, day8.grid, day8.size), is(1));
        assertThat(day8.testV(2,2, day8.grid, day8.size), is(0));
        assertThat(day8.testV(3,3, day8.grid, day8.size), is(0));
    }

    @Test
    void visibility() {
        //surrounded
        assertThat(
                day8.testH(1,1, toGrid("111 101 111", 3), 3),
                is(0)
        );
        assertThat(
                day8.testV(1,1, toGrid("111 101 111", 3), 3),
                is(0)
        );

        //towering above all
        assertThat(
                day8.testH(1,1, toGrid("000 010 000", 3), 3),
                is(1)
        );
        assertThat(
                day8.testV(1,1, toGrid("000 010 000", 3), 3),
                is(1)
        );

        //towering but surrounded all
        assertThat(
                day8.testH(1,1, toGrid("22222 20002 20102 20002 22222", 5), 5),
                is(0)
        );
        assertThat(
                day8.testV(1,1, toGrid("22222 20002 20102 20002 22222", 5), 5),
                is(0)
        );

        //Only top
        assertThat(
                day8.testH(1,1, toGrid("111 010 000", 3), 3),
                is(1)
        );
        assertThat(
                day8.testV(1,1, toGrid("111 010 000", 3), 3),
                is(1)
        );

        //Only bottom
        assertThat(
                day8.testH(1,1, toGrid("000 010 111", 3), 3),
                is(1)
        );
        assertThat(
                day8.testV(1,1, toGrid("000 010 111", 3), 3),
                is(1)
        );

        //Only Left
        assertThat(
                day8.testH(1,1, toGrid("100 110 100", 3), 3),
                is(1)
        );
        assertThat(
                day8.testV(1,1, toGrid("100 110 100", 3), 3),
                is(1)
        );

        //Only Right
        assertThat(
                day8.testH(1,1, toGrid("001 011 001", 3), 3),
                is(1)
        );
        assertThat(
                day8.testV(1,1, toGrid("001 011 001", 3), 3),
                is(1)
        );

        //Equally tall
        assertThat(
                day8.testH(1,1, toGrid("000 000 000", 3), 3),
                is(0)
        );
        assertThat(
                day8.testV(1,1, toGrid("000 000 000", 3), 3),
                is(0)
        );
    }

    @Test
    void multi() {
        assertThat(
                day8.visibleInnerTrees(toGrid("0000 0120 0210 0000", 4), 4),
                is(4)
        );
        assertThat(
                day8.visibleInnerTrees(toGrid("0000 0120 0210 0000", 4), 4),
                is(4)
        );
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
