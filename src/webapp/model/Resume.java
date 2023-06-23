package webapp.model;

/** Initial resume class */
public class Resume {

    /** Unique identifier */
    private final String uuid;

    /** Person name */
    private String name;

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { // добавила поле, чтобы было что обновлять
        this.name = name;
    }

    @Override
    public String toString() {
        return uuid + (name == null ? "" : " " + name);
    }
}
