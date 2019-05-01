package com.pgsproject.todo.exceptions;

public class UserNotFoundException extends RuntimeException {

    private long userId;
    public UserNotFoundException(long userId){
        this.userId = userId;
    }

    public long getUserId(){
        return userId;
    }
}
