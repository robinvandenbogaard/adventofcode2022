package nl.rockstars.robin.util;

public record Direction(int x, int y) {
    public static Direction up = new Direction(0, -1);
    public static Direction down = new Direction(0, 1);
    public static Direction left = new Direction(-1, 0);
    public static Direction right = new Direction(1, 0);

    public static Direction of(String key) {
        return switch (key) {
            case "U" -> up;
            case "D" -> down;
            case "R" -> right;
            case "L" -> left;
            default -> throw new IllegalArgumentException(key);
        };
    }

    public Direction multiply(int multiplier) {
        return new Direction(x*multiplier, y*multiplier);
    }
}
