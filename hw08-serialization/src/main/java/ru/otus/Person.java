package ru.otus;

import java.util.Objects;

public class Person {
    private final String firstName;
    private final int age;

    public Person(String firstName, int age) {
        this.firstName = firstName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(firstName, person.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, age);
    }
}
