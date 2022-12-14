package nl.rockstars.robin;

import nl.rockstars.robin.Day13.PacketData;
import nl.rockstars.robin.Day13.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.rockstars.robin.Day13.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day13Test {

    private Day13 day = new Day13();

    @BeforeEach
    void setUp() {
        day = new Day13();
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is("13"));
    }

    @Test
    void pairOrder() {
        assertThat(new Pair<>(0, 1, 2).inOrder(), is(true));
        assertThat(new Pair<>(0, 1, 1).inOrder(), is(false));
        assertThat(new Pair<>(0, 2, 1).inOrder(), is(false));
    }

    @Test
    void createPacketData() {
        assertThat(PacketData.of("[9]"), is(new PacketData(List.of(new Value(9L)))));
        assertThat(PacketData.of("[]"), is(new PacketData(List.of())));
        assertThat(PacketData.of("[[]]"), is(new PacketData(List.of(new Value(new PacketData(List.of()))))));
    }

    @Test
    void comparePacketData() {
        var left = new PacketData(List.of());
        var right = new PacketData(List.of(new Value(3L)));
        assertThat(left.compareTo(right), is(-1));
        assertThat(right.compareTo(left), is(1));
    }
}