package ru.otus.dao;

import ru.otus.model.Address;
import ru.otus.model.Phone;
import ru.otus.model.User;

import java.util.*;

public class UserDaoImpl implements UserDao {

    private final Map<Long, User> users;

    public UserDaoImpl() {

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Вася Пупкин");
        Address addressUser = new Address("Пр. Ленина, 1", user1);
        Phone phone1User = new Phone("+72390423094", user1);
        Phone phone2User = new Phone("+5324343433", user1);
        user1.setAddress(addressUser);
        user1.setPhones(List.of(phone1User, phone2User));

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Люся Педалькина");
        Address addressUser2 = new Address("Ул. Карла Маркса, 2", user2);
        Phone phone1User2 = new Phone("+79890238256", user2);
        Phone phone2User2 = new Phone("+743382752930", user2);
        Phone phone3User2 = new Phone("+73438242420", user2);
        user2.setAddress(addressUser2);
        user2.setPhones(List.of(phone1User2, phone2User2, phone3User2));

        users = new HashMap<>();

        users.put(1L, user1);
        users.put(2L, user2);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public long saveUser(User user) {
        users.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public Map<Long, User> getAllUsers() {
        return users;
    }
}
