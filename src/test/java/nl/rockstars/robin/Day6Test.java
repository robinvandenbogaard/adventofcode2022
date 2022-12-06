package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day6.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day6Test {

    private Day6 day6;

    @BeforeEach
    void setUp() {
        day6 = new Day6(MARK_LENGTH);
    }

    @Test
    void inputTest() {
        var result = day6.go().output();
        assertThat(result, is("7"));
    }

    @Test
    void solve() {
        assertThat(new Day6(MARK_LENGTH).solve("bvwbjplbgvbhsrlpgdmjqwftvncz"), is(5));
        assertThat(new Day6(MARK_LENGTH).solve("nppdvjthqldpwncqszvftbrmjlhg"), is(6));
        assertThat(new Day6(MARK_LENGTH).solve("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), is(10));
        assertThat(new Day6(MARK_LENGTH).solve("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), is(11));

        assertThat(new Day6(MESSAGE_LENGTH).solve("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), is(19));
        assertThat(new Day6(MESSAGE_LENGTH).solve("bvwbjplbgvbhsrlpgdmjqwftvncz"), is(23));
        assertThat(new Day6(MESSAGE_LENGTH).solve("nppdvjthqldpwncqszvftbrmjlhg"), is(23));
        assertThat(new Day6(MESSAGE_LENGTH).solve("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), is(29));
        assertThat(new Day6(MESSAGE_LENGTH).solve("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), is(26));
    }


}