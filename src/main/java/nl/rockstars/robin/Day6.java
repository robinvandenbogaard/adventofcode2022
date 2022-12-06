package nl.rockstars.robin;

public class Day6 implements DayProcessor {

    public static final int MARK_LENGTH = 4;
    private String line;

    public static void main(String[] args) {
        new Day6().go();
    }

    public static int solve(String input) {

        for (int cursor = MARK_LENGTH; cursor < input.length(); cursor++) {
            var marker = input.substring(cursor-4, cursor);
            if (allUnique(marker))
                return cursor;
        }
        throw new IllegalStateException("cannot complete");
    }

    private static boolean allUnique(String str) {
        // If at any time we encounter 2 same
        // characters, return false
        for (int i = 0; i < str.length(); i++)
            for (int j = i + 1; j < str.length(); j++)
                if (str.charAt(i) == str.charAt(j))
                    return false;

        // If no duplicate characters encountered,
        // return true
        return true;
    }

    @Override
    public void process(String line) {
        this.line = line;
    }

    @Override
    public Result getResult() {
        return new Result(solve(line));
    }
}