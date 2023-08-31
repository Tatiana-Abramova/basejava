package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Map based storage for Resumes */
public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected Resume getElement(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void saveElement(String searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(String searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteElement(String searchKey, String uuid) {
        storage.remove(uuid);
    }
}
