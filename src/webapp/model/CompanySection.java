package webapp.model;

import java.util.ArrayList;
import java.util.List;

import static webapp.utils.Utils.getLineSeparator;

/** Section that contains experience or education records */
public class CompanySection extends Section {
    private final List<Company> companies = new ArrayList<>();

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Company company : companies) {
            result
                    .append(company)
                    .append(getLineSeparator());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanySection that)) return false;

        return getCompanies().equals(that.getCompanies());
    }

    @Override
    public int hashCode() {
        return getCompanies().hashCode();
    }
}
