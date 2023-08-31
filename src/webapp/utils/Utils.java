package webapp.utils;

import java.time.LocalDate;
import java.time.Month;

public class Utils {
    public static String getLn() {
        return System.getProperty("line.separator");
    }

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
