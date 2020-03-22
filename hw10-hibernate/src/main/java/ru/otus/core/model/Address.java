package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "tAddress")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "address_street", nullable = false)
  private String street;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Address() {

  }

  public Address(String street, User user) {
    this.street = street;
    this.user = user;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public String toString() {
    return "Address{" +
            "id=" + id +
            ", street='" + street + '\'' +
            '}';
  }
}
