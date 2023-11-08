package webapp.utils;

public class Utils {
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    public static boolean notEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
