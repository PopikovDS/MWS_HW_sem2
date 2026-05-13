package com.mipt.popikovdmitriy.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.mipt.popikovdmitriy.model.TaskAttachment;

@Repository
public class InMemoryTaskAttachmentRepository implements TaskAttachmentRepository {

  private final Map<Long, TaskAttachment> storage = new ConcurrentHashMap<>();
  private final AtomicLong idSequence = new AtomicLong(0);

  @Override
  public TaskAttachment create(TaskAttachment attachment) {
    Long id = idSequence.incrementAndGet();
    attachment.setId(id);
    storage.put(id, attachment);
    return attachment;
  }

  @Override
  public Optional<TaskAttachment> findById(Long id) {
    if (id == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(storage.get(id));
  }

  @Override
  public List<TaskAttachment> findAllByTaskId(Long taskId) {
    List<TaskAttachment> result = new ArrayList<>();
    for (TaskAttachment attachment : storage.values()) {
      if (taskId != null && taskId.equals(attachment.getTaskId())) {
        result.add(attachment);
      }
    }
    return result;
  }

  @Override
  public boolean deleteById(Long id) {
    if (id == null) {
      return false;
    }
    return storage.remove(id) != null;
  }
}
