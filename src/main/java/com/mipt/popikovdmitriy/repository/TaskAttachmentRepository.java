package com.mipt.popikovdmitriy.repository;

import java.util.List;
import java.util.Optional;

import com.mipt.popikovdmitriy.model.TaskAttachment;

public interface TaskAttachmentRepository {

  TaskAttachment create(TaskAttachment attachment);

  Optional<TaskAttachment> findById(Long id);

  List<TaskAttachment> findAllByTaskId(Long taskId);

  boolean deleteById(Long id);
}
