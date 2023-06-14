/** Array based storage for Resumes */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    /** Clears all the stored resumes */
    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
        size = 0;
    }

    /**
     * Saves new resume to the storage
     * @param r a new resume
     */
    void save(Resume r) {
        if (r != null && r.uuid != null) {
            for (int i = 0; i < size(); i++) {
                if (storage[i] != null
                        && storage[i].uuid != null
                        && storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Resume with the same uuid has already exist: " + r.uuid);
                    return;
                }
            }

            if (size() < storage.length) {
                storage[size()] = r;
                size++;
            } else {
                System.out.println("The array overflow has occurred");
            }
        }
    }

    /**
     * @param uuid resume unique ID
     * @return resume by its ID
     */
    Resume get(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size(); i++) {
                if (uuid.equals(storage[i].uuid)) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    /**
     * Deletes resume by its ID
     * @param uuid resume unique ID
     */
    void delete(String uuid) {
        if (uuid != null) {
            int deletedIndex = -1;
            for (int i = 0; i < size(); i++) {
                if (uuid.equals(storage[i].uuid)) {
                    storage[i] = null;
                    deletedIndex = i;
                    break;
                }
            }
            if (deletedIndex >= 0 && size() - 1 - deletedIndex >= 0) {
                System.arraycopy(storage, deletedIndex + 1, storage, deletedIndex, size() - 1 - deletedIndex);
                storage[size() - 1] = null;
                size--;
            }
        }
    }

    /** @return array, contains only Resumes in storage (without null) */
    Resume[] getAll() {
        Resume[] result = new Resume[size()];
        System.arraycopy(storage, 0, result, 0, size());
        return result;
    }

    /** @return size of the resume array */
    int size() {
        return size;
    }
}
