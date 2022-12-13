package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day13Test {

    private Day13 day = new Day13();

    @BeforeEach
    void setUp() {
        day = new Day13();
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is(""));
    }
}