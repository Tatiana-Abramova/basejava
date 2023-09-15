package webapp.storage;

import webapp.serialization.ObjectStreamWriter;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(".\\resources", new ObjectStreamWriter()));
    }
}
