package webapp.model;

/** Experience or education record */
public class Company {
    private String name;
    private String website;
    private Period period;

    public Company(String name, String website, Period period) {
        this.name = name;
        this.website = website;
        this.period = period;
    }

    @Override
    public String toString() {
        return name + " "
                + website + getLn()
                + period + getLn();
    }

    private String getLn() {
        return System.getProperty("line.separator");
    }
}
