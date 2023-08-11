package webapp.storage;

import webapp.model.Resume;

import java.util.List;

/** Storage for Resumes */
public interface Storage {

    /** @return size of the resume storage */
    int size();

    /** @return all arrays in storage */
    List<Resume> getAllSorted();

    /**
     * @param uuid resume unique ID
     * @return resume by its ID
     */
    Resume get(String uuid);

    /**
     * Saves new resume to the storage
     * @param resume a new resume
     */
    void save(Resume resume);

    /**
     * Updates resume in the storage
     * @param resume new resume
     */
    void update(Resume resume);

    /**
     * Deletes resume by its ID
     * @param uuid resume unique ID
     */
    void delete(String uuid);

    /** Clears all the stored resumes */
    void clear();

}
