package ru.otus.msclient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.message.SocketMsgType;
import ru.otus.Serializers;
import ru.otus.message.SocketMsg;
import ru.otus.msserver.MapQueueSocketMsg;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест очереди socket-сообщений на отправку")
class MapQueueSocketMsgTest {

	@Test
	@DisplayName("Добавление сообщений одному клиенту")
	void addGetSocketMsg1() {
		MapQueueSocketMsg queue = new MapQueueSocketMsg();
		String client1 = "FE1";
		String client2 = "FE2";
		byte[] mes1 = Serializers.serialize("TestObject1");
		byte[] mes2 = Serializers.serialize("TestObject2");
		SocketMsg socketMsg1 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes1);
		SocketMsg socketMsg2 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes2);

		queue.add(client1, socketMsg1);
		queue.add(client1, socketMsg2);

		assertEquals(socketMsg1, queue.getSocketMsg(client1));
		assertEquals(socketMsg2, queue.getSocketMsg(client1));
	}

	@Test
	@DisplayName("Добавление сообщений разным клиентам клиентам")
	void addGetSocketMsg2() {
		MapQueueSocketMsg queue = new MapQueueSocketMsg();
		String client1 = "FE1";
		String client2 = "FE2";
		byte[] mes1 = Serializers.serialize("TestObject1");
		byte[] mes2 = Serializers.serialize("TestObject2");
		SocketMsg socketMsg1 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes1);
		SocketMsg socketMsg2 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes2);

		queue.add(client1, socketMsg1);
		queue.add(client2, socketMsg2);

		assertEquals(socketMsg1, queue.getSocketMsg(client1));
		assertEquals(socketMsg2, queue.getSocketMsg(client2));
	}

	@Test
	@DisplayName("Если нет сообщений")
	void addGetSocketMsg3() {
		MapQueueSocketMsg queue = new MapQueueSocketMsg();
		String client1 = "FE1";
		String client2 = "FE2";
		byte[] mes1 = Serializers.serialize("TestObject1");

		SocketMsg socketMsg1 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes1);

		queue.add(client1, socketMsg1);
		assertNull(queue.getSocketMsg(client2));
	}

	@Test
	@DisplayName("Тест очистки")
	void clear() {
		MapQueueSocketMsg queue = new MapQueueSocketMsg();
		String client1 = "FE1";
		String client2 = "FE2";
		byte[] mes1 = Serializers.serialize("TestObject1");
		byte[] mes2 = Serializers.serialize("TestObject2");
		SocketMsg socketMsg1 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes1);
		SocketMsg socketMsg2 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes2);

		queue.add(client1, socketMsg1);
		queue.add(client2, socketMsg2);
		queue.clear();
		assertNull(queue.getSocketMsg(client1));
		assertNull(queue.getSocketMsg(client2));

	}

	@Test
	@DisplayName("Проверка очереди на пустоту")
	void empty() {
		MapQueueSocketMsg queue = new MapQueueSocketMsg();
		String client1 = "FE1";
		String client2 = "FE2";
		byte[] mes1 = Serializers.serialize("TestObject1");
		byte[] mes2 = Serializers.serialize("TestObject2");
		SocketMsg socketMsg1 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes1);
		SocketMsg socketMsg2 = new SocketMsg(client1, null, null, SocketMsgType.NEW_USER.getValue(),mes2);

		queue.add(client1, socketMsg1);
		queue.add(client2, socketMsg2);
		queue.clear();

		assertTrue(queue.isEmpty());
	}

	Set<String> clients = new HashSet<>();
	@Test
	@DisplayName("Проверка замены __toAll__")
	void toAll() {
		MapQueueSocketMsg queue = new MapQueueSocketMsg();
		String toAllClient = "__toAll__";

		byte[] mes1 = Serializers.serialize("TestObject1");

		SocketMsg socketMsg1 = new SocketMsg("FE1", null, null, SocketMsgType.NEW_USER.getValue(),mes1);

		queue.add(toAllClient, socketMsg1);

		String client1 = "DB1";
		String client2 = "DB2";
		String client3 = "DB3";


		clients.add(client1);
		clients.add(client2);
		clients.add(client3);

		checkMessageToAll(queue);

		assertNull(queue.getSocketMsg(toAllClient));
		assertNotNull(queue.getSocketMsg(client1));
		assertNotNull(queue.getSocketMsg(client2));
		assertNotNull(queue.getSocketMsg(client3));

	}


	private MapQueueSocketMsg checkMessageToAll(MapQueueSocketMsg localQueueSocketMsg) {
		SocketMsg socketMsg = localQueueSocketMsg.getSocketMsg("__toAll__");
		if(socketMsg != null ) {
			for (String client : clients) {
				localQueueSocketMsg.add(client, socketMsg);
			}
		}
		return localQueueSocketMsg;
	}
}