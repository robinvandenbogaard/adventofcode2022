package nl.rockstars.robin;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day11 implements DayProcessor {

    private static final float BORED = 3;
    private long total;

    public static void main(String[] args) {
        new Day11().go();
    }

    @Override
    public void process(String line) {

    }

    @Override
    public void afterInput() {
        var monkeys = Monkeys.MAIN_MONKEYS;

        var lcm = getLcm(monkeys);

        for (long round = 0; round < 10000; round++) {
            for (var monkey : monkeys) {
                for (var item : monkey.items()) {
                    var worryLevel = monkey.inspect(item);
                    //worryLevel = (long) Math.floor( worryLevel / BORED);
                    worryLevel = worryLevel % lcm;
                    int throwToMonkeyAtIndex = monkey.test().apply(worryLevel);
                    monkeys.get(throwToMonkeyAtIndex).add(new Item(worryLevel));
                }
                monkey.clearItems();
            }
            if (round == 1 || round == 20 || round % 1000 == 0) {
                monkeys.forEach(System.out::println);
            }
        }
        total = monkeys.stream().map(Monkey::inspections).sorted(Collections.reverseOrder()).limit(2).reduce(1L, (i, j) -> i * j);

    }

    private static Long getLcm(List<Monkey> monkeys) {
        return monkeys.stream().map(Monkey::test).map(MonkeyTest::divider).reduce(1L, Day11::lcm);
    }

    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0)
            return 0;
        else {
            long gcd = gcd(number1, number2);
            return Math.abs(number1 * number2) / gcd;
        }
    }

    public static long gcd(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return number1 + number2;
        } else {
            long absNumber1 = Math.abs(number1);
            long absNumber2 = Math.abs(number2);
            long biggerValue = Math.max(absNumber1, absNumber2);
            long smallerValue = Math.min(absNumber1, absNumber2);
            return gcd(biggerValue % smallerValue, smallerValue);
        }
    }

    @Override
    public Result getResult() {
        return new Result(String.valueOf(total));
    }

    record Operation(Function<Long, Long> func) {

        public static Operation of(Function<Long, Long> func) {
            return new Operation(func);
        }
    }

    record MonkeyTest(long divider, int monkeyIndexTrue, int monkeyIndexFalse) {

        public static MonkeyTest of(long divider, int monkeyIndexTrue, int monkeyIndexFalse) {
            return new MonkeyTest(divider, monkeyIndexTrue, monkeyIndexFalse);
        }

        public int apply(long worryLevel) {
            return worryLevel % divider == 0 ? monkeyIndexTrue : monkeyIndexFalse;
        }
    }

    static final class Item {
        private final long worryLevel;

        Item(long worryLevel) {
            this.worryLevel = worryLevel;
        }

        public long worryLevel() {
            return worryLevel;
        }

        @Override
        public String toString() {
            return "Item[" +
                    "worryLevel=" + worryLevel + ']';
        }
    }

    static final class Monkey {
        private final List<Item> items;
        private final Operation operation;
        private final MonkeyTest test;

        private long inspections;

        Monkey(List<Item> items, Operation operation, MonkeyTest test) {
            this.items = items;
            this.operation = operation;
            this.test = test;
            this.inspections = 0;
        }

        long inspect(Item item) {
            inspections++;
            return operation.func.apply(item.worryLevel());
        }

        public void clearItems() {
            items.clear();
        }

        public void add(Item item) {
            this.items.add(item);
        }

        public List<Item> items() {
            return items;
        }

        public MonkeyTest test() {
            return test;
        }

        @Override
        public String toString() {
            return "Monkey[" +
                    "inspections=" + inspections + ", " +
                    "items=" + items + ']';
        }

        public long inspections() {
            return inspections;
        }
    }

    static class Monkeys {

        public static final Monkey t_m0 = new Monkey(new ArrayList<>(Stream.of(79, 98).map(Item::new).toList()), Operation.of((old) -> old * 19), MonkeyTest.of(23, 2, 3));

        public static final Monkey t_m1 = new Monkey(new ArrayList<>(Stream.of(54, 65, 75, 74).map(Item::new).toList()), Operation.of((old) -> old + 6), MonkeyTest.of(19, 2, 0));

        public static final Monkey t_m2 = new Monkey(new ArrayList<>(Stream.of(79, 60, 97).map(Item::new).toList()), Operation.of((old) -> old * old), MonkeyTest.of(13, 1, 3));

        public static final Monkey t_m3 = new Monkey(new ArrayList<>(Stream.of(74).map(Item::new).toList()), Operation.of((old) -> old + 3), MonkeyTest.of(17, 0, 1));

        public static final List<Monkey> TEST_MONKEYS = List.of(t_m0, t_m1, t_m2, t_m3);

        public static final Monkey m0 = new Monkey(new ArrayList<>(Stream.of(99, 63, 76, 93, 54, 73).map(Item::new).toList()), Operation.of((old) -> old * 11), MonkeyTest.of(2, 7, 1));

        public static final Monkey m1 = new Monkey(new ArrayList<>(Stream.of(91, 60, 97, 54).map(Item::new).toList()), Operation.of((old) -> old + 1), MonkeyTest.of(17, 3, 2));

        public static final Monkey m2 = new Monkey(new ArrayList<>(Stream.of(65).map(Item::new).toList()), Operation.of((old) -> old + 7), MonkeyTest.of(7, 6, 5));

        public static final Monkey m3 = new Monkey(new ArrayList<>(Stream.of(84, 55).map(Item::new).toList()), Operation.of((old) -> old + 3), MonkeyTest.of(11, 2, 6));

        public static final Monkey m4 = new Monkey(new ArrayList<>(Stream.of(86, 63, 79, 54, 83).map(Item::new).toList()), Operation.of((old) -> old * old), MonkeyTest.of(19, 7, 0));

        public static final Monkey m5 = new Monkey(new ArrayList<>(Stream.of(96, 67, 56, 95, 64, 69, 96).map(Item::new).toList()), Operation.of((old) -> old + 4), MonkeyTest.of(5, 4, 0));

        public static final Monkey m6 = new Monkey(new ArrayList<>(Stream.of(66, 94, 70, 93, 72, 67, 88, 51).map(Item::new).toList()), Operation.of((old) -> old * 5), MonkeyTest.of(13, 4, 5));

        public static final Monkey m7 = new Monkey(new ArrayList<>(Stream.of(59, 59, 74).map(Item::new).toList()), Operation.of((old) -> old + 8), MonkeyTest.of(3, 1, 3));

        public static final List<Monkey> MAIN_MONKEYS = List.of(m0, m1, m2, m3, m4, m5, m6, m7);
    }
}
