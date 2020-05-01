package ru.otus.messagesystem;

public enum MessageType {
    USER_ID("UserId"),
    USER_ADD("UserAdd"),
    USER_ALL("UserAll");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
