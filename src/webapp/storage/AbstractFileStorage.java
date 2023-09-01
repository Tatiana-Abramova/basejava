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

    @Override
    public void clear() {
        for (File file : getFiles()) {
            deleteElement(file, null);
        }
    }

    @Override
    public int size() {
        return getFiles().length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getFiles()) {
            resumes.add(getElement(file));
        }
        return resumes;
    }

    @Override
    protected Resume getElement(File searchKey) {
        Object result;
        try (FileInputStream fis = new FileInputStream(searchKey);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            result = ois.readObject();
            if (!(result instanceof Resume)) {
                throw new StorageException("Wrong file content type", searchKey.getName());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
        return (Resume) result;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void saveElement(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
            writeToFile(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("Cannot create file. IO error", searchKey.getName(), e);
        }
    }

    @Override
    protected void updateElement(File searchKey, Resume resume) {
        try {
            writeToFile(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("Cannot update file. IO error", searchKey.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File searchKey, String uuid) {
        boolean deleted = searchKey.delete();
        if (!deleted) {
            throw new StorageException("Cannot delete file", searchKey.getName());
        }
    }

    protected abstract void writeToFile(Resume resume, File file) throws IOException;

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Cannot read directory. IO error", null);
        }
        return files;
    }
}
