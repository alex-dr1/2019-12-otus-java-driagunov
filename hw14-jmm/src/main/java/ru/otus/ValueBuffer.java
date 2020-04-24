package ru.otus;

public class ValueBuffer {
  private int element = -1;

  public synchronized void put(int value) throws InterruptedException {
    while (this.element != -1){
      this.wait();
    }

    this.element = value;
    this.notifyAll();
  }

  public synchronized int get() throws InterruptedException {
    while (this.element == -1){
      this.wait();
    }

    int result = this.element;
    this.element = -1;
    this.notifyAll();
    return result;
  }
}
