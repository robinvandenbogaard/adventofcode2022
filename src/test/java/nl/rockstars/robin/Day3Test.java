package nl.rockstars.robin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.rockstars.robin.Day3.Rucksack;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Day3Test {
    @Test
    void inputTest() {
        var result = new Day3().go().output();
        assertThat(result, is("70"));
    }

    @Test
    void findUniqueItems() {
        var rucksack = Rucksack.of("ttgJtRGJQctTZtZT");
        assertThat(rucksack.uniqueItems(), containsInAnyOrder("t","g","J","R","G","Q","c","T","Z"));
    }

    @Test
    void full() {
        var rucksack1 = Rucksack.of("aa");
        var rucksack2 = Rucksack.of("bb");
        var rucksack3 = Rucksack.of("cc");

        var group = new Day3.Group(List.of(rucksack1, rucksack2, rucksack3));
        assertThat(group.isFull(), is(true));
    }

    @Test
    void commonLetter() {
        var rucksack1 = Rucksack.of("az");
        var rucksack2 = Rucksack.of("zb");
        var rucksack3 = Rucksack.of("czc");

        var group = new Day3.Group(List.of(rucksack1, rucksack2, rucksack3));
        assertThat(group.commonLetter(), is("z"));
    }

    @Test
    void retain() {
        var rucksack1 = Rucksack.of("az");
        var rucksack2 = Rucksack.of("zb");

        assertThat(rucksack1.retailAll(rucksack2), is(new Rucksack("z")));
    }
}
