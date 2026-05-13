package com.mipt.popikovdmitriy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
import java.util.Map;
import com.mipt.popikovdmitriy.scope.PrototypeScopedBean;
import com.mipt.popikovdmitriy.scope.RequestScopedBean;
import com.mipt.popikovdmitriy.service.PrototypeBeanService;
import com.mipt.popikovdmitriy.model.CreateTaskRequest;
import com.mipt.popikovdmitriy.model.Task;
import com.mipt.popikovdmitriy.service.TaskService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * REST controller that exposes CRUD endpoints for task management.
 *
 * <p>
 * All request/response bodies are validated using Jakarta Bean Validation.
 * Endpoints:
 * <ul>
 * <li>{@code POST   /api/tasks} — create a new task</li>
 * <li>{@code GET    /api/tasks/{id}} — retrieve a task by its identifier</li>
 * <li>{@code GET    /api/tasks} — list all tasks</li>
 * <li>{@code PUT    /api/tasks/{id}} — update an existing task</li>
 * <li>{@code DELETE /api/tasks/{id}} — delete a task</li>
 * </ul>
 *
 * @see com.mipt.popikovdmitriy.service.TaskService
 */
@RestController
@Validated
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;
  private final RequestScopedBean requestScopedBean;
  private final PrototypeBeanService prototypeBeanService;

  public TaskController(TaskService taskService,
                        RequestScopedBean requestScopedBean,
                        PrototypeBeanService prototypeBeanService) {
    this.taskService = taskService;
    this.requestScopedBean = requestScopedBean;
    this.prototypeBeanService = prototypeBeanService;
  }

  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody @Valid CreateTaskRequest request) {
    Task created = taskService.createTask(request.getTitle(),
        request.getDescription(),
        request.isCompleted());
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .header("Location", "/api/tasks/" + created.getId())
        .body(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTask(@PathVariable @Min(1) Long id) {
    return ResponseEntity.ok(taskService.getTaskById(id));
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAllTasks() {
    return ResponseEntity.ok(taskService.getAllTasks());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable @Min(1) Long id, @RequestBody @Valid CreateTaskRequest update) {
    return ResponseEntity.ok(
        taskService.updateTask(id, update.getTitle(), update.getDescription(),
            update.isCompleted()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable @Min(1) Long id) {
    taskService.deleteTaskById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/scope/request")
  public ResponseEntity<Map<String, Object>> requestScope() {
    String id1 = requestScopedBean.getRequestId();
    Instant startedAt1 = requestScopedBean.getStartedAt();

    String id2 = requestScopedBean.getRequestId();
    Instant startedAt2 = requestScopedBean.getStartedAt();

    return ResponseEntity.ok(Map.of(
        "requestId1", id1,
        "requestId2", id2,
        "sameInstanceWithinRequest", id1.equals(id2) && startedAt1.equals(startedAt2),
        "startedAt", startedAt1.toString()));
  }

  @GetMapping("/scope/prototype")
  public ResponseEntity<Map<String, Object>> prototypeScope() {
    PrototypeScopedBean b1 = prototypeBeanService.newPrototypeBean();
    PrototypeScopedBean b2 = prototypeBeanService.newPrototypeBean();

    return ResponseEntity.ok(Map.of(
        "instanceId1", b1.getInstanceId(),
        "instanceId2", b2.getInstanceId(),
        "taskId1", b1.newTaskId(),
        "taskId2", b2.newTaskId(),
        "differentInstances", !b1.getInstanceId().equals(b2.getInstanceId())));
  }
}
