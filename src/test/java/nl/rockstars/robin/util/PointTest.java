package nl.rockstars.robin.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PointTest {

    @Test
    void movePoint() {
        Point start = Point.of(0,0);
        assertThat(start.move(Direction.up), is(Point.of(0,-1)));
        assertThat(start.move(Direction.down), is(Point.of(0,1)));
        assertThat(start.move(Direction.left), is(Point.of(-1,0)));
        assertThat(start.move(Direction.right), is(Point.of(1,0)));
    }

    @Test
    void manhattanDistance() {
        assertThat(Point.of(-3,3).manhattanDistance(Point.of(3, -3)), is(12));
        assertThat(Point.of(5,0).manhattanDistance(Point.of(6, 4)), is(5));
    }
}