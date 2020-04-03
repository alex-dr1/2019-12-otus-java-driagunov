package ru.otus.core.servlet;

import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.templates.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

//        //Map<Long, User> allUsers = userDao.getAllUsers();
//        Map<String, Object> usersTemplate = new HashMap<>();
//
//        usersTemplate.put("users", allUsers);
//
//        response.setContentType("text/html");
//        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, usersTemplate));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pName = request.getParameter("name");
        String pAddress = request.getParameter("address");
        String[] pTelephones = request.getParameterValues("telephone");

        long size = userDao.getAllUsers().size();

        User user = new User();
        user.setId(size+1);
        user.setName(pName);
        Address addressUser = new Address(pAddress, user);
        user.setAddress(addressUser);

        List<Phone> phoneList = new ArrayList<>();
        for (String tel: pTelephones){
            phoneList.add(new Phone(tel, user));
        }

        user.setPhones(phoneList);
//        System.out.println(user);
        userDao.saveUser(user);
        doGet(request, response);
    }

}
