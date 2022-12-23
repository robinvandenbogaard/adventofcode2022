package nl.rockstars.robin;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 extends TraversalListenerAdapter<Day16.Valve, DefaultWeightedEdge> implements DayProcessor {

    private Graph<Valve, DefaultWeightedEdge> graph;
    Map<String, Valve> valves = new HashMap<>();
    private int tick = 0;

    public static void main(String[] args) {
        new Day16().go();
    }

    @Override
    public void beforeInput() {
        graph = GraphTypeBuilder.<Valve, DefaultWeightedEdge>directed().weighted(true).allowingMultipleEdges(true).allowingSelfLoops(false).edgeClass(DefaultWeightedEdge.class).buildGraph();
    }

    @Override
    public void process(String line) {
        Valve v = Valve.of(line);
        valves.put(v.name, v);
    }

    @Override
    public void afterInput() {
        valves.values().forEach(graph::addVertex);
        valves.values().forEach(v -> v.neighbours().forEach(name -> {
            var other = valves.get(name);
            graph.addEdge(v, other);
            graph.setEdgeWeight(v, other, other.rate);
        }));
    }

    @Override
    public Result getResult() {
        var algorithm = new DijkstraShortestPath<>(graph);
        var p = algorithm.getPaths(valves.get("AA"));
        System.out.println(p);
        return new Result("");
    }

    @Override
    public void connectedComponentFinished(ConnectedComponentTraversalEvent event) {
        System.out.println("connectedComponentFinished: "+ event.getType());
    }

    @Override
    public void connectedComponentStarted(ConnectedComponentTraversalEvent event) {
        System.out.println("connectedComponentStarted: "+ event.getType());
    }

    public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> event) {
        System.out.println("edgeTraversed: "+ event.getEdge());
    }


    @Override
    public void vertexTraversed(VertexTraversalEvent<Valve> event) {
        System.out.println("vertexTraversed: "+event.getVertex());
        event.getVertex().open(++tick);
        tick++;
    }

    public void vertexFinished(VertexTraversalEvent<Valve> event) {
        System.out.println("vertexFinished: "+event.getVertex());
    }

    static class Valve {
        private static final Pattern p =
                Pattern.compile("Valve ([A-Z]*?) has flow rate=([0-9]*?); tunnels? leads? to valves? (.*)");
        private final String name;
        private final int rate;
        private final String neighbours;
        private int openedAtTick = -1;

        public Valve(String name, int rate, String neighbours) {
            this.name = name;
            this.rate = rate;
            this.neighbours = neighbours;
        }


        public static Valve of(String input) {
            Matcher m = p.matcher(input);
            if (!m.matches())
                throw new IllegalArgumentException(input);

            var name = m.group(1);
            var rate = Integer.parseInt(m.group(2));
            var neighbours = m.group(3);

            return new Valve(name, rate, neighbours);
        }

        public String name() {
            return name;
        }

        public int rate() {
            return rate;
        }

        public List<String> neighbours() {
            return List.of(neighbours.split(", "));
        }

        public void open(int tick) {
            if (openedAtTick == -1) {
                openedAtTick = tick;
                System.out.printf("%s opened at %d %n", name, openedAtTick);
            } else {
                throw new IllegalStateException("can only open valve once!");
            }
        }

        @Override
        public String toString() {
            return "Valve{" +
                    "name='" + name + '\'' +
                    ", rate=" + rate +
                    ", openedAtTick=" + openedAtTick +
                    '}';
        }
    }
}
