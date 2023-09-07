package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;

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

    @Override
    protected Resume readFile(File file) throws IOException, ClassNotFoundException {
        Object result;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            result = ois.readObject();
            if (!(result instanceof Resume)) {
                throw new StorageException("Wrong file content type", file.getName());
            }
        }
        return (Resume) result;
    }
}
