package ua.epam.hw12.rest;

import com.google.gson.Gson;
import ua.epam.hw12.model.Developer;
import ua.epam.hw12.service.AccountService;
import ua.epam.hw12.service.DeveloperService;
import ua.epam.hw12.service.SkillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DeveloperServlet", urlPatterns = "/developer")
public class DeveloperServlet extends HttpServlet {
    private DeveloperService developerService = new DeveloperService();
    private AccountService accountService = new AccountService();
    private SkillService skillService = new SkillService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Developer developer = gson.fromJson(request.getReader(), Developer.class);
        developer.setDeveloperSkillsSet(skillService.create(developer.getDeveloperSkillsSet()));
        developer.setDeveloperAccount(accountService.create(developer.getDeveloperAccount()));
        developerService.create(developer);
        response.sendRedirect("/developer");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Developer developer = gson.fromJson(request.getReader(), Developer.class);
        Developer developerWithId = developerService.readById(developer.getId());
        developerService.update(developerWithId.getId(), developer);
        accountService.update(developerWithId.getDeveloperAccount().getId(), developer.getDeveloperAccount());
        skillService.update(developerWithId.getDeveloperSkillsSet(), developer.getDeveloperSkillsSet());
        response.sendRedirect("/developer");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Developer developerWithId = developerService.readById(id);
        developerService.delete(id);
        accountService.delete(developerWithId.getDeveloperAccount().getId());
        response.sendRedirect("/developer");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String id = request.getParameter("id");
        if (id == null) {
            List<Developer> developers = developerService.read();
            if (developers == null) {
                response.sendError(404);
            } else {
                for (Developer developer : developers) {
                    writer.println(gson.toJson(developer));
                }
            }
        } else {
            long developerId = Long.parseLong(id);
            Developer developer = developerService.readById(developerId);
            writer.println(gson.toJson(developer));
        }
        writer.flush();
        writer.close();
    }
}
