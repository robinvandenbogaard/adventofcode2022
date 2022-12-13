package nl.rockstars.robin;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Day12 implements DayProcessor {

    private Graph<Point, DefaultWeightedEdge> graph;
    private final Point[][] matrix;
    private int parsingRow =0;
    private final int columns;
    private final int rows;

    private final List<Point> starts;
    private Point end;

    public Day12(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.matrix= new Point[rows][columns];
        this.starts = new ArrayList<>();
    }

    public static void main(String[] args) {
        new Day12(67, 41).go();
    }

    @Override
    public void beforeInput() {
        graph = GraphTypeBuilder
                .<Point, DefaultWeightedEdge>directed()
                .weighted(true).allowingMultipleEdges(true)
                .allowingSelfLoops(false).edgeClass(DefaultWeightedEdge.class).buildGraph();
    }

    @Override
    public void process(String line) {
        var split = line.toCharArray();
        for (int column = 0; column < columns; column++) {
            var c = split[column];
            Point p;
            if (c == 'S' || c == 'a') {
                starts.add(p = new Point(parsingRow, column, 'a'));
            }
            else if ( c == 'E') {
                end = p = new Point(parsingRow, column, 'z');
            }
            else {
                p = new Point(parsingRow, column, c);
            }
            matrix[parsingRow][column] = p;

        }
        parsingRow++;
    }

    @Override
    public void afterInput() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                graph.addVertex(matrix[row][column]);
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                addVertexAndEdges(matrix[row][column]);
            }
        }
    }

    private void addVertexAndEdges(Point point) {
        up(point).ifPresent(up->graph.addEdge(point, up));
        down(point).ifPresent(down->graph.addEdge(point, down));
        left(point).ifPresent(left->graph.addEdge(point, left));
        right(point).ifPresent(right->graph.addEdge(point, right));
    }

    Optional<Point> up(Point point) {
        return point.row == 0 ? Optional.empty() :
                Optional.of(matrix[point.row-1][point.column]).filter(point::allowsMoveTo);
    }

    Optional<Point> down(Point point) {
        return point.row == rows-1 ? Optional.empty() :
                Optional.of(matrix[point.row+1][point.column]).filter(point::allowsMoveTo);
    }

    Optional<Point> right(Point point) {
        return point.column == columns-1 ? Optional.empty() :
                Optional.of(matrix[point.row][point.column+1]).filter(point::allowsMoveTo);
    }

    Optional<Point> left(Point point) {
        return point.column == 0 ? Optional.empty() :
                Optional.of(matrix[point.row][point.column-1]).filter(point::allowsMoveTo);
    }

    @Override
    public Result getResult() {
        var dijkstraAlg = new DijkstraShortestPath<>(graph);
        int minLength = starts.stream()
                .map(dijkstraAlg::getPaths)
                .filter(Objects::nonNull)
                .map(paths->paths.getPath(end))
                .filter(Objects::nonNull)
                .mapToInt(GraphPath::getLength)
                .min()
                .orElseThrow();
        return new Result(minLength);
    }

    private record Point(int row, int column, int value) {
        public boolean allowsMoveTo(Point other) {
            return this.value+1 >= other.value ;
        }
    }
}
