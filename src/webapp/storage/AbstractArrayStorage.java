package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.Arrays;

/** Array based storage for Resumes */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isElementExist(Resume resume) {
        int index = getIndex(resume.getUuid());
        return index >= 0
                && storage[index].getUuid().equals(resume.getUuid());
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }
}
