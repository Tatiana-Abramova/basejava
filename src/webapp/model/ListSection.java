package webapp.model;

import java.util.LinkedList;
import java.util.List;

import static webapp.utils.Utils.getLn;

/** List section */
public class ListSection extends Section {
    private final List<String> list = new LinkedList<>();

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String point : list) {
            result.append("* ").append(point).append(getLn());
        }
        return result.toString();
    }
}
