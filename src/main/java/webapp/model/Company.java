package webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static webapp.utils.Utils.getLineSeparator;

/** Experience or education record */
@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String website;
    private List<Period> periods = new LinkedList<>();

    public static Company EMPTY = new Company("", "", List.of(Period.EMPTY));

    public Company(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public Company(String name, String website, List<Period> periods) {
        this(name, website);
        this.periods = periods;
    }

    public Company() {
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
            periodsStr.append(period).append(getLineSeparator());
        }
        return name + " "
                + website + getLineSeparator()
                + periodsStr + getLineSeparator();
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
