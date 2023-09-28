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
            writeWithException(contacts.entrySet(), dos, (entry) -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            writeWithException(sections.entrySet(), dos, (entry) -> {
                SectionType type = entry.getKey();
                dos.writeUTF(type.getTitle());
                Section section = entry.getValue();
                switch (type) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> records = ((ListSection) section).getList();
                        writeWithException(records, dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> companies = ((CompanySection) section).getCompanies();
                        writeWithException(companies, dos, (company -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite());
                            List<Period> periods = company.getPeriods();
                            writeWithException(periods, dos, (period -> {
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

            readWithException(dis, () -> {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            List<Section> sections = new ArrayList<>();
            readWithException(dis, () -> {
                SectionType type = SectionType.fromTitle(dis.readUTF());
                switch (type) {
                    case PERSONAL, OBJECTIVE -> sections.add(new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection section = new ListSection();
                        sections.add(section);
                        List<String> list = section.getList();
                        readWithException(dis, () -> {
                            list.add(dis.readUTF());
                        });
                    }
                    case EXPERIENCE, EDUCATION -> {
                        CompanySection section = new CompanySection();
                        sections.add(section);
                        List<Company> companies = section.getCompanies();
                        readWithException(dis, () -> {
                            Company company = new Company(dis.readUTF(), dis.readUTF());
                            companies.add(company);
                            List<Period> periods = company.getPeriods();
                            readWithException(dis, () -> {
                                Period period = new Period(
                                        dis.readUTF(),
                                        dis.readUTF(),
                                        dis.readUTF(),
                                        dis.readUTF());
                                periods.add(period);
                            });
                        });
                    }
                }
            });
            return resume;
        }
    }
}
