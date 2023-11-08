package webapp.serialization;

import java.io.IOException;

@FunctionalInterface
public interface ExecutorWithException {
    void read() throws IOException;
}
