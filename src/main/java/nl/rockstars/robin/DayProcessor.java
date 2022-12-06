package nl.rockstars.robin;

import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public interface DayProcessor {

    default Result go() {
        var file = getFile();
        try (var stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(file)) {
            return go(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default String getFile() {
        return this.getClass().getSimpleName().substring(3)+".txt";
    }

    default Result go(InputStream file) {
        StopWatch sw = new StopWatch();
        sw.start();
        beforeInput();
        sw.suspend();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            sw.resume();
            process(scanner.nextLine());
            sw.suspend();
        }
        sw.resume();
        afterInput();

        var result = getResult();
        sw.stop();
        System.out.printf("%s: %s%n", sw.formatTime(), result.output());
        return result;
    }

    default void beforeInput() {}

    void process(String line);

    default void afterInput() {}

    Result getResult();
}
