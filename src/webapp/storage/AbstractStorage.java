package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.exception.StorageException;
import webapp.model.Resume;

/** Storage for Resumes */
public abstract class AbstractStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;

    @Override
    public final void save(Resume resume) {
        if (resume != null) {
            if (size() >= STORAGE_LIMIT) {
                throw new StorageException("The array overflow has occurred", resume.getUuid());
            } else if (isElementExist(resume)) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                saveElement(resume);
                System.out.println("Resume with uuid = " + resume.getUuid() + " has been saved to the storage");
            }
        }
    }

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(index, resume);
            System.out.println("Resume with uuid = " + resume.getUuid() + " has been updated successfully");
        }
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteElement(index, uuid);
        System.out.println("Resume with uuid = " + uuid + " has been deleted from the storage");
    }

    /**
     * Returns an index of an element with provided ID
     * @param uuid ID of an element
     * @return index of an element with provided ID
     */
    protected abstract int getIndex(String uuid);

    /** Checks if the provided element exists in the storage
     * @param resume an element to be checked
     * @return if the provided element exists in the storage
     */
    protected abstract boolean isElementExist(Resume resume);

    /**
     * Saves a new resume to a specified position in the array
     * @param resume a new element to be saved
     */
    protected abstract void saveElement(Resume resume);

    /**
     * Updates element on a specified position
     * @param index  index of an updated element
     * @param resume a new element
     */
    protected abstract void updateElement(int index, Resume resume);

    /**
     * Deletes element on a specified position
     * @param index index of a deleted element
     */
    protected abstract void deleteElement(int index, String uuid);
}
