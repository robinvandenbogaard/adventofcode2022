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
        assertThat(result, is("36"));
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

    @Test
    void snakeDiagonalMove3() {
        var snake = new Snake(3);
        snake.move(Direction.right);
        snake.move(Direction.up);

        /*
         * .H.
         * #..
         **/
        assertThat(snake.tail.location, is(Point.of(0,0)));
        assertThat(snake.head.location, is(Point.of(1, -1)));
        assertThat(snake.head.next.location, is(Point.of(0, 0)));

        snake.move(Direction.right);
        snake.move(Direction.up);
        /*
         * ..H
         * .#.
         * T..
         **/
        assertThat(snake.tail.location, is(Point.of(0,0)));
        assertThat(snake.head.location, is(Point.of(2, -2)));
        assertThat(snake.head.next.location, is(Point.of(1, -1)));
    }

    @Test
    void snakeDiagonalMove4() {
        var snake = new Snake(10);
        var head = snake.head;
        var bp1 = head.next;
        var bp2 = bp1.next;
        var bp3 = bp2.next;
        var bp4 = bp3.next;
        var bp5 = bp4.next;
        var bp6 = bp5.next;

        snake.move(Direction.right);
        snake.move(Direction.right);
        snake.move(Direction.right);
        snake.move(Direction.right);
        /*
         * 4321H (4 covers 5, 6, 7, 8, 9, t)
         **/
        assertThat(head.location, is(Point.of(4, 0)));
        assertThat(bp1.location, is(Point.of(3, 0)));
        assertThat(bp2.location, is(Point.of(2, 0)));
        assertThat(bp3.location, is(Point.of(1, 0)));
        assertThat(bp4.location, is(Point.of(0, 0)));
        assertThat(bp5.location, is(Point.of(0, 0)));
        assertThat(bp6.location, is(Point.of(0, 0)));

        snake.move(Direction.up);
        snake.move(Direction.up);
        /*
         * ...... ......
         * ...... ......
         * ...... ....H.
         * ....H. .4321.
         * 4321.. 5.....
         **/
        assertThat(head.location, is(Point.of(4, -2)));
        assertThat(bp1.location, is(Point.of(4, -1)));
        assertThat(bp2.location, is(Point.of(3, -1)));
        assertThat(bp3.location, is(Point.of(2, -1)));
        assertThat(bp4.location, is(Point.of(1, -1)));
        assertThat(bp5.location, is(Point.of(0, 0)));

        snake.move(Direction.up);
        /*
         * ...... ...... ......
         * ...... ...... ....H.
         * ...... ....H. ....1.
         * ....H. .4321. .432..
         * 4321.. 5..... 5.....
         **/
        assertThat(head.location, is(Point.of(4, -3)));
        assertThat(bp1.location, is(Point.of(4, -2)));
        assertThat(bp2.location, is(Point.of(3, -1)));
        assertThat(bp3.location, is(Point.of(2, -1)));
        assertThat(bp4.location, is(Point.of(1, -1)));
        assertThat(bp5.location, is(Point.of(0, 0)));
    }

    private static Head head() {
        return Head.start();
    }

    private Tail tail() {
        return Tail.start();
    }

}
