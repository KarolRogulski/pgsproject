package com.pgsproject.todo.Controllers;

import com.pgsproject.todo.controllers.TaskController;
import com.pgsproject.todo.entities.Task;
import com.pgsproject.todo.entities.User;
import com.pgsproject.todo.services.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private TaskService taskService;
    private long id = 1L;
    private String url = "/task";

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void findByIdWithAuthorization() throws Exception {
        when(taskService.findById(id)).thenReturn(new Task());

        mock.perform(get(url + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "username")
    public void findByIdTaskByOwner() throws Exception {
        Task task = new Task();
        User user = new User();
        user.setUsername("username");
        task.setUser(user);
        when(taskService.findById(id)).thenReturn(task);

        mock.perform(get(url + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    public void getAllByAdmin() throws Exception{
        when(taskService.findAll()).thenReturn(new ArrayList<>());

        mock.perform(get(url + "/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
