package webapp.storage;

import webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

/** Map based storage for Resumes */
public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().stream().sorted().toArray(Resume[]::new);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.get((String) searchKey) != null;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void saveElement(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteElement(Object searchKey, String uuid) {
        storage.remove(uuid);
    }
}
