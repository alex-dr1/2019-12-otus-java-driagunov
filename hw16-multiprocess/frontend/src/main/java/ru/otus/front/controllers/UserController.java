package ru.otus.front.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.db.repository.model.User;
import ru.otus.db.service.UserFactory;
import ru.otus.front.fontetdservice.FrontendService;

import java.util.List;

@Controller
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final FrontendService frontendService;
  private final UserFactory userFactory;
  private final SimpMessagingTemplate template;

  public UserController(FrontendService frontendService, UserFactory userFactory, SimpMessagingTemplate template) {
    this.frontendService = frontendService;
    this.userFactory = userFactory;
    this.template = template;
  }

  @MessageMapping("/addUser")
   public void addUser(User userForm){
    User user = userFactory.createUser(userForm);
    frontendService.addUser(user, null/*this::sendTrueUserAdded*/);
    template.convertAndSend("/topic/mesAddUser", true);
  }

  @MessageMapping("/getUserList")
  public void getUserList(){
    frontendService.getUserAll(this::sendUserList);
  }

  private void sendUserList(List<User> userList){
    template.convertAndSend("/topic/mesUserList", userList);
  }
}
