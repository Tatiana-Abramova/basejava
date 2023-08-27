package webapp.model;

import java.util.LinkedList;
import java.util.List;

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
            result.append("* ").append(point).append(System.getProperty("line.separator"));
        }
        return result.toString();
    }
}
