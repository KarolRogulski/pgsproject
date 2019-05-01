package com.pgsproject.todo.exceptions;

public class TaskNotFoundException extends RuntimeException {

    private long taskId;
    public TaskNotFoundException(long id){
        this.taskId = taskId;
    }

    public long getTaskId(){
        return taskId;
    }
}
