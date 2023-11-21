package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getListUser();

    boolean createUser(User user);

    User readUser(long id);

    void deleteUser(long id);

    boolean updateUser(User user);

    User findByUsername(@Param("username") String username);
}
