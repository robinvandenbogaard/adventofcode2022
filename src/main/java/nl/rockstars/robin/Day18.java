package nl.rockstars.robin;

import nl.rockstars.robin.util.Point3D;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Day18 implements DayProcessor {

    private final Box world;

    public Day18(int worldSize) {
        this.world = new Box(worldSize);
    }

    public static void main(String[] args) {
        new Day18(25).go();
    }

    @Override
    public void process(String line) {
        var split = line.split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        int z = Integer.parseInt(split[2]);
        var cube = Cube.boundary(x, y, z);

        world.add(cube);
    }

    @Override
    public Result getResult() {
        flood(Point3D.of(0,0,0));

        var lavaBlocks = world.getAll(CubeType.Lava);
        var sum = lavaBlocks.stream().mapToInt(c ->
                c.countUncovered(world)).sum();

        return new Result(sum);
    }

    private void flood(Point3D start) {
        var queue = new ArrayDeque<Point3D>();
        queue.add(start);
        var flooded = 0;
        while (!queue.isEmpty()) {
            var p = queue.pop();
            var type = world.get(p);
            if (type == CubeType.Empty) {
                var cube = Cube.steam(p);
                world.add(cube);
                flooded++;
                queue.addAll(cube.neighbours());
            }
        }
        System.out.println("flooded "+flooded+" cubes out of "+(7*7*7));
    }

    record Cube(Point3D p, CubeType type) {

        public static Cube boundary(int x, int y, int z) {
            return new Cube(Point3D.of(x, y, z), CubeType.Lava);
        }

        public static Cube empty(Point3D p) {
            return new Cube(p, CubeType.Empty);
        }

        public static Cube steam(int x, int y, int z) {
            return new Cube(Point3D.of(x, y, z), CubeType.Steam);
        }

        public static Cube of(Point3D p, CubeType type) {
            return new Cube(p, type);
        }

        public static Cube steam(Point3D p) {
            return new Cube(p, CubeType.Steam);
        }

        public static Cube empty(int x, int y, int z) {
            return Cube.of(Point3D.of(x, y, z), CubeType.Empty);
        }

        public int countUncovered(Box world) {
            List<Point3D> n = new ArrayList<>(neighbours());
            n.removeIf(pos -> {
                var x = world.get(pos);
                return x != CubeType.Steam && x!= CubeType.Void;
            });
            return n.size();
        }

        public List<Point3D> neighbours() {
            return Stream.of(
                    p.north(),
                    p.south(),
                    p.east(),
                    p.west(),
                    p.up(),
                    p.down()
            ).toList();
        }

        public Cube as(CubeType type) {
            return new Cube(p, type);
        }
    }

    enum CubeType {
        Lava, Steam, Empty, Void
    }

    public static class Box {

        static int margin = 4;

        static int OFFSET = 2;

        CubeType[][][] world;
        private final int worldSize;

        public Box(int worldSize) {
            world = new CubeType[worldSize + margin][worldSize + margin][worldSize + margin];
            this.worldSize = worldSize;
            for (int x = 0; x < worldSize+margin; x++) {
                for (int y = 0; y < worldSize+margin; y++) {
                    for (int z = 0; z < worldSize+margin; z++) {
                        world[x][y][z] = CubeType.Empty;
                    }
                }
            }
        }

        public void forEachWorld(Consumer<Point3D> s) {
            for (int x = 0; x < worldSize+margin; x++) {
                for (int y = 0; y < worldSize+margin; y++) {
                    for (int z = 0; z < worldSize+margin; z++) {
                        s.accept(Point3D.of(x, y, z));
                    }
                }
            }
        }

        public List<Cube> getAll(CubeType type) {
            var result = new ArrayList<Cube>();
            forEachWorld(point->{
                CubeType cubeType = get(point);
                if (cubeType == type)
                    result.add(Cube.of(point, type));
            });
            return result;
        }

        public void add(Cube cube) {
            world[cube.p.x() + OFFSET][cube.p.y() + OFFSET][cube.p.z() + OFFSET] = cube.type;
        }

        public CubeType get(Point3D p) {
            var x = p.x() + OFFSET;
            int y = p.y() + OFFSET;
            int z = p.z() + OFFSET;
            var bound = worldSize+margin;
            if (x<0 || y < 0 || z < 0 || x >= bound || y >= bound || z >= bound)
                return CubeType.Void;
            return world[x][y][z];
        }
    }
}
