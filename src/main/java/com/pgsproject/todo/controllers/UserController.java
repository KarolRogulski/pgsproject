package com.pgsproject.todo.controllers;

import com.pgsproject.todo.entities.User;
import com.pgsproject.todo.exceptions.UserNotFoundException;
import com.pgsproject.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Returning user by given id and if successfully return http OK
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User returnById(@PathVariable long id){
        User user = userService.findById(id);
        if(user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    //Returning all user from db or empty list if any user exists
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> returnAll(){
        return userService.findAll();
    }

    //Return users paginated by parameters
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "30") int limit) {
        return userService.findAll(page, limit);
    }

    //Save new user and return saved user and http CREATED
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@Valid @RequestBody User user){
        return userService.save(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@RequestBody User user, @RequestParam String newName){
        userService.update(user, newName);
    }

    //Delete given user
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestBody User user){
        userService.delete(user);
    }



    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error userNotFound(UserNotFoundException e){
        long userId = e.getUserId();
        return new Error("User with id = " + userId + " not found");
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
