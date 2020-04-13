package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.repository.dbservice.DBServiceUser;
import ru.otus.repository.model.User;
import ru.otus.service.UserFactory;

import java.util.List;


@Controller
public class UserController {

    private final DBServiceUser dbServiceUser;
    private final UserFactory userFactory;

    public UserController(DBServiceUser dbServiceUser, UserFactory userFactory) {
        this.dbServiceUser = dbServiceUser;
        this.userFactory = userFactory;
    }

    @GetMapping({"/", "/user/list"})
    public String userListView(Model model) {
        List<User> users = dbServiceUser.getAllUser();
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@RequestParam String name, @RequestParam String street, @RequestParam String[] phones) {
        dbServiceUser.saveUser(userFactory.createUser(name, street, phones));
        return new RedirectView("/user/list", true);
    }

}
