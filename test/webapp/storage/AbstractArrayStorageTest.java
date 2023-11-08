package webapp.storage;

import webapp.storage.AbstractArrayStorage;
import webapp.storage.Storage;
import org.junit.jupiter.api.Test;
import webapp.exception.StorageException;
import webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractArrayStorageTest extends AbstractStorageTest{

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void arrayOverflowTest() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume("UUID_" + i, "test" + i));
        }

        assertThrows(StorageException.class, () -> storage.save(RESUME_4));
    }
}