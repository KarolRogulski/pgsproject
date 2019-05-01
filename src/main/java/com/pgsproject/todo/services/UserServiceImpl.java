package com.pgsproject.todo.services;

import com.pgsproject.todo.entities.Role;
import com.pgsproject.todo.entities.User;
import com.pgsproject.todo.exceptions.UserNotFoundException;
import com.pgsproject.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @PostAuthorize("(hasRole('ROLE_ADMIN') or returnObject.user.username == authentication.principal.username)")
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    //Return user only if admin or user with same username
    //If user not found throw exception
    @Override
    @PostAuthorize("(hasRole('ROLE_ADMIN') or returnObject.user.username == authentication.principal.username)")
    public User findById(long id) {
        Optional<User> optional = userRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else throw new UserNotFoundException(id);
    }

    //Return all users in DB
    @Override
    @Secured("ROLE_ADMIN")
    public List<User> findAll() {
        Iterable<User> iterable = userRepo.findAll();
        List<User> target = new ArrayList<>();
        iterable.forEach(target::add);
        return target;
    }

    //Save given user
    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setName("ROLE_USER");
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(new HashSet<>(roles));
        return userRepo.save(user);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public List<User> findAll(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<User> users = userRepo.findAll(pageableRequest);
        return users.getContent();
    }

    //Update given user
    @Override
    @PreAuthorize("(hasRole('ROLE_ADMIN') or #user.username == authentication.principal.username)")
    public void update(User user, String newName) {
        User userToUpdate;
        Optional<User> optional = userRepo.findById(user.getId());
        if (optional.isPresent()) {
            userToUpdate = optional.get();
            userToUpdate.setUsername(newName);
            userRepo.save(user);
        } else {
            throw new UserNotFoundException(user.getId());
        }
    }

    @Override
    @PreAuthorize("(hasRole('ROLE_ADMIN') or #user.username == authentication.principal.username)")
    public void delete(User user) {
        userRepo.delete(user);
    }
}