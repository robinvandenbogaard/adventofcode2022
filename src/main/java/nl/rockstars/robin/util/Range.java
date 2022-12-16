package nl.rockstars.robin.util;

public record Range(int minimum, int maximum) {

    public static Range between(int v1, int v2) {
        return new Range(Math.min(v1, v2),Math.max(v1, v2));
    }

    public boolean contains(int value) {
        return minimum <= value && maximum >= value;
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public boolean isOverlappedBy(Range otherRange) {
        if (otherRange == null) {
            return false;
        }
        return otherRange.contains(minimum)
                || otherRange.contains(maximum)
                || contains(otherRange.minimum);
    }
}
