package webapp.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        assertThrows(NotExistStorageException.class, () -> storage.get("test"));
    }

    @Test
    public void getAll() {
        Resume[] expected = new Resume[]{
                new Resume(UUID_1),
                new Resume(UUID_2),
                new Resume(UUID_3)};
        assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void save() {
        Resume expected = new Resume("UUID_4");
        storage.save(expected);
        assertEquals(expected, storage.get("UUID_4"));
        assertEquals(4, storage.size());

        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
        assertEquals(3, storage.size());

        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("test")));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void arrayOverflowTest() {
        storage.clear();
        int storageLimit = AbstractArrayStorage.STORAGE_LIMIT;
        try {
            for (int i = 0; i < storageLimit; i++) {
                storage.save(new Resume("UUID_" + i));
            }
        } catch (StorageException e) {
            fail("Premature storage overflow");
        }

        assertThrows(StorageException.class, () -> storage.save(new Resume("test")));
    }
}