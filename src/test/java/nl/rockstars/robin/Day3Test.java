package nl.rockstars.robin;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day3Test
{
    @Test
    void inputTest() {
        var result = new Day3().go().output();
        assertThat(result, is(""));
    }
}
