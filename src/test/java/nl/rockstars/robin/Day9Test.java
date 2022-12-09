package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day9Test {

    private Day9 day9;

    @BeforeEach
    void setUp() {
        day9 = new Day9();
    }

    @Test
    void inputTest() {
        var result = day9.go().output();
        assertThat(result, is("13"));
    }
}
