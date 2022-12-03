package nl.rockstars.robin;

import nl.rockstars.robin.Day3.Compartment;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day3.Rucksack;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Day3Test {
    @Test
    void inputTest() {
        var result = new Day3().go().output();
        assertThat(result, is("157"));
    }

    @Test
    void rucksack() {
        var rucksack = Rucksack.of("vJrwpWtwJgWrhcsFMMfFFhFp");
        assertThat(rucksack.compartments(), contains(
                new Compartment("vJrwpWtwJgWr"),
                new Compartment("hcsFMMfFFhFp")));

        var rucksack2 = Rucksack.of("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL");
        assertThat(rucksack2.compartments(), contains(
                new Compartment("jqHRNqRjqzjGDLGL"),
                new Compartment("rsFMfFZSrLrFZsSL")));
    }

    @Test
    void rucksackCommonItem() {
        var rucksack = Rucksack.of("vJrwpWtwJgWrhcsFMMfFFhFp");
        assertThat(rucksack.getCommonLetter(), is("p"));

        var rucksack2 = Rucksack.of("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL");
        assertThat(rucksack2.getCommonLetter(), is("L"));
    }
}
