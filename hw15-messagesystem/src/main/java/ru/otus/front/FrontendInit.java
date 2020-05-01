package ru.otus.front;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageType;
import ru.otus.messagesystem.MsClient;
import ru.otus.messagesystem.RequestHandler;

import javax.annotation.PostConstruct;

@Service
public class FrontendInit {
  private final MessageSystem messageSystem;
  private final MsClient frontendMsClient;
  private final RequestHandler getUserAllResponse;
  private final RequestHandler addUserResponse;

  public FrontendInit(MessageSystem messageSystem,
                      @Qualifier(value = "frontend") MsClient frontendMsClient,
                      @Qualifier(value = "userAllResponse")RequestHandler getUserAllResponse,
                      @Qualifier(value = "addUserResponse")RequestHandler addUserResponse) {
    this.messageSystem = messageSystem;
    this.frontendMsClient = frontendMsClient;
    this.getUserAllResponse = getUserAllResponse;
    this.addUserResponse = addUserResponse;
  }

  @PostConstruct
  void init(){
    messageSystem.addClient(frontendMsClient);
    frontendMsClient.addHandler(MessageType.USER_ALL, getUserAllResponse);
    frontendMsClient.addHandler(MessageType.USER_ADD, addUserResponse);
  }
}
