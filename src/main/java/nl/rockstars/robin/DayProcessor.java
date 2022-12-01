package nl.rockstars.robin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public interface DayProcessor {

    default Result go(String file) {
        try (var stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(file)) {
            return go(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default Result go(InputStream file) {
        beforeInput();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            process(scanner.nextLine());
        }
        afterInput();
        var result = getResult();
        System.out.println(result.txt());
        return result;
    }

    default void beforeInput() {}

    void process(String line);

    default void afterInput() {}

    Result getResult();
}
