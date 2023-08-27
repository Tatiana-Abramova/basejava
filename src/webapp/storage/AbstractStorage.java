package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** Storage for Resumes */
public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator
                    .nullsLast(Comparator
                            .comparing(Resume::getFullName, Comparator.nullsLast(Comparator.naturalOrder()))
                            .thenComparing(Resume::getUuid, Comparator.nullsLast(Comparator.naturalOrder())));

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return getElement(searchKey);
    }

    @Override
    public final List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return doGetAll()
                .stream()
                .sorted(RESUME_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        if (resume != null) {
            getNotExistingSearchKey(resume.getUuid());
            saveElement(resume);
            System.out.println("Resume with uuid = " + resume.getUuid() + " has been saved to the storage");
        }
    }

    @Override
    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistingSearchKey(resume.getUuid());
        updateElement(searchKey, resume);
        System.out.println("Resume with uuid = " + resume.getUuid() + " has been updated successfully");
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        deleteElement(searchKey, uuid);
        System.out.println("Resume with uuid = " + uuid + " has been deleted from the storage");
    }

    /**
     * Returns an index of an element with provided ID
     * @param uuid ID of an element
     * @return index of an element with provided ID
     */
    protected abstract SK getSearchKey(String uuid);

    /**
     * Checks if an element exists in the storage
     * @param searchKey search key
     * @return existence flag
     */
    protected abstract boolean isExist(SK searchKey);

    /**
     * Returns all elements (unsorted)
     * @return elements
     */
    protected abstract List<Resume> doGetAll();

    /**
     * Returns an element found by a search key
     * @param searchKey search key
     * @return a found element
     */
    protected abstract Resume getElement(SK searchKey);

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
    protected abstract void updateElement(SK searchKey, Resume resume);

    /**
     * Deletes element on a specified position
     * @param searchKey index of a deleted element
     */
    protected abstract void deleteElement(SK searchKey, String uuid);

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
    }
}
