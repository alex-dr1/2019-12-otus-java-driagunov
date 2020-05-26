package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.db.DBServiceUser;
import ru.otus.db.DbServiceUserImpl;
import ru.otus.db.handlers.AddUserRequestHandler;
import ru.otus.db.handlers.GetUserAllRequestHandler;
import ru.otus.requesthandlers.RequestHandler;
import ru.otus.requesthandlers.WatchDogRequestHandler;
import ru.otus.db.repository.dao.UserDao;
import ru.otus.db.repository.dao.UserDaoHibernate;
import ru.otus.db.repository.hibernate.HibernateUtils;
import ru.otus.db.repository.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.db.repository.model.User;
import ru.otus.db.service.DataBaseInitializerDefaultRecord;
import ru.otus.db.service.UserFactory;
import ru.otus.db.service.UserFactoryImpl;
import ru.otus.message.SocketMsg;
import ru.otus.message.SocketMsgType;
import ru.otus.db.repository.model.Address;
import ru.otus.db.repository.model.Phone;


import java.io.*;
import java.net.Socket;
import java.util.*;

public class DBService {
	private static final Logger logger = LoggerFactory.getLogger(DBService.class);
	private static final String HOST = "localhost";
	private static final int PORT = 8091;

	private final Map<String, RequestHandler> handlers = new HashMap<>();
	private final Queue<SocketMsg> socketMsgQueue = new LinkedList<>();
	private final String name;

	public DBService(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		String name = args[0];
		new DBService(name).start();
	}

	private DBServiceUser getDbServiceUser() {
		Class<?>[] annotatedClasses = {User.class, Address.class, Phone.class};
		SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", annotatedClasses);
		SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
		UserDao userDao = new UserDaoHibernate(sessionManager);
		return new DbServiceUserImpl(userDao);
	}


	private void start() {
		DBServiceUser dbServiceUser = getDbServiceUser();
		InsertDeFaultRecord(dbServiceUser);
		addHandler(SocketMsgType.NEW_USER, new AddUserRequestHandler(dbServiceUser));
		addHandler(SocketMsgType.GET_ALL_USER, new GetUserAllRequestHandler(dbServiceUser, name));
		addHandler(SocketMsgType.WATCH_DOG, new WatchDogRequestHandler(10L));
		go();
	}


	public void addHandler(SocketMsgType type, RequestHandler requestHandler) {
		this.handlers.put(type.getValue(), requestHandler);
	}

	private void InsertDeFaultRecord(DBServiceUser dbServiceUser) {
		UserFactory userFactory = new UserFactoryImpl();
		new DataBaseInitializerDefaultRecord(dbServiceUser, userFactory).addInitRecord();
	}

	private void go() {
		while (!Thread.currentThread().isInterrupted()) {
			process();
		}
	}

	private void process() {
		try {
			try (Socket clientSocket = new Socket(HOST, PORT)) {
				DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
				DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());

				sendSocketMsg(dOut);
				receiveSocketMSg(dIn);
			}
		} catch (Exception ex) {
			System.out.print("-");
			pause(10L);
		}
	}

	private void receiveSocketMSg(DataInputStream dIn) throws IOException {
		int length = dIn.readInt();
		if(length>0) {
			byte[] byteMsgIn = new byte[length];
			dIn.readFully(byteMsgIn, 0, byteMsgIn.length);
			SocketMsg socketMsgIn = Serializers.deserialize(byteMsgIn, SocketMsg.class);

			RequestHandler requestHandler = handlers.get(socketMsgIn.getType());
			requestHandler.handle(socketMsgIn).ifPresent(socketMsgQueue::offer);
			System.out.print("+");
		}
	}

	private void sendSocketMsg(DataOutputStream dOut) throws IOException {
		SocketMsg socketMsgOut = produceWatchDogSocketMsg();
		if(!socketMsgQueue.isEmpty()){
			socketMsgOut = socketMsgQueue.poll();
		}
//		logger.info("Send: {}", socketMsgOut);
		byte[] byteMsgOut = Serializers.serialize(socketMsgOut);
		dOut.writeInt(byteMsgOut.length);
		dOut.write(byteMsgOut);
	}

	private SocketMsg produceWatchDogSocketMsg() {
		return new SocketMsg(name, null, null, SocketMsgType.WATCH_DOG.getValue(), null);
	}

	private void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

