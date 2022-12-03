package nl.rockstars.robin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 implements DayProcessor
{
    public static final String VALUES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final List<Group> groups = new ArrayList<>();
    private Group current = Group.EMPTY;
    private String priority = "";

    public static void main(String[] args )
    {
        new Day3().go();
    }

    @Override
    public void process(String line) {
        var rucksack = Rucksack.of(line);
        addToGroup(rucksack);
    }

    @Override
    public void afterInput() {
        for (var g : groups) {
            priority += g.commonLetter();
        }
    }

    private void addToGroup(Rucksack rucksack) {
        current = current.add(rucksack);
        if (current.isFull()) {
            groups.add(current);
            current = Group.EMPTY;
        }
    }

    @Override
    public Result getResult() {
        char[] xx = priority.toCharArray();
        int sum = 0;
        for (char x : xx) {
            sum += VALUES.indexOf(x)+1;
        }

        return new Result(sum+"");
    }


    record Rucksack(String allItems) {

        public static Rucksack of(String line) {
            return new Rucksack(line);
        }

        public Set<String> uniqueItems() {
            var result = new HashSet<String>();
            for (char x : allItems.toCharArray()) {
                result.add(String.valueOf(x));
            }
            return result;
        }

        public Rucksack retailAll(Rucksack rucksack) {
            var retained = new HashSet<>(uniqueItems());
            retained.retainAll(rucksack.uniqueItems());
            return new Rucksack(String.join("", retained));
        }
    }

    record Group(List<Rucksack> rucksacks) {

        public static Group EMPTY = new Group(new ArrayList<>());
        public static final int MAX = 3;

        Group add(Rucksack rucksack) {
            if (rucksacks.size()== MAX)
                throw new IllegalStateException("to many rucksacks");
            var newResults = new ArrayList<>(rucksacks);
            newResults.add(rucksack);
            return new Group(newResults);
        }

        boolean isFull() {
           return rucksacks.size() == MAX;
        }

        public String commonLetter() {

            return rucksacks.stream().reduce(Rucksack.of(VALUES), Rucksack::retailAll).allItems;
        }
    }

}
