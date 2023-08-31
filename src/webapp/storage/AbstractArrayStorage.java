package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/** Array based storage for Resumes */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int size = 0;

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected List<Resume> doGetAll() {
        return List.of(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void saveElement(Integer searchKey, Resume resume) {
        if (size() >= STORAGE_LIMIT) {
            throw new StorageException("The array overflow has occurred", resume.getUuid());
        }
        addElement(resume);
    }

    protected abstract void addElement(Resume resume);

    @Override
    protected void updateElement(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }
}
