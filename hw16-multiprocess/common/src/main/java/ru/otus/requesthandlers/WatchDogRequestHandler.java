package ru.otus.requesthandlers;

import ru.otus.message.SocketMsg;

import java.util.Optional;

public class WatchDogRequestHandler implements RequestHandler {
    private final long timePause;

    public WatchDogRequestHandler(long timePause) {
        this.timePause = timePause;
    }

    @Override
    public Optional<SocketMsg> handle(SocketMsg socketMsg) {
        pause(timePause);
        return Optional.empty();
    }

    private void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
