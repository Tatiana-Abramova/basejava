package webapp.storage.stream;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;

public class ObjectStreamWriter implements StreamWriter {

    @Override
    public void writeToFile(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume readFile(InputStream is) throws IOException {
        Object result;
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            result = ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
        return (Resume) result;
    }
}
