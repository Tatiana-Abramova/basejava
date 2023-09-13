package webapp.storage;

import webapp.storage.stream.FileStorageHandler;
import webapp.storage.stream.ObjectStreamWriter;
import webapp.storage.stream.StorageType;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorageHandler(".\\resources", StorageType.FILE, new ObjectStreamWriter()).getStorage());
    }
}
