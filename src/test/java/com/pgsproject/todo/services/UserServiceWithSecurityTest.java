package com.pgsproject.todo.services;

import com.pgsproject.todo.TodoApplication;
import com.pgsproject.todo.exceptions.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TodoApplication.class})
public class UserServiceWithSecurityTest {

    @Autowired
    UserService userService;

    @Test(expected = AccessDeniedException.class)
    @WithAnonymousUser
    public void findAllWithAnonymous_throwException() {
        userService.findAll();
    }

    @Test(expected = UserNotFoundException.class)
    @WithMockUser( username = "username", roles = {"USER"})
    public void findById() {
        userService.findById(1L).getUsername();
    }

}
