package com.pgsproject.todo.repositories;

import com.pgsproject.todo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    
}
