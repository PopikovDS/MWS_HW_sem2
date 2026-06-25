package com.mipt.popikovdmitriy.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mipt.popikovdmitriy.model.Priority;
import com.mipt.popikovdmitriy.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompleted(Boolean completed);

    List<Task> findByCompletedAndPriority(Boolean completed, Priority priority);

    @Query("SELECT t FROM Task t WHERE t.createdAt >= :startDate")
    List<Task> findTasksCreatedAfter(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.attachments")
    List<Task> findAllWithAttachments();

}
