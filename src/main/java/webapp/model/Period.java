package webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import webapp.utils.LocalDateAdapter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static webapp.utils.Utils.getLineSeparator;

/** Experience or education period */
@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {

    public static Period EMPTY = new Period();

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String PATTERN = "MM/yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    private static final String NOW = "Сейчас";
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateFrom;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateTo;
    private String header;
    private String description;

    public Period(LocalDate dateFrom, LocalDate dateTo, String header, String description) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.header = header;
        this.description = description;
    }

    public Period(String dateFrom, String dateTo, String header, String description) {
        this(getLocalDate(dateFrom), getLocalDate(dateTo), header, description);
    }

    public Period(LocalDate dateFrom, String header, String description) {
        this(dateFrom, null, header, description);
    }

    public Period(String dateFrom, String header, String description) {
        this(getLocalDate(dateFrom), header, description);
    }

    public Period() {
    }

    public String getDateFrom() {
        return dateFrom == null ? "" : FORMATTER.format(dateFrom);
    }

    public String getDateTo() {
        return dateTo == null ? "" : FORMATTER.format(dateTo);
    }

    public String getHeader() {
        return header;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        String formattedDateFrom = FORMATTER.format(dateFrom);
        String formattedDateTo = getDateTo();
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

    private static LocalDate getLocalDate(String date) {
        String postfix = "/dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN + postfix);
        return Objects.equals(date, NOW) ? null : LocalDate.parse(date + "/01", formatter);
    }
}
