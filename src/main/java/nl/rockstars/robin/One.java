package nl.rockstars.robin;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;


public class One implements DayProcessor
{
    private final int topTier;
    private final SortedSet<Long> max;
    private long sum;

    public static void main(String[] args )
    {
        new One(3).go("One.txt");
    }

    public One(int topTier) {
        this.max = new TreeSet<>(Comparator.reverseOrder());
        this.topTier = topTier;
        this.sum = 0L;
    }

    @Override
    public void process(String line) {
        if (line.isBlank()) {
            max.add(sum);
            sum = 0L;
        } else {
            sum += Long.parseLong(line.trim());
        }
    }

    @Override
    public Result getResult() {
        var tierSum = max.stream().limit(topTier).mapToLong(Long::valueOf).sum();
        return new Result(tierSum+"");
    }

}
