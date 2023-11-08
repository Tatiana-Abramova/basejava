package webapp.storage;

import webapp.serialization.XmlStreamSerializer;
import webapp.storage.PathStorage;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}
