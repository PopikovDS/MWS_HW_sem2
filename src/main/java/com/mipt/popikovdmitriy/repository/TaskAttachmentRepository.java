package com.mipt.popikovdmitriy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mipt.popikovdmitriy.model.TaskAttachment;

public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, Long> {

}
