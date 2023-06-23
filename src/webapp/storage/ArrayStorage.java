package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/** Array based storage for Resumes */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
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
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    return storage[i];
                }
            }
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
        if (r != null && r.getUuid() != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i] != null
                        && storage[i].getUuid() != null
                        && storage[i].getUuid().equals(r.getUuid())) {
                    System.out.println("Resume with the same uuid has already exist: " + r.getUuid());
                    return;
                }
            }

            if (size < storage.length) {
                storage[size] = r;
                size++;
                System.out.println("Resume with uuid = " + r.getUuid() + " has been saved to the storage");
            } else {
                System.out.println("The array overflow has occurred");
            }
        }
    }

    /**
     * Updates resume in the storage
     * @param uuid resume ID
     * @param name person name
     */
    public void update(String uuid, String name) {
        Resume resume = get(uuid);
        if (resume != null) {
            resume.setName(name);
            System.out.println("Resume with uuid = " + uuid + " has been updated successfully");
        }
    }

    /**
     * Deletes resume by its ID
     * @param uuid resume unique ID
     */
    public void delete(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                    System.out.println("Resume with uuid = " + uuid + " has been deleted from the storage");
                    return;
                }
            }
        }
        printDoesNotExist(uuid);
    }

    /** Clears all the stored resumes */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    private void printDoesNotExist(String uuid) {
        System.out.println("Resume with uuid = " + uuid + " does not exist");
    }
}
