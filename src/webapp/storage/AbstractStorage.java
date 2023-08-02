package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

/** Storage for Resumes */
public abstract class AbstractStorage implements Storage {

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return getElement(searchKey);
    }

    @Override
    public final void save(Resume resume) {
        if (resume != null) {
            getNotExistingSearchKey(resume.getUuid());
            saveElement(resume);
            System.out.println("Resume with uuid = " + resume.getUuid() + " has been saved to the storage");
        }
    }

    @Override
    public final void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        updateElement(searchKey, resume);
        System.out.println("Resume with uuid = " + resume.getUuid() + " has been updated successfully");
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        deleteElement(searchKey, uuid);
        System.out.println("Resume with uuid = " + uuid + " has been deleted from the storage");
    }

    /**
     * Returns an index of an element with provided ID
     * @param uuid ID of an element
     * @return index of an element with provided ID
     */
    protected abstract Object getSearchKey(String uuid);

    /**
     * Checks if an element exists in the storage
     * @param searchKey search key
     * @return existence flag
     */
    protected abstract boolean isExist(Object searchKey);

    /**
     * Returns an element found by a search key
     * @param searchKey search key
     * @return a found element
     */
    protected abstract Resume getElement(Object searchKey);

    /**
     * Saves a new resume to a specified position in the array
     * @param resume a new element to be saved
     */
    protected abstract void saveElement(Resume resume);

    /**
     * Updates element on a specified position
     * @param searchKey index of an updated element
     * @param resume    a new element
     */
    protected abstract void updateElement(Object searchKey, Resume resume);

    /**
     * Deletes element on a specified position
     * @param searchKey index of a deleted element
     */
    protected abstract void deleteElement(Object searchKey, String uuid);

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(uuid);
        }
    }
}
