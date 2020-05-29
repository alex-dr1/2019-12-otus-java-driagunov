package ru.otus.msserver;

import ru.otus.message.SocketMsg;
import ru.otus.messagesystem.Message;
import ru.otus.producerqueue.ProducerQueueSocketMsg;

public interface MsServer {

    boolean sendMessage(Message message);

    void addMapQueueSocketMsg(Message msg);

    void start();

    String getName();

    <T> Message produceMessage(SocketMsg socketMsg);

    void addProducerQueue (String socketMsgType, ProducerQueueSocketMsg producerQueueSocketMsg);
    void removeProducerQueue (String socketMsgType);
}
