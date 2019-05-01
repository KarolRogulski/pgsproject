package com.pgsproject.todo.services;

import com.pgsproject.todo.entities.Task;

import java.util.List;

public interface TaskService {

    Task findById(long id);

    List<Task> findAll();

    Task save(Task task);

    void update(Task task);

    void delete(Task task);
}
