package nl.rockstars.robin;

public record Result(String output) {
    public static final Result EMPTY = new Result("");
}
