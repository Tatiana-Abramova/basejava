package webapp.model;

import static webapp.utils.Utils.getLineSeparator;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml0(Section section) {
            return toHtmlList(section);
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml0(Section section) {
            return toHtmlList(section);
        }
    },
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static SectionType fromTitle(String text) {
        for (SectionType sectionType : SectionType.values()) {
            if (sectionType.title.equalsIgnoreCase(text)) {
                return sectionType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + text);
    }

    protected String toHtmlList(Section section) {
        StringBuilder builder = new StringBuilder();
        for (String value : ((ListSection) section).getList()) {
            builder.append("* ").append(value).append(getLineSeparator());
        }
        return getTitleHtml() + getLineSeparator() + builder;
    }

    private String getTitleHtml() {
        return "<h3>" + title + "</h3>";
    }

    protected String toHtml0(Section section) {
        return getTitleHtml() + getLineSeparator() + section.toString();
    }

    public String toHtml(Section section) {
        return (section == null) ? "" : toHtml0(section);
    }
}
