package nl.rockstars.robin;

import nl.rockstars.robin.util.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Day18 implements DayProcessor {


    public static void main(String[] args) {
        new Day18().go();
    }

    private final List<Cube> cubes = new ArrayList<>();

    @Override
    public void process(String line) {
        var split = line.split(",");
        var point = Cube.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        cubes.add(point);
    }

    @Override
    public Result getResult() {
        var result = cubes.stream().mapToInt(c->c.countUncovered(cubes)).sum();
        return new Result(result);
    }

    record Cube(Point3D p) {

        public static Cube of(int x, int y, int z) {
            return new Cube(Point3D.of(x,y,z));
        }

        public static Cube of(Point3D p) {
            return new Cube(p);
        }


        public int countUncovered(List<Cube> cubes) {
            var n = new ArrayList<>(neighbours());
            n.removeAll(cubes);
            return n.size();
        }

        public List<Cube> neighbours() {
            return List.of(Cube.of(p.north()), Cube.of(p.south()), Cube.of(p.east()), Cube.of(p.west()), Cube.of(p.up()), Cube.of(p.down()));
        }
    }
}
