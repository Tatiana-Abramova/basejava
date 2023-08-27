package webapp.model;

import static webapp.utils.Utils.getLn;

/** Text section */
public class TextSection extends Section {
    private final String text;

    public TextSection(String description) {
        this.text = description;
    }

    @Override
    public String toString() {
        return text + getLn();
    }
}
