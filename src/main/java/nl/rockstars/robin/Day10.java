package nl.rockstars.robin;


import java.util.Arrays;
import java.util.List;

public class Day10 implements DayProcessor {

    Register register;
    Cycle cycle;
    int sum = 0;
    private Crt crt;

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
        crt = new Crt();
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
        crt.draw(cycle);
        this.sum+=cycle.signalStrength();
    }

    static Cycle noop(Cycle cycle) {
        return cycle.next();
    }

    @Override
    public Result getResult() {
        return new Result("\n"+format(crt.row()));
    }

    private String format(String row) {
        return String.join("\n", Arrays.asList(row.split("(?<=\\G.{40})")));
    }

    record Register(int value) {
        public Register add(int addition) {
            return new Register(value + addition);
        }
    }

    record Cycle(int number, Register register, int valueToAddOnNextCycle) {
        public Cycle(int value, Register register) {
            this(value, register, 0);
        }

        public Cycle next() {
            return new Cycle(number +1, register.add(valueToAddOnNextCycle), 0);
        }

        public Cycle next(int valueForNext) {
            return new Cycle(number +1, register.add(this.valueToAddOnNextCycle), valueForNext);
        }

        public int signalStrength() {
            return number % 40 == 20 ? number * register.value() : 0;
        }
    }

    static class Crt {

        private int position = 0;
        private String row = "";

        public int spritePosition() {
            return position;
        }

        public String draw(Cycle cycle) {
            position = cycle.register.value;
            var pixel = "";
            int pixelIndexToDraw = (cycle.number-1)%40;
            if (pixelIndexToDraw >= position-1 && pixelIndexToDraw <= position+1)
                 pixel = "#";
            else
                pixel = ".";
            row+=pixel;
            return pixel;
        }

        public String row() {
            return row;
        }
    }
}