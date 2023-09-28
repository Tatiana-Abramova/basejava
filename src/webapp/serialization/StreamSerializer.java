package webapp.serialization;

import webapp.model.Resume;

import java.io.*;
import java.util.Collection;
import java.util.Objects;

public interface StreamSerializer {

    void writeToFile(Resume resume, OutputStream os) throws IOException;

    Resume readFile(InputStream is) throws IOException;

    default <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithException<? super T> action) throws IOException {
        Objects.requireNonNull(action);
        dos.writeInt(collection.size());
        for (T element : collection) {
            action.write(element);
        }
    }

    default void readWithException(DataInputStream dis, ExecutorWithException action) throws IOException {
        Objects.requireNonNull(action);
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.read();
        }
    }
}
