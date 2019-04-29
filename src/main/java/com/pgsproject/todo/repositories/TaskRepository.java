package com.pgsproject.todo.repositories;

import com.pgsproject.todo.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
