package ua.epam.hw12.rest;

import com.google.gson.Gson;
import ua.epam.hw12.model.Account;
import ua.epam.hw12.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private AccountService accountService = new AccountService();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = gson.fromJson(request.getReader(), Account.class);
        accountService.create(account);
        response.sendRedirect("/account");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = gson.fromJson(request.getReader(), Account.class);
        accountService.update(account.getId(), account);
        response.sendRedirect("/account");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        accountService.delete(Long.parseLong(request.getParameter("id")));
        response.sendRedirect("/account");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String id = request.getParameter("id");
        if (id == null) {
            List<Account> accountList = accountService.read();
            if (accountList == null) {
                response.sendError(404);
            } else {
                for (int i = 0; i < accountList.size(); i++) {
                    Account account = accountList.get(i);
                    writer.println(gson.toJson(account));
                }
            }
        } else {
            long accountId = Long.parseLong(id);
            Account account = accountService.readById(accountId);
            writer.println(gson.toJson(account));
        }
        writer.flush();
        writer.close();
    }
}
