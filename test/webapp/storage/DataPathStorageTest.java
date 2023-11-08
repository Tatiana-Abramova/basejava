package webapp.storage;


import webapp.serialization.DataStreamSerializer;
import webapp.storage.PathStorage;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}
