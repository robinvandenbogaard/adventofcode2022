package nl.rockstars.robin;

import java.util.List;

public class Day10 implements DayProcessor {

    Register register;
    Cycle cycle;
    int sum = 0;

    public static void main(String[] args) {
        new Day10().go();
    }

    public static List<Cycle> addX(int valueToAdd, Cycle cycle) {
        var cycle1 = cycle.next();
        var cycle2 = cycle.next().next(valueToAdd);
        return List.of(cycle1, cycle2);
    }

    @Override
    public void beforeInput() {
        register = new Register(1);
        cycle = new Cycle(0, register);
    }

    @Override
    public void process(String line) {
        if (line.startsWith("n")) {
            setCycle(noop(cycle));
        } else {
            int value = Integer.parseInt(line.split(" ")[1]);
            addX(value, cycle).forEach(this::setCycle);
        }
    }

    private void setCycle(Cycle cycle) {
        this.cycle = cycle;
        this.sum+=cycle.signalStrength();
    }

    static Cycle noop(Cycle cycle) {
        return cycle.next();
    }

    @Override
    public Result getResult() {
        return new Result(sum);
    }

    record Register(int value) {
        public Register add(int addition) {
            return new Register(value + addition);
        }
    }

    record Cycle(int value, Register register, int valueToAddOnNextCycle) {
        public Cycle(int value, Register register) {
            this(value, register, 0);
        }

        public Cycle next() {
            return new Cycle(value+1, register.add(valueToAddOnNextCycle), 0);
        }

        public Cycle next(int valueForNext) {
            return new Cycle(value+1, register.add(this.valueToAddOnNextCycle), valueForNext);
        }

        public int signalStrength() {
            return value % 40 == 20 ? value * register.value() : 0;
        }
    }
}