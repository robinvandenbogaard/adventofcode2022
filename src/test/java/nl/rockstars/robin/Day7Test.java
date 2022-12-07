package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day7.Dir;
import static nl.rockstars.robin.Day7.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Day7Test {

    private Day7 day7;

    @BeforeEach
    void setUp() {
        day7 = new Day7();
    }

    @Test
    void inputTest() {
        var result = day7.go().output();
        assertThat(result, is("95437"));
    }

    @Test
    void dirSize() {
        var root = Dir.of("/", null);

        root.add(File.of(100L, "a"));
        assertThat(root.size(), is(100L));

        var b = Dir.of("b", root);
        root.add(b);
        b.add(File.of(200L, "x"));
        assertThat(root.size(), is(300L));
        assertThat(b.size(), is(200L));
    }

    @Test
    void allDirs() {
        var root = Dir.of("/", null);
        assertThat(root.subdirs(), is(empty()));

        var b = Dir.of("b", root);
        root.add(b);
        assertThat(root.subdirs(), hasItem(b));
        assertThat(b.subdirs(), is(empty()));

        var c = Dir.of("c", b);
        b.add(c);
        assertThat(root.subdirs(), hasItems(b, c));
        assertThat(b.subdirs(), hasItems(c));
        assertThat(c.subdirs(), is(empty()));
    }

    @Test
    void equality() {
        var root = Dir.of("/", null);

        assertThat(Dir.of("b", root), is((Dir.of("b", root))));

        assertThat(Dir.of("b", root), not(is((Dir.of("c", root)))));
        var notRoot = Dir.of("a", root);
        assertThat(Dir.of("b", root), not(is((Dir.of("b", notRoot)))));
    }
}