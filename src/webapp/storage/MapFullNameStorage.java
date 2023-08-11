package webapp.storage;

import webapp.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Map based storage for Resumes */
public class MapFullNameStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream().sorted(RESUME_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume resume = storage.get(uuid);
        if (resume == null) {
            return null;
        } else {
            return resume.getFullName();
        }
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null && storage
                .entrySet()
                .stream()
                .anyMatch((entry) -> entry.getValue().getFullName().equals(searchKey));
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.values()
                .stream()
                .filter((r) -> r.getFullName().equals(searchKey))
                .findAny()
                .orElse(null);
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
