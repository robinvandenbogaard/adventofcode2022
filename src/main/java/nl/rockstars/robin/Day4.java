package nl.rockstars.robin;

import nl.rockstars.robin.util.Range;

import java.util.Arrays;

public class Day4 implements DayProcessor
{

    private int sum = 0;

    public static void main(String[] args )
    {
        new Day4().go();
    }

    @Override
    public void process(String line) {
        var pairs = line.split(",");
        var ranges = Arrays.stream(pairs).map(this::toRange).toList();
        if (ranges.size() != 2)
            throw new IllegalStateException("Unexpected input");

        sum += containsRange(ranges.get(0), ranges.get(1)) ? 1 : 0;
    }

    @Override
    public Result getResult() {
        return new Result(sum);
    }

    Range toRange(String input) {
        var numbers = input.split("-");
        var start = Integer.parseInt(numbers[0]);
        var end = Integer.parseInt(numbers[1]);
        return Range.between(start, end);
    }

    boolean containsRange(Range r1, Range r2) {
        return r1.isOverlappedBy(r2);
    }
}
