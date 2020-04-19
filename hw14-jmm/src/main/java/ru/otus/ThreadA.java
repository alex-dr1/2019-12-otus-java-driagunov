package ru.otus;

public class ThreadA implements Runnable {

  private final SequenceGenerator generator;
  private final ValueBuffer buffer;
  private final long pause;

  public ThreadA(ValueBuffer buffer, SequenceGenerator generator, long pause) {
    this.buffer = buffer;
    this.generator = generator;
    this.pause = pause;
  }

  @Override
  public void run() {

    while(true){
      try {
        int value = generator.get();
        Thread.sleep(pause);
        buffer.put(value);
        System.out.println("Thread-A: " + value);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }
}
