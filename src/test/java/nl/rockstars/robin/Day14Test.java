package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day14Test {

    private Day14 day;

    @BeforeEach
    void setUp() {
        day = new Day14();
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is("24"));
    }
}