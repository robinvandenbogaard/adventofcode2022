package nl.rockstars.robin;

import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day15.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Day15Test {

    private Day15 day;

    @BeforeEach
    void setUp() {
        day = new Day15(10);
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is("26"));
    }

    @Test
    void reaches() {
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

        assertThrows(IllegalStateException.class, ()->sensor.coverageAtY(6));
        assertThat(sensor.coverageAtY(-5), is(Range.between(-0,0)));
        assertThat(sensor.coverageAtY(-4), is(Range.between(-1, 1)));
        assertThat(sensor.coverageAtY(-3), is(Range.between(-2, 2)));
        assertThat(sensor.coverageAtY(-2), is(Range.between(-3, 3)));
        assertThat(sensor.coverageAtY(-1), is(Range.between(-4, 4)));
        assertThat(sensor.coverageAtY(0), is(Range.between(-5, 5)));
        assertThat(sensor.coverageAtY(1), is(Range.between(-4, 4)));
        assertThat(sensor.coverageAtY(2), is(Range.between(-3 ,3)));
        assertThat(sensor.coverageAtY(3), is(Range.between(-2, 2)));
        assertThat(sensor.coverageAtY(4), is(Range.between(-1, 1)));
        assertThat(sensor.coverageAtY(5), is(Range.between(0, 0)));
        assertThrows(IllegalStateException.class, ()->sensor.coverageAtY(-6));
    }
}