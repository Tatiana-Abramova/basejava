package webapp.storage;

import webapp.sql.Config;

public class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}
