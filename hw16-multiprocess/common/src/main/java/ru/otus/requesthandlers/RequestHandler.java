package ru.otus.requesthandlers;


import ru.otus.message.SocketMsg;

import java.util.Optional;

public interface RequestHandler {
    Optional<SocketMsg> handle(SocketMsg socketMsg);
}
