package ru.otus;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BagOfValues {
  private final boolean valueBoolean;
  private final char valueChar;
  private final short valueShort;
  private final int valueInteger;
  private final long valueLong;
  private final String valueString;
  private final List<String> stringList;
  private final Set<Long> longSet;

  public BagOfValues(boolean valueBoolean, char valueChar, short valueShort, int valueInteger, long valueLong, String valueString, List<String> stringList, Set<Long> longSet) {
    this.valueBoolean = valueBoolean;
    this.valueChar = valueChar;
    this.valueShort = valueShort;
    this.valueInteger = valueInteger;
    this.valueLong = valueLong;
    this.valueString = valueString;
    this.stringList = stringList;
    this.longSet = longSet;
  }

  @Override
  public String toString() {
    return "BagOfValues{" +
            "valueBoolean=" + valueBoolean +
            ", valueChar=" + valueChar +
            ", valueShort=" + valueShort +
            ", valueInteger=" + valueInteger +
            ", valueLong=" + valueLong +
            ", valueString='" + valueString + '\'' +
            ", stringList=" + stringList +
            ", longSet=" + longSet +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BagOfValues)) return false;
    BagOfValues that = (BagOfValues) o;
    return valueBoolean == that.valueBoolean &&
            valueChar == that.valueChar &&
            valueShort == that.valueShort &&
            valueInteger == that.valueInteger &&
            valueLong == that.valueLong &&
            Objects.equals(valueString, that.valueString) &&
            Objects.equals(stringList, that.stringList) &&
            Objects.equals(longSet, that.longSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valueBoolean, valueChar, valueShort, valueInteger, valueLong, valueString, stringList, longSet);
  }
}
