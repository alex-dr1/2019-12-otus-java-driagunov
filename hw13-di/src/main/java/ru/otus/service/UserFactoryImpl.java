package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.repository.model.Address;
import ru.otus.repository.model.Phone;
import ru.otus.repository.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFactoryImpl implements UserFactory {
  @Override
  public User createUser(String pName, String pAddress, String[] pTelephones) {
    User user = new User();
    user.setName(pName);

    Address addressUser = new Address(pAddress, user);

    user.setAddress(addressUser);

    List<Phone> phoneList = Arrays.stream(pTelephones).map(tel -> new Phone(tel, user)).collect(Collectors.toList());

    user.setPhones(phoneList);

    return user;
  }
}
