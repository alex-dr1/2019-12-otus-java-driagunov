package ru.otus;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface myClass = IoC.createMyClass(new TestLogging());

        myClass.calculation(6);
        myClass.calculation(1,2,3);
    }
}