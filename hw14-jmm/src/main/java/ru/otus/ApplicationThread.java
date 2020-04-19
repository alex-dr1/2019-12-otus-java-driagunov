package ru.otus;

public class ApplicationThread {
  public void run() {
    SequenceGenerator generator = new SequenceGenerator();
    ValueBuffer buffer = new ValueBuffer();
    Thread thread0 = new Thread(new ThreadA(buffer, generator, 1));
    Thread thread1 = new Thread(new ThreadB(buffer, 0));
    thread0.start();
    thread1.start();
  }
}
