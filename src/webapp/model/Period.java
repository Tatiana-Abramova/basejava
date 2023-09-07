package webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static webapp.utils.Utils.getLineSeparator;

/** Experience or education period */
public class Period  implements Serializable {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String header;
    private String description;

    public Period(LocalDate dateFrom, LocalDate dateTo, String header, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.header = header;
        this.description = description;
    }

    public Period(LocalDate dateFrom, String header, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = null;
        this.header = header;
        this.description = description;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        String formattedDateFrom = formatter.format(dateFrom);
        String formattedDateTo = dateTo == null ? "Сейчас" : formatter.format(dateTo);
        return formattedDateFrom + " - "
                + formattedDateTo + " "
                + header + getLineSeparator()
                + description + getLineSeparator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period period)) return false;

        if (!getDateFrom().equals(period.getDateFrom())) return false;
        if (!getDateTo().equals(period.getDateTo())) return false;
        if (!getHeader().equals(period.getHeader())) return false;
        return getDescription() != null ? getDescription().equals(period.getDescription()) : period.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getDateFrom().hashCode();
        result = 31 * result + getDateTo().hashCode();
        result = 31 * result + getHeader().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
