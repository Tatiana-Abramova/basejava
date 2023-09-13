package webapp.storage;

import webapp.storage.stream.FileStorageHandler;
import webapp.storage.stream.ObjectStreamWriter;
import webapp.storage.stream.StorageType;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new FileStorageHandler(".\\resources", StorageType.PATH, new ObjectStreamWriter()).getStorage());
    }
}
