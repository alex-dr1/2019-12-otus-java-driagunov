package ru.otus.msserver;

import ru.otus.message.SocketMsg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MapQueueSocketMsg {
	private final Map<String, Queue<SocketMsg>> queueMap = new HashMap<>();

	public void add(String client, SocketMsg socketMsg){
		if (!queueMap.containsKey(client)){
			Queue<SocketMsg> newQueueSocketMsg = new LinkedList<>();
			newQueueSocketMsg.offer(socketMsg);
			queueMap.put(client, newQueueSocketMsg);
		} else {
			Queue<SocketMsg> queueSocketMsg = queueMap.get(client);
			queueSocketMsg.offer(socketMsg);
			queueMap.put(client, queueSocketMsg);
		}
	}

	public void clear(){
		queueMap.clear();
	}

	public SocketMsg getSocketMsg(String client){
		if (!queueMap.containsKey(client)){
			return null;
		} else {
			Queue<SocketMsg> queueSocketMsg = queueMap.get(client);
			SocketMsg result = queueSocketMsg.poll();
			if(queueSocketMsg.isEmpty()){
				queueMap.remove(client);
			}
			return result;
		}

	}

	public boolean isEmpty(){
		return queueMap.isEmpty();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder("|=== MapQueueSocketMsg ===|\n");
		queueMap.forEach((client, queue)->{
			out.append(client);
			out.append("----->\n");
			queue.forEach(q -> {
				out.append("           ");
				out.append(q.toString());
				out.append("\n");
			});
		});

		return out.toString();
	}
}
