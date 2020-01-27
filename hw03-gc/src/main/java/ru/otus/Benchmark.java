package ru.otus;

import java.util.List;

public class Benchmark {
    private int size;
    private int loopCounter;
    private List<Long> list;

    public Benchmark(int size, int loopCounter, List<Long> list) throws InterruptedException {
        this.size = size;
        this.loopCounter = loopCounter;
        this.list = list;
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < loopCounter; i++){
            listAdd();
            listRemove();
        }
    }

    void listAdd () throws InterruptedException {
        for (int i = 0; i<size; i++){
            list.add(System.currentTimeMillis());
            //Thread.sleep(10);
        }
    }

    void listRemove() {
        int start = list.size()-1;
        int end = start - size/2;
        list.subList(end, start).clear();
    }
}
