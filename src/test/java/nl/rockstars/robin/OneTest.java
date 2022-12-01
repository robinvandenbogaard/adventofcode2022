package nl.rockstars.robin;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

public class OneTest
{

    @Test
    void mostCalories() {
        var result = new One(1).go("One.txt").txt();
        assertThat(result, is("24000"));
    }

    @Test
    void top3totalCalories() {
        var result = new One(3).go("One.txt").txt();
        assertThat(result, is("45000"));
    }
}
