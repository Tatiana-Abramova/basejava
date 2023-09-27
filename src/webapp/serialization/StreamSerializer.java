package webapp.serialization;

import webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Objects;

public interface StreamSerializer {

    void writeToFile(Resume resume, OutputStream os) throws IOException;

    Resume readFile(InputStream is) throws IOException;

    default <T> void forEachWithException(Collection<T> collection, ConsumerWithException<? super T> action) throws IOException {
        Objects.requireNonNull(action);
        for (T element : collection) {
            action.write(element);
        }
    }
}
