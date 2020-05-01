package ru.otus.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.db.service.UserFactory;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageType;
import ru.otus.messagesystem.MsClient;
import ru.otus.messagesystem.RequestHandler;

import javax.annotation.PostConstruct;

@Service()
public class DataBaseInit {
  private final MessageSystem messageSystem;
  private final MsClient databaseMsClient;
  private final RequestHandler getUserAllRequest;
  private final RequestHandler addUserRequest;
  private final UserFactory userFactory;

  public DataBaseInit(MessageSystem messageSystem,
                      @Qualifier(value = "dataBase") MsClient databaseMsClient,
                      @Qualifier(value = "allUserRequest") RequestHandler getUserAllRequest,
                      @Qualifier(value = "addUserRequest") RequestHandler addUserRequest,
                      UserFactory userFactory) {
    this.messageSystem = messageSystem;
    this.databaseMsClient = databaseMsClient;
    this.getUserAllRequest = getUserAllRequest;
    this.addUserRequest = addUserRequest;
    this.userFactory = userFactory;
  }

  @PostConstruct
  void init(){
    messageSystem.addClient(databaseMsClient);
    databaseMsClient.addHandler(MessageType.USER_ALL, getUserAllRequest);
    databaseMsClient.addHandler(MessageType.USER_ADD, addUserRequest);
  }
}
