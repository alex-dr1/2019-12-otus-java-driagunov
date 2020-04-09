package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.repository.dbservice.DBServiceUser;
import ru.otus.repository.model.Address;
import ru.otus.repository.model.Phone;
import ru.otus.repository.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class UserController {

    private final DBServiceUser dbServiceUser;

    public UserController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
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
        dbServiceUser.saveUser(createUser(name, street, phones));
        return new RedirectView("/user/list", true);
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
