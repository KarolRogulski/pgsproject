package com.pgsproject.todo.services;

import com.pgsproject.todo.entities.User;
import com.pgsproject.todo.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    UserRepository userRepository;

    @Test
    public void addUser(){
        User user = new User();
        user.setPassword("password");
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("******");

        userService.save(user);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void findUserById(){
        long someId = 522L;
        when(userRepository.findById(someId)).thenReturn(java.util.Optional.of(new User()));

        userService.findById(someId);

        verify(userRepository).findById(someId);
    }

    @Test
    public void findAllUsers(){
        userService.findAll();

        verify(userRepository).findAll();
    }

    @Test
    public void updateUser(){
        User user = new User();
        String name = "newName";
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        userService.update(user, name);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void deleteUser(){
        User user = new User();
        userService.delete(user);

        verify(userRepository, times(1)).delete(user);
    }
}
