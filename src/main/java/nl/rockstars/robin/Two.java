package nl.rockstars.robin;

public class Two implements DayProcessor
{

    public static void main(String[] args )
    {
        new Two().go("Two.txt");
    }

    @Override
    public void process(String line) {

    }

    @Override
    public Result getResult() {
        return new Result("");
    }
}
