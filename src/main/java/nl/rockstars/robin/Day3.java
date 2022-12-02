package nl.rockstars.robin;

public class Day3 implements DayProcessor
{

    public static void main(String[] args )
    {
        new Day3().go();
    }

    @Override
    public void process(String line) {
    }

    @Override
    public Result getResult() {
        return new Result("");
    }
}
