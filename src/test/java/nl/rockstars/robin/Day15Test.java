package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day15Test {

    private Day15 day;

    @BeforeEach
    void setUp() {
        day = new Day15();
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is(""));
    }
}