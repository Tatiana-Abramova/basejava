package webapp.storage.stream;

import webapp.exception.StorageException;
import webapp.storage.Storage;

import java.io.File;

public class FileStorageHandler {

    private Storage storage;
    private StreamWriter writer;


    public FileStorageHandler(String directory, StorageType type, StreamWriter writer) {
        switch (type) {
            case PATH -> storage = new PathStorage(directory, writer);
            case FILE -> storage = new FileStorage(new File(directory), writer);
            default -> throw new StorageException("Unknown storage type", directory);
        }
        this.writer = writer;
    }

    public Storage getStorage() {
        return storage;
    }
}
