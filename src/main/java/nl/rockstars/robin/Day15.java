package nl.rockstars.robin;

import nl.rockstars.robin.util.Point;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Day15 implements DayProcessor {

    private final List<Beacon> beacons = new ArrayList<>();
    private final List<Sensor> sensors = new ArrayList<>();
    private final int targetY;

    public Day15(int targetY) {
        this.targetY = targetY;
    }

    public static void main(String[] args) {
        new Day15(2_000_000).go();
    }

    @Override
    public void process(String line) {
        var bx = line.substring(line.lastIndexOf("x=")+2, line.lastIndexOf(','));
        var by = line.substring(line.lastIndexOf(',')+4);
        var beacon = Beacon.of(Integer.parseInt(bx), Integer.parseInt(by));
        beacons.add(beacon);

        var sx = line.substring(12, line.indexOf(','));
        var sy = line.substring(line.indexOf(',')+4, line.indexOf(':'));
        var sensor = Sensor.of(Integer.parseInt(sx), Integer.parseInt(sy), beacon);
        sensors.add(sensor);
    }

    @Override
    public Result getResult() {
        return new Result(notReached());
    }

    private int notReached() {
        Predicate<Sensor> reachesY = (s)->s.reachesY(targetY);
        var inRangeSensors = sensors.stream().filter(reachesY).toList();

        var min = inRangeSensors.stream().map(s->s.coverageAtY(targetY)).mapToInt(Range::getMinimum).min().orElseThrow();
        var max = inRangeSensors.stream().map(s->s.coverageAtY(targetY)).mapToInt(Range::getMaximum).max().orElseThrow();
        System.out.printf("%d <--> %d%n", min, max);

        return max - min;
    }

    record Sensor(Point location, Beacon beacon, Range<Integer> horizontal, Range<Integer> vertical) {

        public static Sensor of(int x, int y, Beacon beacon) {
            var location = Point.of(x, y);
            var r = location.manhattanDistance(beacon.location);
            var horizontal = Range.between(x - r, x + r);
            var vertical = Range.between(y - r, y + r);
            return new Sensor(location, beacon, horizontal, vertical);
        }

        public boolean reachesY(int targetY) {
            return vertical.contains(targetY);
        }

        public Range<Integer> coverageAtY(int y) {
            var hgap = Math.abs(location.y()-y);
            if (!horizontal.contains(hgap+location.x()))
                throw new IllegalStateException("Should not occur, filter in range sensors properly");

            return Range.between(
                    horizontal.getMinimum()+(hgap),
                    horizontal.getMaximum()-(hgap)
            );
        }
    }

    record Beacon(Point location) {

        public static Beacon of(int x, int y) {
            return new Beacon(Point.of(x, y));
        }
    }
}
