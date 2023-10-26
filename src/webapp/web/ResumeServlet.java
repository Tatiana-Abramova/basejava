package webapp.web;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webapp.model.Resume;
import webapp.sql.Config;
import webapp.storage.Storage;

import java.io.IOException;

public class ResumeServlet extends HttpServlet {


    private Storage storage;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!\n" : "Hello " + name + "!\n");
        StringBuilder html = new StringBuilder();
        html.append("""
                <html>
                  <table>
                    <tr>
                      <th>UUID</th>
                      <th>Full Name</th>
                    </tr>
                """);
        Storage storage = Config.get().getStorage();
        for (Resume resume : storage.getAllSorted()) {
            html.append("    <tr>\n" +
                    "      <td>" + resume.getUuid() + "</td>\n" +
                    "      <td>" + resume.getFullName() + "</td>\n" +
                    "    </tr>\n");
        }
        html.append(
                "  </table>\n" +
                        "</html>");
        response.getWriter().write(html.toString());
    }
}
