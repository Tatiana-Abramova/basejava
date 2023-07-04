package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/** Array based storage for Resumes */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        printDoesNotExist(uuid);
        return null;
    }

    @Override
    public void save(Resume resume) {
        if (resume != null) {
            int index = getIndex(resume.getUuid());
            if (size >= storage.length) {
                System.out.println("The array overflow has occurred");
            } else if (index >= 0
                    && storage[index].getUuid().equals(resume.getUuid())) {
                System.out.println("Resume with the same uuid has already exist: " + resume.getUuid());
            } else {
                saveElement(resume, index);
                size++;
                System.out.println("Resume with uuid = " + resume.getUuid() + " has been saved to the storage");
            }
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            printDoesNotExist(resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.println("Resume with uuid = " + resume.getUuid() + " has been updated successfully");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            printDoesNotExist(uuid);
            return;
        }
        deleteElement(index);
        size--;
        System.out.println("Resume with uuid = " + uuid + " has been deleted from the storage");
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * Saves a new resume to a specified position in the array
     * @param resume a new element to be saved
     * @param index  index of a new element
     */
    protected abstract void saveElement(Resume resume, int index);

    /**
     * Deletes element on a specified position
     * @param index index of a deleted element
     */
    protected abstract void deleteElement(int index);

    /**
     * Returns an index of an element with provided ID
     * @param uuid ID of an element
     * @return index of an element with provided ID
     */
    protected abstract int getIndex(String uuid);

    /**
     * Prints not exist message
     * @param uuid ID of an element
     */
    protected void printDoesNotExist(String uuid) {
        System.out.println("Resume with uuid = " + uuid + " does not exist");
    }
}
