package com.mipt.popikovdmitriy.config;

import com.mipt.popikovdmitriy.repository.StubTaskRepository;
import com.mipt.popikovdmitriy.repository.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskRepositoryConfig {

    @Bean
    public TaskRepository stubTaskRepository() {
        return new StubTaskRepository();
    }
}
