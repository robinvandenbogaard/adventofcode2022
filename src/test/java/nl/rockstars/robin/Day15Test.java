package nl.rockstars.robin;

import nl.rockstars.robin.util.Point;
import nl.rockstars.robin.util.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static nl.rockstars.robin.Day15.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Day15Test {

    private Day15 day;

    @BeforeEach
    void setUp() {
        day = new Day15(20);
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is(String.valueOf(4000000*14+11)));
    }

    @Test
    void reachesY() {
        var beacon = Beacon.of(0,5);
        var sensor = Sensor.of(0,0, beacon);

        assertThat(sensor.reachesY(-6), is(false));
        for (int y = -5; y < 5; y++) {
            assertThat(sensor.reachesY(y), is(true));
        }
        assertThat(sensor.reachesY(6), is(false));
    }

    @Test
    void rangeAtY() {
        var beacon = Beacon.of(0,5);
        var sensor = Sensor.of(0,0, beacon);

        assertThrows(NoSuchElementException.class, ()->sensor.coverageAtY(6).get());
        assertThat(sensor.coverageAtY(-5).get(), is(Range.between(-0,0)));
        assertThat(sensor.coverageAtY(-4).get(), is(Range.between(-1, 1)));
        assertThat(sensor.coverageAtY(-3).get(), is(Range.between(-2, 2)));
        assertThat(sensor.coverageAtY(-2).get(), is(Range.between(-3, 3)));
        assertThat(sensor.coverageAtY(-1).get(), is(Range.between(-4, 4)));
        assertThat(sensor.coverageAtY(0).get(), is(Range.between(-5, 5)));
        assertThat(sensor.coverageAtY(1).get(), is(Range.between(-4, 4)));
        assertThat(sensor.coverageAtY(2).get(), is(Range.between(-3 ,3)));
        assertThat(sensor.coverageAtY(3).get(), is(Range.between(-2, 2)));
        assertThat(sensor.coverageAtY(4).get(), is(Range.between(-1, 1)));
        assertThat(sensor.coverageAtY(5).get(), is(Range.between(0, 0)));
        assertThrows(NoSuchElementException.class, ()->sensor.coverageAtY(-6).get());
    }

    @Test
    void tuningFrequence() {
        assertThat(Beacon.of(14, 11).tuningFrequence(), is(56000011L));
        assertThat(Beacon.of(14, 11).tuningFrequence(), is(56000011L));
    }


    /**
     * ..#..
     * .###.
     * ##S#B
     * .###.
     * ..#..
     */
    @Test
    void reachesPoint() {
        var beacon = Beacon.of(0,2);
        var sensor = Sensor.of(0,0, beacon);
        //completely of reach
        assertThat(sensor.reaches(Point.of(3,0)), is(false));

        assertThat(sensor.reaches(Point.of(-2,2)), is(false));
        assertThat(sensor.reaches(Point.of(1,2)), is(false));
        assertThat(sensor.reaches(Point.of(0,2)), is(true));

        assertThat(sensor.reaches(Point.of(-2,1)), is(false));
        assertThat(sensor.reaches(Point.of(1,1)), is(true));
        assertThat(sensor.reaches(Point.of(0,1)), is(true));

        assertThat(sensor.reaches(Point.of(-2,0)), is(true));
        assertThat(sensor.reaches(Point.of(1,0)), is(true));
        assertThat(sensor.reaches(Point.of(0,0)), is(true));
        assertThat(sensor.reaches(Point.of(2,0)), is(true));

        assertThat(sensor.reaches(Point.of(-2,1)), is(false));
        assertThat(sensor.reaches(Point.of(1,1)), is(true));
        assertThat(sensor.reaches(Point.of(0,1)), is(true));

        assertThat(sensor.reaches(Point.of(-1,2)), is(false));
        assertThat(sensor.reaches(Point.of(2,2)), is(false));
        assertThat(sensor.reaches(Point.of(0,2)), is(true));
    }

    @Test
    void getOuterBound() {
        var beacon = Beacon.of(0,2);
        var sensor = Sensor.of(0,0, beacon);

        var boundingPoint = sensor.getOutOfBoundsPoints();
        boundingPoint.forEach(p ->
                assertThat(p.toString(), sensor.reaches(p), is(false)));

    }
}