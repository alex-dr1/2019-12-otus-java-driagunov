package ru.otus;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.message.SocketMsgType;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.msserver.MsServer;
import ru.otus.msserver.MsServerImpl;
import ru.otus.producerqueue.ProducerQueueSocketMsgGetAllUser;
import ru.otus.producerqueue.ProducerQueueSocketMsgNewUser;
import ru.otus.producerqueue.ProducerQueueSocketMsgPtp;

import java.io.IOException;

public class ServerMSMain {
  private static final Logger logger = LoggerFactory.getLogger(ServerMSMain.class);
  private static final String FRONTEND_SERVER = "ServerFE";
  private static final String BACKEND_SERVER = "ServerBE";


  public static void main(String[] args) throws InterruptedException {

    MessageSystem messageSystem = new MessageSystemImpl();

    MsServer msServerFrontend = new MsServerImpl(FRONTEND_SERVER, BACKEND_SERVER, messageSystem, 8090);
    msServerFrontend.addProducerQueue(SocketMsgType.ALL_USER.getValue(), new ProducerQueueSocketMsgPtp());
    msServerFrontend.addProducerQueue(SocketMsgType.ID_USER.getValue(), new ProducerQueueSocketMsgPtp());

    MsServer msServerBackend = new MsServerImpl(BACKEND_SERVER, FRONTEND_SERVER, messageSystem, 8091);
    msServerBackend.addProducerQueue(SocketMsgType.NEW_USER.getValue(), new ProducerQueueSocketMsgNewUser());
    msServerBackend.addProducerQueue(SocketMsgType.GET_ALL_USER.getValue(), new ProducerQueueSocketMsgGetAllUser());

    messageSystem.addClient(msServerFrontend);
    messageSystem.addClient(msServerBackend);

    messageSystem.start();

    cmdTerminal("gnome-terminal -- java -jar ./database/target/database-16.0-jar-with-dependencies.jar DB1");
    cmdTerminal("gnome-terminal -- java -jar ./database/target/database-16.0-jar-with-dependencies.jar DB2");

    cmdTerminal("gnome-terminal -- java -jar ./frontend/target/frontend-16.0.jar --server.port=8080 --fs.name=FE1 --fs.host=localhost --fs.port=8090");
    cmdTerminal("gnome-terminal -- java -jar ./frontend/target/frontend-16.0.jar --server.port=8081 --fs.name=FE2 --fs.host=localhost --fs.port=8090");

    logger.info("Done.");
  }

  private static void cmdTerminal(String cmdLine) {
    String[] cmdLineSplit = cmdLine.split(" ");
//    var currentDir = new File("./target/classes");
    try {
      new ProcessBuilder(cmdLineSplit)
          .inheritIO()
//              .directory(currentDir)
              .start();
    } catch (IOException e) {
      logger.error("Error run command {}", cmdLine);
      e.printStackTrace();
    }
  }


}
