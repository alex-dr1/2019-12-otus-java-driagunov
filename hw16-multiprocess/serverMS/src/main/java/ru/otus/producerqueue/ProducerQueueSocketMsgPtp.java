package ru.otus.producerqueue;

import ru.otus.message.SocketMsg;
import ru.otus.msserver.MapQueueSocketMsg;

import java.util.Optional;

public class ProducerQueueSocketMsgPtp implements ProducerQueueSocketMsg {
	@Override
	public Optional<MapQueueSocketMsg> addQueueOutPost(MapQueueSocketMsg queueOutPost, SocketMsg socketMsg) {
		queueOutPost.add(socketMsg.getClientTo(), socketMsg);
		return Optional.of(queueOutPost);
	}
}
