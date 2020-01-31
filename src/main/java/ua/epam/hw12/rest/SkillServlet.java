package ua.epam.hw12.rest;

import com.google.gson.Gson;
import ua.epam.hw12.model.Skill;
import ua.epam.hw12.service.SkillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SkillServlet", urlPatterns = "/skill")
public class SkillServlet extends HttpServlet {
    private SkillService skillService = new SkillService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Skill skill = gson.fromJson(request.getReader(), Skill.class);
        skillService.create(skill);
        response.sendRedirect("/skill");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Skill skill = gson.fromJson(request.getReader(), Skill.class);
        skillService.update(skill.getId(), skill);
        response.sendRedirect("/skill");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        skillService.delete(Long.parseLong(request.getParameter("id")));
        response.sendRedirect("/skill");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String id = request.getParameter("id");
        if (id == null) {
            List<Skill> skillList = skillService.read();
            if (skillList == null) {
                response.sendError(404);
            } else {
                for (int i = 0; i < skillList.size(); i++) {
                    Skill skill = skillList.get(i);
                    writer.println(gson.toJson(skill));
                }
            }
        }
        else {
            long skillId = Long.parseLong(id);
            Skill skill = skillService.readById(skillId);
            writer.println(gson.toJson(skill));
        }
        writer.flush();
        writer.close();
    }
}
