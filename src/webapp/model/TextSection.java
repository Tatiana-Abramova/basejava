package webapp.model;

/** Text section */
public class TextSection extends Section {
    private final String text;

    public TextSection(String description) {
        this.text = description;
    }

    @Override
    public String toString() {
        return text + System.getProperty("line.separator");
    }
}
