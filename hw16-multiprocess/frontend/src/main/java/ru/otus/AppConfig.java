package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.db.service.UserFactory;
import ru.otus.db.service.UserFactoryImpl;
import ru.otus.front.fontetdservice.FrontendService;
import ru.otus.front.fontetdservice.FrontendServiceImpl;
import ru.otus.front.handlers.AllUserResponseHandler;
import ru.otus.front.handlers.IdUserResponseHandler;
import ru.otus.message.SocketMsgType;
import ru.otus.requesthandlers.WatchDogRequestHandler;


@Configuration
@EnableWebSocketMessageBroker
public class AppConfig implements WebSocketMessageBrokerConfigurer {

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
  public FrontendService createFrontendService (@Value("${fs.name}") String name,
                                                @Value("${fs.host}") String host,
                                                @Value("${fs.port}") int port){
    FrontendService frontendService = new FrontendServiceImpl(name, host, port);
    frontendService.addHandler(SocketMsgType.WATCH_DOG, new WatchDogRequestHandler(10L));
    frontendService.addHandler(SocketMsgType.ID_USER, new IdUserResponseHandler(frontendService));
    frontendService.addHandler(SocketMsgType.ALL_USER, new AllUserResponseHandler(frontendService));
    return frontendService;
  }

  @Bean
  public UserFactory createUserFactory(){
    return new UserFactoryImpl();
  }
}





