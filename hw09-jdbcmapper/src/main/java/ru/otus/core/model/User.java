package ru.otus.core.model;

import java.util.Objects;

public class User {

  @Id
  private long id;
  private String name;
  private int age;

  public User(){}

  public User(long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return getId() == user.getId() &&
            getAge() == user.getAge() &&
            Objects.equals(getName(), user.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getAge());
  }
}
