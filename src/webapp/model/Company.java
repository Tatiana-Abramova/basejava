package webapp.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static webapp.utils.Utils.getLn;

/** Experience or education record */
public class Company  implements Serializable {
    private String name;
    private String website;
    private final List<Period> periods = new LinkedList<>();

    public Company(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        StringBuilder periodsStr = new StringBuilder();
        for (Period period : periods) {
            periodsStr.append(period).append(getLn());
        }
        return name + " "
                + website + getLn()
                + periodsStr + getLn();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;

        if (!getName().equals(company.getName())) return false;
        if (!getWebsite().equals(company.getWebsite())) return false;
        return getPeriods().equals(company.getPeriods());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + website.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }
}
