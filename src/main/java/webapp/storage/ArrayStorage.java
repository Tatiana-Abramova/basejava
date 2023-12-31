package webapp.storage;

import webapp.model.Resume;

/** Unsorted array based storage for Resumes */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addElement(Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void deleteElement(Integer searchKey) {
        storage[searchKey] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
