package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.db.repository.hibernate.HibernateUtils;
import ru.otus.db.repository.model.Address;
import ru.otus.db.repository.model.Phone;
import ru.otus.db.repository.model.User;
import ru.otus.front.FrontendService;
import ru.otus.front.FrontendServiceImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.MsClient;
import ru.otus.messagesystem.MsClientImpl;

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

  @Bean
  public MessageSystem createMessageSystem(){
    return new MessageSystemImpl();
  }

  @Bean(name = "dataBase")
  public MsClient createDataBaseClient(MessageSystem messageSystem){
    return new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
  }

  @Bean(name = "frontend")
  public MsClient createFrontendClient(MessageSystem messageSystem){
    return new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
  }

  @Bean
  public FrontendService createFrontendService(@Qualifier(value = "frontend") MsClient frontendMsClient){
    return new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
  }

}
