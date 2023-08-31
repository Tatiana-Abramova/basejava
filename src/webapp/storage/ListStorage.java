package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/** List based storage for Resumes */
public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public final int size() {
        return storage.size();
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        int index = searchKey;
        return index != -1 && storage.get(index) != null;
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> doGetAll() {
        return storage;
    }

    @Override
    protected void saveElement(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateElement(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    protected void deleteElement(Integer searchKey, String uuid) {
        storage.remove((searchKey).intValue());
    }
}
