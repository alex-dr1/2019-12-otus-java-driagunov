package ru.otus;


public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {}

    @Override
    public void printNumber(int num) {}

    @Override
    public String toString() {
        return "TestLogging{}";
    }
}
