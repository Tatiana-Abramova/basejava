package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.serialization.StreamWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Files based resume storage */
public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamWriter writer;

    public FileStorage(File directory, StreamWriter writer) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.writer = writer;
    }

    @Override
    public void clear() {
        for (File file : getFiles()) {
            deleteElement(file);
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
        try {
            return writer.readFile(new BufferedInputStream(new FileInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Cannot get file content. IO error", searchKey.getName(), e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected void saveElement(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Cannot create file. IO error", searchKey.getName(), e);
        }
        updateElement(searchKey, resume);
    }

    @Override
    protected void updateElement(File searchKey, Resume resume) {
        try {
            writer.writeToFile(resume, new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Cannot update file. IO error", searchKey.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File searchKey) {
        boolean deleted = searchKey.delete();
        if (!deleted) {
            throw new StorageException("Cannot delete file", searchKey.getName());
        }
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Cannot read directory. IO error", null);
        }
        return files;
    }
}
