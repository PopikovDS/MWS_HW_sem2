package com.mipt.popikovdmitriy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskCreateDto {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @NotNull(message = "Description must not be null")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    private Boolean completed = false;

    private String priority = "MEDIUM";

    public TaskCreateDto() {
    }

    public TaskCreateDto(String title, String description, Boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = "MEDIUM";
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
