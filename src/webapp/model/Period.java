package webapp.model;

import java.time.LocalDate;

public class Period {
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

    @Override
    public String toString() {
        return dateFrom + " - "
                + dateTo + " "
                + header + System.getProperty("line.separator")
                + description + System.getProperty("line.separator");
    }
}
