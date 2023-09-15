package webapp.storage;

import webapp.serialization.ObjectStreamWriter;

import java.io.File;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(new File(".\\resources"), new ObjectStreamWriter()));
    }
}
