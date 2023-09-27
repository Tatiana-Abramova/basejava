package webapp.serialization;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithException<T> {

    void write(T t) throws IOException;

}
