package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Files based resume storage */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            deleteElement(file, null);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int size() {
        return directory.list().length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            resumes.add(readFile(file));
        }
        return resumes;
    }

    @Override
    protected Resume getElement(File searchKey) {
        return readFile(searchKey);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void saveElement(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
        writeToFile(resume, searchKey);
    }

    @Override
    protected void updateElement(File searchKey, Resume resume) {
        writeToFile(resume, searchKey);
    }

    @Override
    protected void deleteElement(File searchKey, String uuid) {
        boolean deleted = searchKey.delete();
        if (!deleted) {
            throw new StorageException("Cannot delete file", searchKey.getName());
        }
    }

    protected abstract void writeToFile(Resume resume, File file);

    protected abstract Resume readFile(File file);
}
