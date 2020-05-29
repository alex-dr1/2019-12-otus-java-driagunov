package ru.otus.message;

public enum SocketMsgType {
    NEW_USER("NewUser"),
    GET_ALL_USER("GetAllUser"),
    ID_USER("IDUser"),
    ALL_USER("AllUser"),
    WATCH_DOG("WatchDog");

    private final String value;

    SocketMsgType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
