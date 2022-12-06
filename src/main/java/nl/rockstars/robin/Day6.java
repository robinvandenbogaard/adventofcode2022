package nl.rockstars.robin;

public class Day6 implements DayProcessor {

    public static final int MESSAGE_LENGTH = 14;
    public static final int MARK_LENGTH = 4;
    private String line;
    private final int markLength;

    public Day6(int markLength) {
        this.markLength = markLength;
    }

    public static void main(String[] args) {
        new Day6(MESSAGE_LENGTH).go();
    }

    public int solve(String input) {

        for (int cursor = markLength; cursor < input.length(); cursor++) {
            var marker = input.substring(cursor-markLength, cursor);
            if (allUnique(marker))
                return cursor;
        }
        throw new IllegalStateException("cannot complete");
    }

    private static boolean allUnique(String str) {
        for (int i = 0; i < str.length(); i++)
            for (int j = i + 1; j < str.length(); j++)
                if (str.charAt(i) == str.charAt(j))
                    return false;

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