package com.mipt.popikovdmitriy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mipt.popikovdmitriy.exception.TaskNotFoundException;
import com.mipt.popikovdmitriy.model.Task;
import com.mipt.popikovdmitriy.service.TaskService;

@SpringBootTest
@Transactional
class TaskServiceTransactionTest {

  @Autowired
  private TaskService taskService;

  @Test
  void bulkCompleteTasks_shouldRollbackOnNonExistentId() {
    // Создаем две задачи
    Task t1 = taskService.createTask(new Task(null, "Task 1", "Description 1", false));
    Task t2 = taskService.createTask(new Task(null, "Task 2", "Description 2", false));

    // Проверяем, что они не выполнены
    assertThat(taskService.getTaskById(t1.getId()).isCompleted()).isFalse();
    assertThat(taskService.getTaskById(t2.getId()).isCompleted()).isFalse();

    // Пытаемся обновить список, который содержит несуществующий ID
    List<Long> ids = List.of(t1.getId(), t2.getId(), 999L);

    // Должно выброситься исключение
    assertThrows(TaskNotFoundException.class, () -> {
      taskService.bulkCompleteTasks(ids);
    });

    // Проверяем, что ни одна задача не обновилась (транзакция откатилась)
    assertThat(taskService.getTaskById(t1.getId()).isCompleted()).isFalse();
    assertThat(taskService.getTaskById(t2.getId()).isCompleted()).isFalse();
  }

  @Test
  void bulkCompleteTasks_shouldUpdateAllTasksWhenAllIdsExist() {
    // Создаем две задачи
    Task t1 = taskService.createTask(new Task(null, "Task 1", "Description 1", false));
    Task t2 = taskService.createTask(new Task(null, "Task 2", "Description 2", false));

    // Проверяем, что они не выполнены
    assertThat(taskService.getTaskById(t1.getId()).isCompleted()).isFalse();
    assertThat(taskService.getTaskById(t2.getId()).isCompleted()).isFalse();

    // Обновляем список
    List<Long> ids = List.of(t1.getId(), t2.getId());
    taskService.bulkCompleteTasks(ids);

    // Проверяем, что обе задачи стали выполненными
    assertThat(taskService.getTaskById(t1.getId()).isCompleted()).isTrue();
    assertThat(taskService.getTaskById(t2.getId()).isCompleted()).isTrue();
  }
}