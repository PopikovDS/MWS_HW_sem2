package com.mipt.popikovdmitriy.repository;

import java.util.List;
import java.util.Optional;

import com.mipt.popikovdmitriy.model.Task;

/**
 * Abstraction for task persistence operations.
 *
 * <p>
 * Defines the standard CRUD contract that all repository implementations must
 * fulfill. Concrete implementations may store data in memory, in a database, or
 * return pre-configured stub data.</p>
 *
 * @see com.mipt.popikovdmitriy.repository.InMemoryTaskRepository
 * @see com.mipt.popikovdmitriy.repository.StubTaskRepository
 */
public interface TaskRepository {

    Task create(String title, String description, Boolean completed);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    Task update(Task task);

    boolean deleteById(Long id);
}

