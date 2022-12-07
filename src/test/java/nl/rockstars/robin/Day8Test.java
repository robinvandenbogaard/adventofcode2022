package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day8Test {

    private Day8 day8;

    @BeforeEach
    void setUp() {
        day8 = new Day8();
    }

    @Test
    void inputTest() {
        var result = day8.go().output();
        assertThat(result, is(""));
    }
}