package ru.otus;

public interface TestLoggingInterface {

    void calculation(int param);

    void calculation(int param1, int param2, int param3);

    void calculationNOTLOG(int param1, int param2, int param3);

    void printNumber(int num);
}
