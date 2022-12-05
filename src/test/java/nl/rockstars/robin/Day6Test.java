package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day6Test {

    private Day6 day6;

    @BeforeEach
    void setUp() {
        day6 = new Day6();
    }

    @Test
    void inputTest() {
        var result = day6.go().output();
        assertThat(result, is(""));
    }

}