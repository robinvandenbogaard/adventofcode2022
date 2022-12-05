package nl.rockstars.robin;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 implements DayProcessor {

    private boolean parseStacks = true;
    private Stacks stacks;

    public static void main(String[] args) {
        new Day5().go();
    }

    @Override
    public void beforeInput() {
        this.stacks = new Stacks();
    }

    @Override
    public void process(String line) {
        if (parseStacks) {
            if (line.isEmpty()) {
                parseStacks = false;
                stacks.reverse();
                return;
            }

            stacks.addAll(Crate.of(line));
        } else {
            stacks.execute(Instruction.of(line));
        }
    }

    @Override
    public Result getResult() {
        return new Result(stacks.getTops());
    }

    record Instruction(int amount, int from, int to) {
        public static Instruction of(String line) {
            var tokens = line.split(" ");
            return new Instruction(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[5]));
        }
    }
    record Crate(int stackNumber, String content) {
        public static List<Crate> of(String line) {
            List<Crate> result = new ArrayList<>();
            var nr = 1;
            var cursor = 1;
            while (cursor < line.length()) {
               var token = String.valueOf(line.charAt(cursor));
               if (!" ".equals(token)) {
                   result.add(new Crate(nr, token));
               }
               cursor+=4;
               nr++;
            }
            return result;
        }
    }

    static class Stacks {

        Map<Integer, Stack<Crate>> stacks = new HashMap<>();

        public void execute(Instruction instruction) {
            var crates = new ArrayList<Crate>();
            for (int crateNr = 0; crateNr < instruction.amount; crateNr++) {
                crates.add(stacks.get(instruction.from()).pop());
            }
            Collections.reverse(crates);
            crates.forEach(crate->stacks.get(instruction.to()).push(crate));
        }

        public void addAll(List<Crate> crates) {
            crates.forEach(crateToAdd-> {
                var stackToUpdate = stacks.computeIfAbsent(crateToAdd.stackNumber(), (nr)->new Stack<>());
                stackToUpdate.push(crateToAdd);
            });
        }

        public void reverse() {
            stacks.values().forEach(Collections::reverse);
        }

        public String getTops() {
            return stacks.values().stream().map(crates -> crates.isEmpty() ? new Crate(0, " ") : crates.peek()).map(Crate::content).collect(Collectors.joining());
        }
    }
}