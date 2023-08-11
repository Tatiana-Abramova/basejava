package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/** Array based storage for Resumes */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final List<Resume> getAllSorted() {
        return List.of(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void saveElement(Resume resume) {
        if (size() >= STORAGE_LIMIT) {
            throw new StorageException("The array overflow has occurred", resume.getUuid());
        }
        addElement(resume);
    }

    protected abstract void addElement(Resume resume);

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }
}
