package com.pgsproject.todo.Controllers;

import com.pgsproject.todo.controllers.UserController;
import com.pgsproject.todo.entities.User;
import com.pgsproject.todo.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private UserService userService;
    private long id = 1L;
    private String url = "/user";

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void findByIdWithAuthorization() throws Exception {
        when(userService.findById(id)).thenReturn(new User());

        mock.perform(get(url + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "username")
    public void findByIdUserByHimself() throws Exception {
        User user = new User();
        user.setUsername("username");
        when(userService.findById(id)).thenReturn(user);

        mock.perform(get(url + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    public void getAllByAdmin() throws Exception {
        when(userService.findAll()).thenReturn(new ArrayList<>());

        mock.perform(get(url + "/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
