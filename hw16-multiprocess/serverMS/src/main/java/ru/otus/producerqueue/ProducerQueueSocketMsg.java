package ru.otus.producerqueue;

import ru.otus.message.SocketMsg;
import ru.otus.msserver.MapQueueSocketMsg;

import java.util.Optional;

public interface ProducerQueueSocketMsg {
	Optional<MapQueueSocketMsg> addQueueOutPost(MapQueueSocketMsg queueOutPost, SocketMsg socketMsg);
}
