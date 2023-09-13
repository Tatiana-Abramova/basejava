package webapp.storage.stream;

import webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamWriter {

    void writeToFile(Resume resume, OutputStream os) throws IOException;

    Resume readFile(InputStream is) throws IOException;
}
