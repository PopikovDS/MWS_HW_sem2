package com.mipt.popikovdmitriy.service;

import org.springframework.stereotype.Service;

import com.mipt.popikovdmitriy.repository.TaskRepository;

@Service
public class TaskStatisticsService {

    private final TaskRepository repository;

    public TaskStatisticsService(TaskRepository repository) {
        this.repository = repository;
    }

    public long countAll() {
        return repository.count();
    }

    public String compareRepositories() {
        long primary = repository.count();
        long stub = 0L; // stub repository removed; return placeholder
        return "primary=" + primary + ", stub=" + stub;
    }

}
