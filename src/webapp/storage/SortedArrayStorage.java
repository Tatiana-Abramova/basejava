package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/** Sorted array based storage for Resumes */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addElement(Resume resume) {
        Integer searchKey = getSearchKey(resume.getUuid());
        int indexToSave = -searchKey - 1;
        System.arraycopy(storage, indexToSave, storage, indexToSave + 1, size - indexToSave);
        storage[indexToSave] = resume;
        size++;
    }

    @Override
    protected void deleteElement(Integer searchKey, String uuid) {
        int index = searchKey;
        System.arraycopy(storage, index + 1, storage, index, size - index);
        size--;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = Arrays
                .stream(storage)
                .limit(size)
                .filter(r -> r != null && r.getUuid().equals(uuid))
                .findAny()
                .orElse(new Resume(uuid, null));
        return Arrays.binarySearch(storage, 0, size, resume, RESUME_COMPARATOR);
    }
}
