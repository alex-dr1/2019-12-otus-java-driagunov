package ru.otus;

public class SequenceGenerator {
  private final int[] sequence = new int[]{1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2};
  private int counter;

  public SequenceGenerator() {
    this.counter = 0;
  }

  public int get() {
    int result = sequence[counter];

    counter++;
    if (counter >= sequence.length)
      counter = 0;

    return result;
  }
}
