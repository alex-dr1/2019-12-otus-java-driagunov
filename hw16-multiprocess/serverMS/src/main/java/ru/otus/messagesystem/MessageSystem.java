package ru.otus.messagesystem;

import ru.otus.msserver.MsServer;

public interface MessageSystem {

    void addClient(MsServer msClient);

    void removeClient(String clientId);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;

    void start();

    int currentQueueSize();
}

