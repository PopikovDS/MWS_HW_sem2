package com.mipt.popikovdmitriy.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mipt.popikovdmitriy.exception.InvalidTaskException;
import com.mipt.popikovdmitriy.exception.TaskNotFoundException;
import com.mipt.popikovdmitriy.model.Task;
import com.mipt.popikovdmitriy.repository.TaskRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;
    private final LinkedHashMap<Long, Task> taskCache = new LinkedHashMap<>();

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void initCache() {
        try {
            repository.save(new Task(null, "Welcome", "First task created on startup", false));
            repository.save(new Task(null, "Readme", "Check API endpoints in controller", false));
            repository.save(new Task(null, "Done example", "This one is already completed", true));
        } catch (RuntimeException e) {
            log.debug("Preload tasks skipped/failed: {}", e.getMessage());
        }

        for (Task task : repository.findAll()) {
            if (task != null && task.getId() != null) {
                taskCache.put(task.getId(), task);
            }
        }

        log.info("Task cache initialized: {} entries", taskCache.size());
    }

    @PreDestroy
    public void clearCache() {
        log.info("Destroying TaskService. Cache size before destroy: {}", taskCache.size());
        Path out = Path.of("task-cache-stats.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(out, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(Instant.now() + " cacheSize=" + taskCache.size());
            writer.newLine();
        } catch (IOException e) {
            log.error("Failed to write cache stats to {}: {}", out.toAbsolutePath(), e.getMessage());
        }
        taskCache.clear();
    }

    public Task createTask(Task task) {
        if (task == null) {
            throw new InvalidTaskException("Task must not be null");
        }
        normalizeTaskForSave(task);
        Task created = repository.save(task);
        if (created != null && created.getId() != null) {
            taskCache.put(created.getId(), created);
        }
        return created;
    }

    public Task getTaskById(Long id) {
        if (id == null) {
            throw new TaskNotFoundException(null);
        }
        Task cached = taskCache.get(id);
        if (cached != null) {
            return cached;
        }
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskCache.put(id, task);
        return task;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task updateTask(Task task) {
        if (task == null || task.getId() == null) {
            throw new InvalidTaskException("Task id must be provided for update");
        }
        normalizeTaskForSave(task);
        Optional<Task> existing = repository.findById(task.getId());
        if (existing.isEmpty()) {
            throw new TaskNotFoundException(task.getId());
        }
        Task updated = repository.save(task);
        if (updated != null && updated.getId() != null) {
            taskCache.put(updated.getId(), updated);
        }
        return updated;
    }

    public void deleteTaskById(Long id) {
        if (id == null) {
            throw new TaskNotFoundException(null);
        }
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
        taskCache.remove(id);
    }

    private void normalizeTaskForSave(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new InvalidTaskException("Title must not be empty");
        }
        if (task.getDescription() == null) {
            throw new InvalidTaskException("Description must not be null");
        }
        if (task.isCompleted() == null) {
            task.setCompleted(false);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = TaskNotFoundException.class)
    public void bulkCompleteTasks(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        List<Task> tasks = repository.findAllById(ids);
        if (tasks.size() != ids.size()) {
            throw new TaskNotFoundException(null);
        }
        for (Task t : tasks) {
            t.setCompleted(true);
        }
        repository.saveAll(tasks);
        for (Task t : tasks) {
            if (t.getId() != null) {
                taskCache.put(t.getId(), t);
            }
        }
    }
}
