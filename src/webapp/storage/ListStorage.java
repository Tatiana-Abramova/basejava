package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** List based storage for Resumes */
public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

    @Override
    public final int size() {
        return storage.size();
    }

    @Override
    public final Resume get(String uuid) {
        return getElement(uuid)
                .orElseThrow(() -> new NotExistStorageException(uuid));
    }

    @Override
    public final Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected int getIndex(String uuid) {
        return storage.indexOf(getElement(uuid).orElse(new Resume(uuid)));
    }

    @Override
    protected boolean isElementExist(Resume resume) {
        return getElement(resume.getUuid()).isPresent();
    }

    @Override
    protected void saveElement(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteElement(int index, String uuid) {
        storage.remove(index);
    }

    private Optional<Resume> getElement(String uuid) {
        return storage
                .stream()
                .filter(resume -> uuid.equals(resume.getUuid()))
                .findFirst();
    }
}
