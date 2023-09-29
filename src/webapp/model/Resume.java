package webapp.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import static webapp.utils.Utils.getLineSeparator;

/** Initial resume class */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Unique identifier */
    private String uuid;

    /** Full employee name */
    private String fullName;

    /** Employee contacts */
    private Map<ContactType, String> contacts = new LinkedHashMap<>();

    /** Resume sections */
    private Map<SectionType, Section> sections = new LinkedHashMap<>();

    public Resume(@NotNull String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume() {
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setContact(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    public void removeContact(ContactType type) {
        contacts.remove(type);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void setSection(SectionType type, Section section) {
        sections.put(type, section);
    }

    public void removeSection(SectionType type) {
        sections.remove(type);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

        @Override
    public String toString() {
        StringBuilder result = new StringBuilder(fullName + getLineSeparator());

        for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
            result
                    .append(contact.getKey().getTitle()).append(": ")
                    .append(contact.getValue()).append(getLineSeparator());
        }

        result.append(System.getProperty("line.separator"));

        for (Map.Entry<SectionType, Section> section : sections.entrySet()) {
            result
                    .append(section.getKey().getTitle()).append(getLineSeparator())
                    .append(section.getValue()).append(getLineSeparator());
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume resume)) return false;

        if (!getUuid().equals(resume.getUuid())) return false;
        if (!getFullName().equals(resume.getFullName())) return false;
        if (!getContacts().equals(resume.getContacts())) return false;
        return getSections().equals(resume.getSections());
    }

    @Override
    public int hashCode() {
        int result = getUuid().hashCode();
        result = 31 * result + getFullName().hashCode();
        result = 31 * result + getContacts().hashCode();
        result = 31 * result + getSections().hashCode();
        return result;
    }
}
