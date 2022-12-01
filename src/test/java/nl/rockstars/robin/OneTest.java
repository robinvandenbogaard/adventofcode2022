package nl.rockstars.robin;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for simple App.
 */
public class OneTest
{

    @Test
    void mostCalories() {
        var stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("One.txt");
        long result = One.process(stream, 1);
        assertThat(result, is(24000L));
    }

    @Test
    void top3totalCalories() {
        var stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("One.txt");
        long result = One.process(stream, 3);
        assertThat(result, is(45000L));
    }
}
