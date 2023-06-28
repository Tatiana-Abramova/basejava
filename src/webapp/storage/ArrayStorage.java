package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/** Array based storage for Resumes */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    /** @return size of the resume array */
    public int size() {
        return size;
    }

    /**
     * @param uuid resume unique ID
     * @return resume by its ID
     */
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        printDoesNotExist(uuid);
        return null;
    }

    /** @return array, contains only resumes (without null) */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    /**
     * Saves new resume to the storage
     * @param r a new resume
     */
    public void save(Resume r) {
        if (r != null) {
            int index = getIndex(r.getUuid());
            if (size >= storage.length) {
                System.out.println("The array overflow has occurred");
            } else if (index >= 0
                    && storage[index].getUuid() != null
                    && storage[index].getUuid().equals(r.getUuid())) {
                System.out.println("Resume with the same uuid has already exist: " + r.getUuid());
            } else {
                storage[size] = r;
                size++;
                System.out.println("Resume with uuid = " + r.getUuid() + " has been saved to the storage");
            }
        }
    }

    /**
     * Updates resume in the storage
     * @param resume new resume
     */
    public void update(Resume resume) {
        if (resume == null) {
            printDoesNotExist(null);
        } else {
            int index = getIndex(resume.getUuid());
            if (index < 0) {
                printDoesNotExist(resume.getUuid());
            } else {
                storage[index] = resume;
                System.out.println("Resume with uuid = " + resume.getUuid() + " has been updated successfully");
            }
        }
    }

    /**
     * Deletes resume by its ID
     * @param uuid resume unique ID
     */
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            printDoesNotExist(uuid);
            return;
        }
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
        System.out.println("Resume with uuid = " + uuid + " has been deleted from the storage");
    }

    /** Clears all the stored resumes */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    private void printDoesNotExist(String uuid) {
        System.out.println("Resume with uuid = " + uuid + " does not exist");
    }

    private int getIndex(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    return i;
                }
            }
        }
        return -1;
    }
}
