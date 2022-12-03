package nl.rockstars.robin;

import java.util.List;

public class Day3 implements DayProcessor
{
    private String priority = "";

    public static void main(String[] args )
    {
        new Day3().go();
    }

    @Override
    public void process(String line) {
        var rucksack = Rucksack.of(line);
        priority += rucksack.getCommonLetter();
    }

    @Override
    public Result getResult() {
        char[] xx = priority.toCharArray();
        int sum = 0;
        for (char x : xx) {
            String VALUES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            sum += VALUES.indexOf(x)+1;
        }

        return new Result(sum+"");
    }


    record Rucksack(List<Compartment> compartments) {
        final static int COMPARTMENTS = 2;

        public static Rucksack of(String line) {
            var middle = line.length()/ COMPARTMENTS;
            var compartment1 = line.substring(0,middle);
            var compartment2 = line.substring(middle);
            return new Rucksack(List.of(new Compartment(compartment1), new Compartment(compartment2)));
        }

        public String getCommonLetter() {
            return compartments.stream().reduce(Compartment.BLANK, Compartment::findMatching).items;
        }
    }

    record Compartment(String items) {
        static Compartment BLANK = new Compartment(null);

        public Compartment findMatching(Compartment other) {
            if (this == BLANK)
                return other;

            for (var character : other.items.toCharArray()) {
                if (this.items.contains(String.valueOf(character)))
                    return new Compartment(String.valueOf(character));
            }

            throw new IllegalStateException("No match found");
        }

    }

}
