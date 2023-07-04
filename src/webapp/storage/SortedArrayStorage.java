package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/** Sorted array based storage for Resumes */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveElement(Resume resume, int index) {
        if (index == -1) { // меньше всех имеющихся
            System.arraycopy(storage, 0, storage, 1, size);
            storage[0] = resume;
        } else if (index == -size - 1) { // больше всех
            storage[size] = resume;
        } else {
            int indexToSave = -index - 1; // куда сохранять
            System.arraycopy(storage, indexToSave, storage, indexToSave + 1, size - indexToSave);
            storage[indexToSave] = resume;
        }
    }

    @Override
    protected void deleteElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
