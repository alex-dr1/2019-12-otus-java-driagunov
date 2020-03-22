package ru.otus.core.model;


import java.math.BigDecimal;
import java.util.Objects;

public class Account {
  @Id
  private long no;
  private String type;
  private BigDecimal rest;

  public Account() {
  }

  public Account(long no, String type, BigDecimal rest) {
    this.no = no;
    this.type = type;
    this.rest = rest;
  }

  public long getNo() {
    return no;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getRest() {
    return rest;
  }

  public void setNo(long no) {
    this.no = no;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setRest(BigDecimal rest) {
    this.rest = rest;
  }

  @Override
  public String toString() {
    return "Account{" +
            "no=" + no +
            ", type='" + type + '\'' +
            ", rest=" + rest +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Account)) return false;
    Account account = (Account) o;
    return getNo() == account.getNo() &&
            Objects.equals(getType(), account.getType()) &&
            Objects.equals(getRest(), account.getRest());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNo(), getType(), getRest());
  }
}
