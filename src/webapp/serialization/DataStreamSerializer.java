package webapp.serialization;

import webapp.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void writeToFile(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            forEachWithException(contacts.entrySet(), (entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            forEachWithException(sections.entrySet(), (entry) -> {
                SectionType type = entry.getKey();
                dos.writeUTF(type.getTitle());
                Section section = entry.getValue();
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> records = ((ListSection) section).getList();
                        dos.writeInt(records.size());
                        forEachWithException(records, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companies = ((CompanySection) section).getCompanies();
                        dos.writeInt(companies.size());
                        forEachWithException(companies, (company -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite());
                            List<Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
                            forEachWithException(periods, (period -> {
                                dos.writeUTF(period.getDateFrom());
                                dos.writeUTF(period.getDateTo());
                                dos.writeUTF(period.getHeader());
                                dos.writeUTF(period.getDescription());
                            }));
                        }));
                    }
                }
            });
        }
    }

    @Override
    public Resume readFile(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            List<Section> sections = new ArrayList<>();
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = SectionType.fromTitle(dis.readUTF());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> sections.add(new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection section = new ListSection();
                        sections.add(section);
                        List<String> list = section.getList();
                        int sectionSize = dis.readInt();
                        for (int j = 0; j < sectionSize; j++) {
                            list.add(dis.readUTF());
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection section = new CompanySection();
                        sections.add(section);
                        List<Company> companies = section.getCompanies();
                        int sectionSize = dis.readInt();
                        for (int j = 0; j < sectionSize; j++) {
                            Company company = new Company(dis.readUTF(), dis.readUTF());
                            companies.add(company);
                            List<Period> periods = company.getPeriods();
                            int periodsSize = dis.readInt();
                            for (int k = 0; k < periodsSize; k++) {
                                Period period = new Period(
                                        dis.readUTF(),
                                        dis.readUTF(),
                                        dis.readUTF(),
                                        dis.readUTF());
                                periods.add(period);
                            }
                        }
                    }
                }
            }
            return resume;
        }
    }
}
