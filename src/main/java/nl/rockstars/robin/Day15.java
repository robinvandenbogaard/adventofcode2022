package nl.rockstars.robin;

import nl.rockstars.robin.util.Point;
import nl.rockstars.robin.util.Range;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Day15 implements DayProcessor {
    private final List<Sensor> sensors = new ArrayList<>();
    private final long searchSpace;
    private final Range searchSpaceRange;
    private final StopWatch sw;

    public Day15(int searchSpace) {
        this.sw = new StopWatch();
        this.searchSpace = searchSpace;
        this.searchSpaceRange = Range.between(0, searchSpace);
    }

    public static void main(String[] args) {
        new Day15(4000000).go();
    }

    @Override
    public void process(String line) {
        var bx = line.substring(line.lastIndexOf("x=")+2, line.lastIndexOf(','));
        var by = line.substring(line.lastIndexOf(',')+4);
        var beacon = Beacon.of(Integer.parseInt(bx), Integer.parseInt(by));

        var sx = line.substring(12, line.indexOf(','));
        var sy = line.substring(line.indexOf(',')+4, line.indexOf(':'));
        var sensor = Sensor.of(Integer.parseInt(sx), Integer.parseInt(sy), beacon);
        sensors.add(sensor);
    }

    @Override
    public Result getResult() {
        return new Result(String.valueOf(notReached()));
    }

    private long notReached() {
        Predicate<Sensor> reachesY = (s)->s.inbounds(searchSpaceRange);
        var inRangeSensors = sensors.stream().filter(reachesY).toList();

        // only scan the edges, looked this hint up on the web.
        var edges = sensors.stream()
                .flatMap((Sensor sensor) -> sensor.getOutOfBoundsPoints().stream())
                .filter(p->p.x()>=0 && p.x() <=searchSpace && p.y()>=0 && p.y() <=searchSpace ).toList();

        System.out.println("Edges: "+edges.size());

        sw.start();
        Beacon b = edges.stream().filter(p->inRangeSensors.stream().noneMatch(s->s.reaches(p))).map(Beacon::new).findFirst().orElse(null);
        sw.stop();

        if (b == null)
            throw new IllegalStateException("No beacon found");

        System.out.printf("No reach at %s%n", b);
        return b.tuningFrequence();
    }

    record Sensor(Point location, Beacon beacon, Range horizontal, Range vertical, int radius) {

        public static Sensor of(int x, int y, Beacon beacon) {
            var location = Point.of(x, y);
            var r = location.manhattanDistance(beacon.location);
            var horizontal = Range.between(x - r, x + r);
            var vertical = Range.between(y - r, y + r);
            return new Sensor(location, beacon, horizontal, vertical, r);
        }

        public boolean reachesY(int targetY) {
            return vertical.contains(targetY);
        }

        public Optional<Range> coverageAtY(int y) {
            var hgap = Math.abs(location.y()-y);
            if (!horizontal.contains(hgap+location.x()))
                return Optional.empty();

            return Optional.of(Range.between(
                    horizontal.getMinimum() + (hgap),
                    horizontal.getMaximum() - (hgap)
            ));
        }

        public Optional<Range> coverageAtX(int x) {
            var vgap = Math.abs(location.x()-x);
            if (!vertical.contains(vgap+location.y()))
                return Optional.empty();

            return Optional.of(Range.between(
                    vertical.getMinimum() + (vgap),
                    vertical.getMaximum() - (vgap)
            ));
        }

        public boolean inbounds(Range squareBounds) {
            return squareBounds.isOverlappedBy(horizontal) || squareBounds.isOverlappedBy(vertical);
        }

        public boolean reaches(Point p) {
            return
                coverageAtX(p.x())
                    .map(r->r.contains(p.y()))
                    .orElse(false)
                &&
                coverageAtY(p.y())
                    .map(r-> r.contains(p.x()))
                    .orElse(false);
        }

        public List<Point> getOutOfBoundsPoints() {
            var points = new ArrayList<Point>();
            var radius = radius()+1;
            for (int dx = -radius; dx < radius; dx++) {
                var dy = radius - Math.abs(dx);
                points.add(Point.of(location.x()+dx, location.y()+dy));
                points.add(Point.of(location.x()+dx, location.y()-dy));
            }
            return points;
        }
    }

    record Beacon(Point location) {

        public static Beacon of(int x, int y) {
            return new Beacon(Point.of(x, y));
        }

        public long tuningFrequence() {
            return location().x() * 4000000L + location().y();
        }
    }
}
