package nl.rockstars.robin;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day13 implements DayProcessor {

    List<PacketData> packets = new ArrayList<>();

    public static void main(String[] args) {
        new Day13().go();
    }

    @Override
    public void process(String line) {
        if (Objects.equals(line, "")) {
            return;
        }

        packets.add(PacketData.of(line));
    }

    @Override
    public Result getResult() {
        packets.add(PacketData.SIGNAL1);
        packets.add(PacketData.SIGNAL2);
        var list = packets.stream().sorted(PacketData::compareTo).toList();
        list.forEach(System.out::println);
        return new Result((1+list.indexOf(PacketData.SIGNAL1)) * (1+list.indexOf(PacketData.SIGNAL2)));
    }

    public static final class PacketData implements Comparable<PacketData> {
        public static final PacketData SIGNAL1 = PacketData.of("[[2]]");
        public static final PacketData SIGNAL2 = PacketData.of("[[6]]");
        private final List<Value> values;

        public PacketData(List<Value> values) {
            this.values = values;
        }

        @SuppressWarnings("unchecked")
        public static PacketData of(String input) {
            JSONParser p = new JSONParser();
            try {
                JSONArray a = (JSONArray) p.parse(input);
                var values = new ArrayList<Value>();
                a.forEach(x -> {
                    if (x instanceof JSONArray) {
                        values.add(new Value(PacketData.of(((JSONArray) x).toJSONString())));
                    } else if (x instanceof Long) {
                        values.add(new Value((long) x));

                    }
                });
                return new PacketData(values);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int compareTo(PacketData o) {
            System.out.printf("  Compare %s vs  %s%n", this, o);
            var result = 0;
            int i;
            for (i = 0; i < values.size() && i < o.values.size() && result == 0; i++) {
                var left = values.get(i);
                var right = o.values.get(i);
                result = left.compareTo(right);
            }

            if (result == 0) {
                if (values.size() < o.values.size()) {
                    System.out.println("   Left side ran out of items, so inputs are in the right order");
                    return -1;
                } else if (values.size() > o.values.size()) {
                    System.out.println("   Right side ran out of items, so inputs are not in the right order");
                    return 1;
                }
            }

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (PacketData) obj;
            return Objects.equals(this.values, that.values);
        }

        @Override
        public int hashCode() {
            return Objects.hash(values);
        }

        @Override
        public String toString() {
            return ""+ values;
        }

        }

    public static final class Value implements Comparable<Value> {
        private final PacketData packetData;
        private final Long nr;

        public Value(PacketData packetData) {
            this.packetData = packetData;
            this.nr = null;
        }

        public Value(Long nr) {
            this.packetData = null;
            this.nr = nr;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Value) obj;
            return Objects.equals(this.packetData, that.packetData) &&
                    Objects.equals(this.nr, that.nr);
        }

        @Override
        public int hashCode() {
            return Objects.hash(packetData, nr);
        }

        @Override
        public String toString() {
            return "" + (packetData != null ? packetData : nr);
        }

        @Override
        public int compareTo(Value o) {
            System.out.printf("   - Comparing %s vs  %s%n", this, o);
            if (this.nr != null && o.nr != null)
                return this.nr.compareTo(o.nr);
            if (this.packetData != null && o.packetData != null)
                return this.packetData.compareTo(o.packetData);
            if (this.packetData != null && o.nr != null) {
                System.out.printf("   - Mixed Types; converting right to [] and retry comparison%n");
                return this.packetData.compareTo(new PacketData(List.of(o)));
            }
            if (this.nr != null && o.packetData != null) {
                System.out.printf("   - Mixed Types; converting left to [] and retry comparison%n");
                return new PacketData(List.of(this)).compareTo(o.packetData);
            }
            throw new IllegalStateException("Should not be reached");
        }
    }

}
