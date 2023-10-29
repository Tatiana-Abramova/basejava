package webapp.web;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webapp.exception.StorageException;
import webapp.model.*;
import webapp.sql.Config;
import webapp.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static webapp.utils.Utils.getLineSeparator;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

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
            case "edit":
                if (uuid != null) {
                    r = storage.get(uuid);
                } else {
                    r = new Resume();
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
            if (value != null && value.trim().length() != 0) {
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
                    List<String> list = new ArrayList<>(List.of(value.split(getLineSeparator())));
                    list.removeAll(Arrays.asList("", null));
                    section = new ListSection(list);
                }
                case EXPERIENCE, EDUCATION -> {
                }
            }
            if (value != null && value.trim().length() != 0) {
                r.setSection(type, section);
            } else {
                r.getSections().remove(type);
            }
        }
    }
}
