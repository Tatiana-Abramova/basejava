package webapp.model;

import java.io.Serial;
import java.util.LinkedList;
import java.util.List;

import static webapp.utils.Utils.getLineSeparator;

/** List section */
public class ListSection extends Section {

    public static ListSection EMPTY = new ListSection(List.of(""));

    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> list = new LinkedList<>();

    public ListSection() {

    }

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        return String.join(getLineSeparator(), list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection that)) return false;

        return getList().equals(that.getList());
    }

    @Override
    public int hashCode() {
        return getList().hashCode();
    }
}
