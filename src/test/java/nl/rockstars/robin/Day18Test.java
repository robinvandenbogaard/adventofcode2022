package nl.rockstars.robin;

import nl.rockstars.robin.util.Point3D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.rockstars.robin.Day18.Box;
import static nl.rockstars.robin.Day18.Cube;
import static nl.rockstars.robin.Day18.CubeType.Void;
import static nl.rockstars.robin.Day18.CubeType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class Day18Test {

    private Day18 day;

    @BeforeEach
    void setUp() {
        day = new Day18(7);
    }

    @Test
    void getResult() {
        var result = day.go().output();
        assertThat(result, is("58"));
    }

    @Test
    void getResult2() {
        var day = new Day18(7) {
            @Override
            public String getFile() {
                return "18-2.txt";
            }
        };
        var result = day.go().output();
        assertThat(result, is("90"));
    }

    @Test
    void notCovered() {
        var world = new Box(6);
        world.add(Cube.steam(0,0,1));
        world.add(Cube.steam(0,0,-1));
        world.add(Cube.steam(0,1,0));
        world.add(Cube.steam(0,-1,0));
        world.add(Cube.steam(1,0,0));
        world.add(Cube.steam(-1,0,0));
        assertThat(Cube.boundary(0,0,0).countUncovered(world), is(6));
    }

    @Test
    void covered() {
        var world = new Box(6);
        world.add(Cube.steam(0,0,1));
        world.add(Cube.steam(0,0,-1));
        world.add(Cube.steam(0,1,0));
        world.add(Cube.steam(0,-1,0));
        world.add(Cube.steam(1,0,0));
        world.add(Cube.steam(-1,0,0));

        //empty covers
        world.add(Cube.empty(1,0,0));
        assertThat(Cube.boundary(0,0,0).countUncovered(world), is(5));

        //boundary covers
        world.add(Cube.boundary(-1,0,0));
        assertThat(Cube.boundary(0,0,0).countUncovered(world), is(4));

        //void does not cover
        world.add(new Cube(Point3D.of(0,-1,0), Void));
        assertThat(Cube.boundary(0,0,0).countUncovered(world), is(4));
    }

    @Test
    void as() {
        var cube = Cube.steam(0,0,1);
        assertThat(cube.as(Steam), is(Cube.steam(0,0,1)));
        assertThat(cube.as(Lava), is(Cube.boundary(0,0,1)));
        assertThat(cube.as(Empty), is(Cube.empty(Point3D.of(0,0,1))));
    }
}