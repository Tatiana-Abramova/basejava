package webapp.model;

import java.io.Serial;

import static webapp.utils.Utils.getLineSeparator;

/** Text section */
public class TextSection extends Section {

    @Serial
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection(String description) {
        this.text = description;
    }

    public TextSection() {
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text + getLineSeparator();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextSection that)) return false;

        return getText().equals(that.getText());
    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }
}
