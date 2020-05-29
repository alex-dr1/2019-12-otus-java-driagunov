package ru.otus.producerqueue;

import ru.otus.message.SocketMsg;
import ru.otus.msserver.MapQueueSocketMsg;

import java.util.Optional;

public class ProducerQueueSocketMsgNewUser implements ProducerQueueSocketMsg {
	@Override
	public Optional<MapQueueSocketMsg> addQueueOutPost(MapQueueSocketMsg queueOutPost, SocketMsg socketMsg) {
		queueOutPost.add("__toAll__", socketMsg);
		return Optional.of(queueOutPost);
	}
}
