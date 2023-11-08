package webapp.storage;

import webapp.serialization.ObjectStreamSerializer;
import webapp.storage.FileStorage;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamSerializer()));
    }
}
