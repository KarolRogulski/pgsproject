package com.pgsproject.todo.services;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String login, String password);
}
