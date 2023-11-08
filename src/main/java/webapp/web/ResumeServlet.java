package webapp.web;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webapp.exception.StorageException;
import webapp.model.*;
import webapp.sql.Config;
import webapp.storage.Storage;
import webapp.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(
        name = "resumeServlet",
        urlPatterns = "/resume"
)
public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (fullName == null || fullName.trim().length() == 0) {
            throw new StorageException("Empty field: " + fullName, uuid);
        }
        Resume r;
        if (uuid == null || uuid.trim().length() == 0) {
            r = new Resume(UUID.randomUUID().toString(), fullName);
            updateResume(r, request);
            storage.save(r);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
            updateResume(r, request);
            storage.update(r);
        }

        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                if (uuid != null) {
                    r = storage.get(uuid);
                } else {
                    r = new Resume();
                }
                for (SectionType type : SectionType.values()) {
                    switch (type) {
                        case PERSONAL, OBJECTIVE -> {
                            if (r.getSection(type) == null) {
                                r.setSection(type, TextSection.EMPTY);
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            if (r.getSection(type) == null) {
                                r.setSection(type, ListSection.EMPTY);
                            }
                        }
                        case EXPERIENCE, EDUCATION -> {
                            if (r.getSection(type) == null) {
                                CompanySection section = new CompanySection();
                                Company empty = Company.EMPTY;
                                section.getCompanies().add(empty);
                                r.setSection(type, section);
                            } else {
                                List<Company> companies = ((CompanySection) r.getSection(type)).getCompanies();
                                for (Company company : companies) {
                                    company.getPeriods().add(Period.EMPTY);
                                }
                                companies.add(Company.EMPTY);
                            }
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void updateResume(Resume r, HttpServletRequest request) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (Utils.notEmpty(value)) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            Section section = null;
            String value = request.getParameter(type.name());
            switch (type) {
                case PERSONAL, OBJECTIVE -> section = new TextSection(value);
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    List<String> list = List.of(value.replaceAll("(" + Utils.getLineSeparator() + "\s*)+", Utils.getLineSeparator()).split(Utils.getLineSeparator()));
                    section = new ListSection(list);
                }
                case EXPERIENCE, EDUCATION -> {
                    section = new CompanySection();
                    String typeName = type.name();
                    int counter = 0; // TODO сохранение нескольких периодов
                    while (Utils.notEmpty(request.getParameter(typeName + counter + "header"))) {
                        Company company = new Company(
                                value,
                                request.getParameter(typeName + "url"));
                        Period period = new Period(
                                request.getParameter(typeName + counter + "dateFrom"),
                                request.getParameter(typeName + counter + "dateTo"),
                                request.getParameter(typeName + counter + "header"),
                                request.getParameter(typeName + counter + "description"));
                        company.getPeriods().add(period);
                        counter++;
                        ((CompanySection) section).getCompanies().add(company);
                    }
                }
            }
            if (Utils.notEmpty(value)) {
                r.setSection(type, section);
            } else {
                r.getSections().remove(type);
            }
        }
    }
}
