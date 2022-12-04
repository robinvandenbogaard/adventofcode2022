package nl.rockstars.robin;

import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day4Test {

    private Day4 day4;

    @BeforeEach
    void setUp() {
        day4 = new Day4();
    }

    @Test
    void inputTest() {
        var result = day4.go().output();
        assertThat(result, is("2"));
    }

    @Test
    void parseRange() {
        var range = day4.toRange("1-2");
        assertThat(range, is(Range.between(1, 2)));
        range = day4.toRange("3123-2221");
        assertThat(range, is(Range.between(3123, 2221)));
        assertThat("order is irrelevant", range, is(Range.between(2221, 3123)));
    }

    @Test
    void contains() {
        var r1 = Range.between(3,7);
        var r2 = Range.between(7,7);
        assertThat(day4.containsRange(r1, r2), is(true));
        assertThat("Either range is contained in the other", day4.containsRange(r2, r1), is(true));

        var r3 = Range.between(7,9);
        var r4 = Range.between(6,7);
        assertThat("One must fully contain the other", day4.containsRange(r3, r4), is(false));
    }

}
