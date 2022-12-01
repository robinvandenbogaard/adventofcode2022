package nl.rockstars.robin;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;


public class One
{
    public static void main( String[] args )
    {
        var stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("One.txt");
        long result = One.process(stream, 3);
        System.out.println( result );
    }

    public static long process(InputStream input, int topTier) {
        Scanner scanner = new Scanner(input);
        SortedSet<Long> max = new TreeSet<>(Comparator.reverseOrder());
        long sum = 0L;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.isBlank()) {
                max.add(sum);
                sum = 0L;
            } else {
                sum += Long.parseLong(line.trim());
            }
        }
        return max.stream().limit(topTier).mapToLong(Long::valueOf).sum();
    }
}
