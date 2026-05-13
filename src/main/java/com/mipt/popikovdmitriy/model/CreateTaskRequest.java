package com.mipt.popikovdmitriy.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object used for creating and updating tasks.
 *
 * <p>
 * Carries the client-supplied task fields and enforces basic validation
 * constraints via Jakarta Bean Validation annotations:
 * <ul>
 * <li>{@code title} — mandatory, max 100 characters</li>
 * <li>{@code description} — optional, max 500 characters</li>
 * <li>{@code completed} — defaults to {@code false}</li>
 * </ul>
 */
public class CreateTaskRequest {

  @NotBlank(message = "Название задачи не может быть пустым")
  @Size(max = 100, message = "Название задачи не может быть длиннее 100 символов")

  private String title;

  @NotNull(message = "Описание задачи не может быть null, но может быть пустым")
  @Size(max = 500, message = "Описание задачи не может быть длиннее 500 символов")

  private String description;

  private boolean completed = false;

  public CreateTaskRequest() {
  }

  public CreateTaskRequest(String title, String description, boolean completed) {
    this.title = title;
    this.description = description;
    this.completed = completed;
  }

  public CreateTaskRequest(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
