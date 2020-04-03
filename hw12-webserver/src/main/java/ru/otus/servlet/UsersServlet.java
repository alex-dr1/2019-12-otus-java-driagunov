package ru.otus.servlet;

import ru.otus.dao.UserDao;
import ru.otus.model.User;
import ru.otus.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";

    private final UserDao userDao;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, UserDao userDao) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<Long, User> allUsers = userDao.getAllUsers();
        Map<String, Object> usersTemplate = new HashMap<>();

        usersTemplate.put("users", allUsers);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, usersTemplate));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pName = request.getParameter("name");
        String pLogin = request.getParameter("login");
        String pPass = request.getParameter("pass");
        String pAddress = request.getParameter("address");
        String[] pTelephones = request.getParameterValues("telephone");

        long size = userDao.getAllUsers().size();
        User user = new User(size+1, pName, pLogin, pPass);
        userDao.saveUser(user);
        doGet(request, response);
    }

}
