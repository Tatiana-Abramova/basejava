package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

/** Map based storage for Resumes */
public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().stream().sorted().toArray(Resume[]::new);
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = storage.get(uuid);
        if (resume == null) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected int getIndex(String uuid) {
        return storage.get(uuid) == null ? -1 : 0;
    }

    @Override
    protected boolean isElementExist(Resume resume) {
        return storage.get(resume.getUuid()) != null;
    }

    @Override
    protected void saveElement(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteElement(int index, String uuid) {
        storage.remove(uuid);
    }
}
