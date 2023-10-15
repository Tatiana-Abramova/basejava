package webapp.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webapp.Config;
import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;
import webapp.model.ResumeTestData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {

    protected static final String STORAGE_DIR = Config.get().getStorageDir().getAbsolutePath();

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.createResume(UUID_1, "test1 test1");
        RESUME_2 = ResumeTestData.createResume(UUID_2, "test2 test2");
        RESUME_3 = ResumeTestData.createResume(UUID_3, "test3 test3");
        RESUME_4 = ResumeTestData.createResume(UUID_4, "test4 test4");
    }

    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }


    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void get() {
        Resume r = storage.get(RESUME_1.getUuid());
        assertAll("get",
                () -> assertGet(RESUME_1),
                () -> assertGet(RESUME_2),
                () -> assertGet(RESUME_3),
                () -> assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4)));
    }

    @Test
    public void getAllSorted() {
        assertIterableEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }

    @Test
    public void clear() {
        storage.clear();
        assertAll("clear",
                () -> assertSize(0),
                () -> assertEquals(new ArrayList<>(0), storage.getAllSorted()));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertAll("save",
                () -> assertGet(RESUME_4),
                () -> assertSize(4),
                () -> assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1)));
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "new test1");
        storage.update(newResume);
        assertAll("update",
                () -> assertGet(newResume),
                () -> assertSize(3),
                () -> assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4)));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertAll("delete",
                () -> assertSize(2),
                () -> assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1)));
    }

    protected void assertSize(int expected) {
        assertEquals(expected, storage.size());
    }

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
