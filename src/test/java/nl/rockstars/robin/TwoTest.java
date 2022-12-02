package nl.rockstars.robin;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TwoTest
{

    @Test
    void step1() {
        var result = new Two().go("Two.txt").txt();
        assertThat(result, is("15"));
    }
}
