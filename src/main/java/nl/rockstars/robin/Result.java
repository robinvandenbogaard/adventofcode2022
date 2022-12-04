package nl.rockstars.robin;

public record Result(String output) {
    public static final Result EMPTY = new Result("");

    public Result(int output) {
        this(output+"");
    }
}
