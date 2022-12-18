package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.rockstars.robin.Day18.Cube;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day18Test {

    private Day18 day;

    @BeforeEach
    void setUp() {
        day = new Day18();
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is("64"));
    }

    @Test
    void notCovered() {
        assertThat(Cube.of(0,0,0).countUncovered(List.of()), is(6));
    }

    @Test
    void covered() {
        var cubes = List.of(Cube.of(0,0,1));
        assertThat(Cube.of(0,0,0).countUncovered(cubes), is(5));
    }
}