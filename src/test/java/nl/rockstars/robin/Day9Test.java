package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day9.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day9Test {

    private Day9 day9;

    @BeforeEach
    void setUp() {
        day9 = new Day9();
    }

    @Test
    void inputTest() {
        var result = day9.go().output();
        assertThat(result, is("13"));
    }

    @Test
    void movePoint() {
        Point start = Point.of(0,0);
        assertThat(start.move(Direction.up), is(Point.of(0,-1)));
        assertThat(start.move(Direction.down), is(Point.of(0,1)));
        assertThat(start.move(Direction.left), is(Point.of(-1,0)));
        assertThat(start.move(Direction.right), is(Point.of(1,0)));
    }

    @Test
    void noNeedToFollowIfOnSameSpot() {
        Point expected = Point.of(0,0);
        assertThat(tail().follow(head()), is(expected));
    }

    @Test
    void noNeedToFollowIfAdjacent() {
        Point expected = Point.of(0,0);
        assertThat(tail().follow(head().up()), is(expected));
        assertThat(tail().follow(head().down()), is(expected));
        assertThat(tail().follow(head().left()), is(expected));
        assertThat(tail().follow(head().right()), is(expected));
        assertThat(tail().follow(head().up().left()), is(expected));
        assertThat(tail().follow(head().up().right()), is(expected));
        assertThat(tail().follow(head().down().left()), is(expected));
        assertThat(tail().follow(head().down().right()), is(expected));
    }

    @Test
    void follow() {
        assertThat(tail().follow(head().up().up()), is(Point.start.up()));
        assertThat(tail().follow(head().down().down()), is(Point.start.down()));
        assertThat(tail().follow(head().left().left()), is(Point.start.left()));
        assertThat(tail().follow(head().right().right()), is(Point.start.right()));
    }

    private static Head head() {
        return Head.start();
    }

    private Tail tail() {
        return Tail.start();
    }

}
