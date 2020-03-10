package ru.otus;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BagOfValues {
  private final boolean valBoolean;
  private final char valChar;
  private final short valShort;
  private final int valInteger;
  private final long valLong;
  private final float valFloat;
  private final double valDouble;
  private final String valString;
  private final Person valPerson;
  private final List<String> valStringList;
  private final Set<Long> valLongSet;

  public BagOfValues(boolean valBoolean, char valChar, short valShort, int valInteger, long valLong, float valFloat, double valDouble, String valString, Person valPerson, List<String> valStringList, Set<Long> valLongSet) {
    this.valBoolean = valBoolean;
    this.valChar = valChar;
    this.valShort = valShort;
    this.valInteger = valInteger;
    this.valLong = valLong;
    this.valFloat = valFloat;
    this.valDouble = valDouble;
    this.valString = valString;
    this.valPerson = valPerson;
    this.valStringList = valStringList;
    this.valLongSet = valLongSet;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BagOfValues)) return false;
    BagOfValues that = (BagOfValues) o;
    return valBoolean == that.valBoolean &&
            valChar == that.valChar &&
            valShort == that.valShort &&
            valInteger == that.valInteger &&
            valLong == that.valLong &&
            Float.compare(that.valFloat, valFloat) == 0 &&
            Double.compare(that.valDouble, valDouble) == 0 &&
            Objects.equals(valString, that.valString) &&
            Objects.equals(valPerson, that.valPerson) &&
            Objects.equals(valStringList, that.valStringList) &&
            Objects.equals(valLongSet, that.valLongSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valBoolean, valChar, valShort, valInteger, valLong, valFloat, valDouble, valString, valPerson, valStringList, valLongSet);
  }

  @Override
  public String toString() {
    return "BagOfValues{" +
            "valBoolean=" + valBoolean +
            ", valChar=" + valChar +
            ", valShort=" + valShort +
            ", valInteger=" + valInteger +
            ", valLong=" + valLong +
            ", valFloat=" + valFloat +
            ", valDouble=" + valDouble +
            ", valString='" + valString + '\'' +
            ", valPerson=" + valPerson +
            ", valStringList=" + valStringList +
            ", valLongSet=" + valLongSet +
            '}';
  }
}
