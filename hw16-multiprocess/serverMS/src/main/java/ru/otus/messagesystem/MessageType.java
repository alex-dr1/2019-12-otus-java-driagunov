package ru.otus.messagesystem;

public enum MessageType {
    SIMPLE_DATA("SimpleData");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
