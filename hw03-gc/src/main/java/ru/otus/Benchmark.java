package ru.otus;

import java.util.List;
import java.util.Random;

public class Benchmark {
    private int size;
    private int loopCounter;
    private List<Integer> list;
    private Random random;

    public Benchmark(int size, int loopCounter, List<Integer> list) throws InterruptedException {
        this.size = size;
        this.loopCounter = loopCounter;
        this.list = list;
        random = new Random();
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < loopCounter; i++){
            listAdd();
            listRemove();
            Thread.sleep(1500);
        }
    }

    void listAdd () {
        for (int i = 0; i<size; i++){
            list.add(random.nextInt(1_000_000));
        }
    }

    void listRemove() {
        int start = list.size()-1;
        int end = start - size/2;
        list.subList(end, start).clear();
    }
}
