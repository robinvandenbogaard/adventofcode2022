package nl.rockstars.robin;

import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day2.Type.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Day2Test
{

    @Test
    void inputTest() {
        var result = new Day2().go().output();
        assertThat(result, is("12"));
    }

    @Test
    void losesTo() {
        assertThat(Rock, is(Sciccor.losesTo()));
        assertThat(Paper, is(Rock.losesTo()));
        assertThat(Sciccor, is(Paper.losesTo()));
    }

    @Test
    void winsFrom() {
        assertThat(Rock.winsFrom(), is(Sciccor));
        assertThat(Paper.winsFrom(), is(Rock));
        assertThat(Sciccor.winsFrom(), is(Paper));
    }

    @Test
    void losesFrom() {
        assertThat(Rock.losesFrom(Rock), is(false));
        assertThat(Rock.losesFrom(Paper), is(true));
        assertThat(Rock.losesFrom(Sciccor), is(false));

        assertThat(Paper.losesFrom(Rock), is(false));
        assertThat(Paper.losesFrom(Paper), is(false));
        assertThat(Paper.losesFrom(Sciccor), is(true));

        assertThat(Sciccor.losesFrom(Rock), is(true));
        assertThat(Sciccor.losesFrom(Paper), is(false));
        assertThat(Sciccor.losesFrom(Sciccor), is(false));
    }
}
