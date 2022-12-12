package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day11Test {

    private Day11 day11 = new Day11();

    @BeforeEach
    void setUp() {
        day11 = new Day11();
    }

    @Test
    void getResult() {
        var result = day11.go().output();
        assertThat(result, is(""));
    }
}