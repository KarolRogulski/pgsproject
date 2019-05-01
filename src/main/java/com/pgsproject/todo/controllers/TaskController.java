package com.pgsproject.todo.controllers;

import com.pgsproject.todo.entities.Task;
import com.pgsproject.todo.exceptions.TaskNotFoundException;
import com.pgsproject.todo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //Returning task by given id and if successfully return http OK
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Task returnById(@PathVariable long id){
        Task task = taskService.findById(id);
        if(task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    //Returning all task from db or empty list if any tasks exists
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Task> returnAll(){
        return taskService.findAll();
    }

    //Save new task and return saved task and http CREATED
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Task saveTask(@RequestBody Task task){
        return taskService.save(task);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTask(@Valid @RequestBody Task task){
        taskService.update(task);
    }

    //Delete given task
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@RequestBody Task task){
        taskService.delete(task);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error taskNotFound(TaskNotFoundException e){
        long taskId = e.getTaskId();
        return new Error("Task with id = " + taskId + " not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
