package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/** Sorted array based storage for Resumes */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addElement(Resume resume) {
        Integer searchKey = (Integer) getSearchKey(resume.getUuid());
        int indexToSave = -searchKey - 1;
        System.arraycopy(storage, indexToSave, storage, indexToSave + 1, size - indexToSave);
        storage[indexToSave] = resume;
        size++;
    }

    @Override
    protected void deleteElement(Object searchKey, String uuid) {
        int index = (Integer) searchKey;
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume resume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }
}
