package nl.rockstars.robin;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

public class Day1Test
{

    @Test
    void mostCalories() {
        var result = new Day1(1).go().output();
        assertThat(result, is("24000"));
    }

    @Test
    void top3totalCalories() {
        var result = new Day1(3).go().output();
        assertThat(result, is("45000"));
    }
}
