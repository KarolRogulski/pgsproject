package com.pgsproject.todo.services;

import com.pgsproject.todo.entities.Task;
import com.pgsproject.todo.repositories.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
public class TaskServiceTest {

    @InjectMocks
    TaskServiceImpl taskService;

    @Mock
    TaskRepository taskRepository;

    @Test
    public void addTask() {
        Task task = new Task();

        taskService.save(task);

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void findTaskById() {
        long someId = 522L;
        when(taskRepository.findById(someId)).thenReturn(java.util.Optional.of(new Task()));

        taskService.findById(someId);

        verify(taskRepository).findById(someId);
    }

    @Test
    public void findAllTasks() {
        taskService.findAll();

        verify(taskRepository).findAll();
    }

    @Test
    public void updateTask() {
        Task task = new Task();
        when(taskRepository.findById(task.getId())).thenReturn(java.util.Optional.of(task));

        taskService.update(task);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void deleteTask() {
        Task task = new Task();
        taskService.delete(task);

        verify(taskRepository, times(1)).delete(task);
    }
}
