package com.pgsproject.todo.services;

import com.pgsproject.todo.entities.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

    User findById(long id);

    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void update(User user, String newName);

    void delete(User user);

    List<User> findAll(int page, int limit);
}
