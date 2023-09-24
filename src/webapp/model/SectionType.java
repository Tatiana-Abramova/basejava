package webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
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
}
