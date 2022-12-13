package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day12Test {

    private Day12 day;

    @BeforeEach
    void setUp() {
        day = new Day12(8,5);
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is("31"));
    }
}