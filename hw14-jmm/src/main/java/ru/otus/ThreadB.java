package ru.otus;

public class ThreadB implements Runnable {

  private final ValueBuffer buffer;
  private final long pause;

  public ThreadB(ValueBuffer buffer, long pause) {
    this.buffer = buffer;
    this.pause = pause;
  }

  @Override
  public void run() {
    while(true){
      try {
        int value = buffer.get();
        System.out.println("Thread-B:   " + value);
        Thread.sleep(pause);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }
}
