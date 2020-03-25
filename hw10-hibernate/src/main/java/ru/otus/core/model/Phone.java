package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "tPhones")
public class Phone {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "phone_number", nullable = false)
  private String number;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Phone() {
  }

  public Phone(String number, User user) {
    this.number = number;
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Phone{" +
            "id=" + id +
            ", number='" + number + '\'' +
            '}';
  }
}
