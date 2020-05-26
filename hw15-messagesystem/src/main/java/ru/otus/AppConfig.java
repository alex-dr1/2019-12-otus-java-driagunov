package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.db.DBServiceUser;
import ru.otus.db.DbServiceUserImpl;
import ru.otus.db.handlers.AddUserRequestHandler;
import ru.otus.db.handlers.GetUserAllRequestHandler;
import ru.otus.db.repository.dao.UserDao;
import ru.otus.db.repository.hibernate.HibernateUtils;
import ru.otus.db.repository.model.Address;
import ru.otus.db.repository.model.Phone;
import ru.otus.db.repository.model.User;
import ru.otus.front.FrontendService;
import ru.otus.front.FrontendServiceImpl;
import ru.otus.front.handlers.AddUserResponseHandler;
import ru.otus.front.handlers.GetUserAllResponseHandler;
import ru.otus.messagesystem.*;


@Configuration
@EnableWebSocketMessageBroker
public class AppConfig implements WebSocketMessageBrokerConfigurer {
  private static final String FRONTEND_SERVICE_CLIENT_NAME = "FRONTEND";
  private static final String DATABASE_SERVICE_CLIENT_NAME = "DATABASE";

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/gs-guide-websocket").withSockJS();
  }

  @Bean
  public SessionFactory createSessionFactory(){
    Class<?>[] annotatedClasses = {User.class, Address.class, Phone.class};
    return HibernateUtils.buildSessionFactory("hibernate.cfg.xml", annotatedClasses);
  }

  @Bean(destroyMethod = "dispose")
  public MessageSystem createMessageSystem(){
    return new MessageSystemImpl();
  }

  @Bean(name = "dataBase")
  public MsClient createDataBaseMSClient(MessageSystem messageSystem, DBServiceUser dbServiceUser){
    MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
    messageSystem.addClient(databaseMsClient);
    databaseMsClient.addHandler(MessageType.USER_ALL, new GetUserAllRequestHandler(dbServiceUser));
    databaseMsClient.addHandler(MessageType.USER_ADD, new AddUserRequestHandler(dbServiceUser));
    return databaseMsClient;
  }

  @Bean(name = "frontend")
  public MsClient createFrontendMSClient(MessageSystem messageSystem){
    MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
    messageSystem.addClient(frontendMsClient);
    return frontendMsClient;
  }

  @Bean
  public FrontendService createFrontendService(@Qualifier(value = "frontend") MsClient frontendMsClient){
    FrontendService frontendService =  new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
    frontendMsClient.addHandler(MessageType.USER_ALL, new GetUserAllResponseHandler(frontendService));
    frontendMsClient.addHandler(MessageType.USER_ADD, new AddUserResponseHandler(frontendService));
    return frontendService;
  }
}





