package webapp.model;

import java.io.Serial;
import java.util.LinkedList;
import java.util.List;

import static webapp.utils.Utils.getLineSeparator;

/** List section */
public class ListSection extends Section {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> list = new LinkedList<>();

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String point : list) {
            result.append("* ").append(point).append(getLineSeparator());
        }
        return result.toString();
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
