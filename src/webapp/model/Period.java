package webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static webapp.utils.Utils.getLn;

public class Period {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
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

    @Override
    public String toString() {
        String formattedDateFrom = formatter.format(dateFrom);
        String formattedDateTo = dateTo == null ? "Сейчас" : formatter.format(dateTo);
        return formattedDateFrom + " - "
                + formattedDateTo + " "
                + header + getLn()
                + description + getLn();
    }
}
