package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.rockstars.robin.Day5.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Day5Test {

    private Day5 day5;

    @BeforeEach
    void setUp() {
        day5 = new Day5();
    }

    @Test
    void inputTest() {
        var result = day5.go().output();
        assertThat(result, is("MCD"));
    }

    @Test
    void parseInstruction() {
        assertThat(Instruction.of("move 2 from 1 to 7"), is(new Instruction(2, 1, 7)));
        assertThat(Instruction.of("move 1 from 5 to 2"), is(new Instruction(1, 5, 2)));
    }

    @Test
    void parseStacks() {
        assertThat(Crate.of("    [D]"), contains(new Crate(2, "D")));
        assertThat(Crate.of("[N] [C]"), contains(new Crate(1, "N"), new Crate(2, "C")));
        assertThat(Crate.of("[Z] [M] [P]"), contains(new Crate(1, "Z"),new Crate(2, "M"),new Crate(3, "P")));
    }

    @Test
    void mergeStacks() {
        var stack = new Stacks();
        stack.addAll(List.of(new Crate(1, "Z"),new Crate(2, "M"),new Crate(3, "P"), new Crate(1, "X")));
        assertThat(stack.stacks.get(1), contains(new Crate(1, "Z"), new Crate(1, "X")));
        assertThat(stack.stacks.get(2), contains(new Crate(2, "M")));
        assertThat(stack.stacks.get(3), contains(new Crate(3, "P")));

        stack.reverse();
        assertThat(stack.stacks.get(1), contains(new Crate(1, "X"), new Crate(1, "Z")));
        assertThat(stack.stacks.get(2), contains(new Crate(2, "M")));
        assertThat(stack.stacks.get(3), contains(new Crate(3, "P")));
    }
    @Test
    void tops() {
        var stack = new Stacks();
        stack.addAll(List.of(new Crate(1, "X")));
        assertThat(stack.getTops(), is("X"));

        stack.addAll(List.of(new Crate(2, "Z")));
        assertThat(stack.getTops(), is("XZ"));

        stack.addAll(List.of(new Crate(3, "A")));
        stack.execute(new Instruction(1, 2,1));
        assertThat(stack.getTops(), is("Z A"));
    }



}