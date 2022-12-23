package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day16.Valve;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

class Day16Test {

    private Day16 day;

    @BeforeEach
    void setUp() {
        day = new Day16();
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is("1651"));
    }

    @Test
    void parseValve() {
        var text = "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB";
        var valve = Valve.of(text);

        assertThat(valve.name(), is("AA"));
        assertThat(valve.rate(), is(0));
        assertThat(valve.neighbours(), contains("DD", "II", "BB"));
    }

    @Test
    void parseValveSingular() {
        var text = "Valve JJ has flow rate=21; tunnel leads to valve II";
        var valve = Valve.of(text);

        assertThat(valve.name(), is("JJ"));
        assertThat(valve.rate(), is(21));
        assertThat(valve.neighbours(), contains("II"));
    }
}