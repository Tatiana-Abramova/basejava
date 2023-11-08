package webapp.storage;

import webapp.serialization.ObjectStreamSerializer;
import webapp.storage.PathStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
