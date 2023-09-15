package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.serialization.StreamWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Paths based resume storage */
public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamWriter writer;

    public PathStorage(String dir, StreamWriter writer) {
        this.directory = Paths.get(dir);
        this.writer = writer;

        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).forEach(file -> resumes.add(getElement(file)));
        } catch (IOException e) {
            throw new StorageException("Cannot get file list. IO error", directory.toString(), e);
        }
        return resumes;
    }

    @Override
    protected Resume getElement(Path searchKey) {
        try {
            return writer.readFile(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Cannot get file content. IO error", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void saveElement(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Cannot create file. IO error", searchKey.getFileName().toString(), e);
        }
        updateElement(searchKey, resume);
    }

    @Override
    protected void updateElement(Path searchKey, Resume resume) {
        try {
            writer.writeToFile(resume, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("Cannot update file. IO error", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteElement(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Cannot delete file. IO error", searchKey.getFileName().toString(), e);
        }
    }
}
