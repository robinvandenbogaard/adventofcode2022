package nl.rockstars.robin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day10.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Day10Test {

    private Day10 day10;

    @BeforeEach
    void setUp() {
        day10 = new Day10();
    }

    @Test
    void inputTest() {
        var result = day10.go().output();
        assertThat(result, is("""

                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######....."""));
    }

    @Test
    void noSignalStrengthBefore20thCycle() {
        var r = new Register(1);
        for (int i = 0; i < 20; i++) {
            assertThat(new Cycle(i, r).signalStrength(), is(0));
        }
    }

    @Test
    void every40thStartingFrom20thCycleYieldsSignalStrenght() {
        var register = new Register(10);
        assertThat(new Cycle(20, register).signalStrength(), is(200));
        assertThat(new Cycle(40, register).signalStrength(), is(0));
        assertThat(new Cycle(60, register).signalStrength(), is(600));
        assertThat(new Cycle(80, register).signalStrength(), is(0));
        assertThat(new Cycle(100, register).signalStrength(), is(1000));
        assertThat(new Cycle(18260, register).signalStrength(), is(182600));
    }

    @Test
    void noopCycleIncrementsByOne() {
        assertThat(
                Day10.noop(
                        new Cycle(0, new Register(11))
                ),
                is(
                        new Cycle(1, new Register(11))
                )
        );
    }

    @Test
    void addXCycleIncrementsBy2() {
        assertThat(
                Day10.addX(0,
                        new Cycle(0, new Register(100))
                ),
                contains(
                        new Cycle(1, new Register(100)),
                        new Cycle(2, new Register(100))
                )
        );
    }

    @Test
    void lastCycleWillInstructIncrementNextRegister() {
        assertThat(
                Day10.addX(10,
                        new Cycle(0, new Register(1000))
                ),
                contains(
                        new Cycle(1, new Register(1000)),
                        new Cycle(2, new Register(1000), 10)
                )
        );
    }

    @Test
    void incrementRegisterOnNext() {
        final var INSTRUCTED_INCREMENT = 10;
        var cycle = new Cycle(23, new Register(20), INSTRUCTED_INCREMENT);
        assertThat(
                cycle.next(),
                is(
                        new Cycle(24, new Register(30))
                )
        );
    }

    @Test
    void incrementAndInstruct() {
        final var INSTRUCTED_INCREMENT = 15;
        final var NEXT_INCREMENT = 5;
        var cycle = new Cycle(23, new Register(20), INSTRUCTED_INCREMENT);
        assertThat(
                cycle.next(NEXT_INCREMENT),
                is(
                        new Cycle(24, new Register(35), NEXT_INCREMENT)
                )
        );
    }

    @Test
    void sampleTests() {
        assertThat(new Cycle(20, new Register(21)).signalStrength(), is(420));
        assertThat(new Cycle(60, new Register(19)).signalStrength(), is(1140));
        assertThat(new Cycle(100, new Register(18)).signalStrength(), is(1800));
        assertThat(new Cycle(140, new Register(21)).signalStrength(), is(2940));
        assertThat(new Cycle(180, new Register(16)).signalStrength(), is(2880));
        assertThat(new Cycle(220, new Register(18)).signalStrength(), is(3960));
    }

    @Test
    void crtSpritePositionIsZero() {
        var crt = new Crt();
        assertThat(crt.spritePosition(), is(0));
    }

    @Test
    void updatesPositionToRegisterFromCycle() {
        var crt = new Crt();
        crt.draw(new Cycle(23, new Register(12)));
        assertThat(crt.spritePosition(), is(12));

        crt.draw(new Cycle(23, new Register(16)));
        assertThat(crt.spritePosition(), is(16));
    }

    @Test
    void drawsAPixelIfRegisterIsNearPosition() {
        var crt = new Crt();
        assertThat(crt.draw(new Cycle(1, new Register(2))), is("."));
        assertThat(crt.draw(new Cycle(2, new Register(2))), is("#"));
        assertThat(crt.draw(new Cycle(3, new Register(2))), is("#"));
        assertThat(crt.draw(new Cycle(4, new Register(2))), is("#"));
        assertThat(crt.draw(new Cycle(5, new Register(2))), is("."));
    }
}
