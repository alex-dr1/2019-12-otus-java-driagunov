package ru.otus.core.servlet;

import ru.otus.core.dao.UserDao;
import ru.otus.core.dbservice.DBServiceUser;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.templates.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<Long, User> allUsers = dbServiceUser.getAllUser();
        Map<String, Object> usersTemplate = new HashMap<>();

        usersTemplate.put("users", allUsers);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, usersTemplate));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pName = request.getParameter("name");
        String pAddress = request.getParameter("address");
        String[] pTelephones = request.getParameterValues("telephone");

        User user = createUser(pName, pAddress, pTelephones);
//        System.out.println(user);
        dbServiceUser.saveUser(user);
        doGet(request, response);
    }

    private User createUser(String pName, String pAddress, String[] pTelephones) {
        User user = new User();
        user.setName(pName);

        Address addressUser = new Address(pAddress, user);

        user.setAddress(addressUser);

        List<Phone> phoneList = Arrays.stream(pTelephones).map(tel -> new Phone(tel, user)).collect(Collectors.toList());

        user.setPhones(phoneList);

        return user;
    }

}
