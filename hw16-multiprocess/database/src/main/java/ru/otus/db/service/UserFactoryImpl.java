package ru.otus.db.service;

import ru.otus.db.repository.model.Address;
import ru.otus.db.repository.model.Phone;
import ru.otus.db.repository.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

  @Override
  public User createUser(User userForm) {
    User user = new User();
    user.setName(userForm.getName());
    Address addressUser = new Address(userForm.getAddress().getStreet(), user);
    user.setAddress(addressUser);
    List<Phone> phoneListForm = userForm.getPhones();
    List<Phone> phoneList = new ArrayList<>();
    for (Phone phoneForm: phoneListForm){
      phoneList.add(new Phone(phoneForm.getNumber(), user));
    }
    user.setPhones(phoneList);
    return user;
  }
}
