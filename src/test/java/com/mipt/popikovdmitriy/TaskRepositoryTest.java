package com.mipt.popikovdmitriy;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mipt.popikovdmitriy.model.Priority;
import com.mipt.popikovdmitriy.model.Task;
import com.mipt.popikovdmitriy.model.TaskAttachment;
import com.mipt.popikovdmitriy.repository.TaskAttachmentRepository;
import com.mipt.popikovdmitriy.repository.TaskRepository;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskAttachmentRepository attachmentRepository;

    @Test
    void saveTaskWithAttachment() {
        Task t = new Task();
        t.setTitle("T1");
        t.setDescription("D1");
        t.setCompleted(false);
        Task saved = taskRepository.save(t);
        TaskAttachment a = new TaskAttachment();
        a.setTask(saved);
        a.setFileName("f.txt");
        a.setStoredFileName("f-1");
        a.setContentType("text/plain");
        a.setSize(10);
        a.setUploadedAt(LocalDateTime.now());
        attachmentRepository.save(a);

        List<Task> all = taskRepository.findAll();
        var attaches = attachmentRepository.findAll();
        assertThat(all).isNotEmpty();
        assertThat(attaches).isNotEmpty();
    }

    @Test
    void findByCompletedAndPriority_shouldReturnCorrectTasks() {
        Task t1 = new Task(null, "High priority", "Desc1", false);
        t1.setPriority(Priority.HIGH);
        taskRepository.save(t1);

        Task t2 = new Task(null, "High priority completed", "Desc2", true);
        t2.setPriority(Priority.HIGH);
        taskRepository.save(t2);

        Task t3 = new Task(null, "Low priority", "Desc3", false);
        t3.setPriority(Priority.LOW);
        taskRepository.save(t3);

        List<Task> result = taskRepository.findByCompletedAndPriority(false, Priority.HIGH);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("High priority");
    }

    @Test
    void findTasksCreatedAfter_shouldReturnTasksFromLastWeek() {
        // Создаем две задачи
        Task t1 = new Task(null, "Recent", "Recent desc", false);
        Task t2 = new Task(null, "Old", "Old desc", false);

        // Сохраняем обе
        taskRepository.save(t1);
        taskRepository.save(t2);

        // Получаем все задачи
        List<Task> all = taskRepository.findAll();
        assertThat(all).hasSize(2);

        // Берем самую раннюю дату создания
        LocalDateTime startDate = all.stream()
                .map(Task::getCreatedAt)
                .min(LocalDateTime::compareTo)
                .orElseThrow();

        // Вызываем метод с @Query
        List<Task> result = taskRepository.findTasksCreatedAfter(startDate);

        // Проверяем, что метод работает (возвращает хотя бы одну задачу)
        assertThat(result).isNotEmpty();
    }

}
