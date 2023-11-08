package webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume with uuid=" + uuid + " has already exist", uuid, null);
    }
}
