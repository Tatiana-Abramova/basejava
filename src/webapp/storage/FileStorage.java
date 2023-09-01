package webapp.storage;

import webapp.model.Resume;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/** Files based resume storage */
public class FileStorage extends AbstractFileStorage {

    protected FileStorage() {
        super(new File(".\\resources"));
    }

    protected FileStorage(File directory) {
        super(directory);
    }

    @Override
    protected void writeToFile(Resume resume, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(resume);
        }
    }
}
