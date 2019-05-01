package com.pgsproject.todo.services;

import com.pgsproject.todo.entities.Task;
import com.pgsproject.todo.exceptions.TaskNotFoundException;
import com.pgsproject.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepo;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepo){
        this.taskRepo = taskRepo;
    }

    @Override
    @PostAuthorize("(hasRole('ROLE_ADMIN') or returnObject.task.user.username == authentication.principal.username)")
    public Task findById(long id){
        Optional<Task> optional = taskRepo.findById(id);
            return optional.orElse(null);
    }

    //Return all tasks in DB
    @Override
    @Secured("ROLE_ADMIN")
    public List<Task> findAll(){
        Iterable<Task> iterable = taskRepo.findAll();
        List<Task> target = new ArrayList<>();
        iterable.forEach(target::add);
        return target;
    }

    //Save given task
    @Override
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public Task save(Task task){
        return taskRepo.save(task);
    }

    //Update given task
    @Override
    @PreAuthorize("(hasRole('ROLE_ADMIN') or #task.user.username == authentication.principal.username)")
    public void update(Task task){
        Task taskToUpdate;
        Optional<Task> optional = taskRepo.findById(task.getId());
        if(optional.isPresent()) {
            taskToUpdate = optional.get();
            taskToUpdate.setDescription(task.getDescription());
            taskRepo.save(task);
        }
        else{
            throw new TaskNotFoundException(task.getId());
        }
    }

    @Override
    @PreAuthorize("(hasRole('ROLE_ADMIN') or #task.user.username  == authentication.principal.username )")
    public void delete(Task task){
        taskRepo.delete(task);
    }
}
