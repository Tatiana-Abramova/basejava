package webapp.model;

import java.util.ArrayList;
import java.util.List;

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
                    .append(System.getProperty("line.separator"));
        }
        return result.toString();
    }
}
