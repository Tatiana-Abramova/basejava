package webapp.storage;

import webapp.model.Resume;

/** Unsorted array based storage for Resumes */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveElement(Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void deleteElement(int index, String uuid) {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
