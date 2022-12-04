package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day5Test {

    private Day5 day5;

    @BeforeEach
    void setUp() {
        day5 = new Day5();
    }

    @Test
    void inputTest() {
        var result = day5.go().output();
        assertThat(result, is(""));
    }
}