package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** List based storage for Resumes */
public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public final int size() {
        return storage.size();
    }

    @Override
    public final List<Resume> getAllSorted() {
        return storage.stream().sorted(RESUME_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        int index = (Integer) searchKey;
        return index != -1 && storage.get(index) != null;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void saveElement(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    protected void deleteElement(Object searchKey, String uuid) {
        storage.remove(((Integer) searchKey).intValue());
    }
}
