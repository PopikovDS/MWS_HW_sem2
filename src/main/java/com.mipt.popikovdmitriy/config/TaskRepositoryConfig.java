package com.mipt.popikovdmitriy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mipt.popikovdmitriy.repository.StubTaskRepository;
import com.mipt.popikovdmitriy.repository.TaskRepository;

/**
 * Configuration class that declares additional
 * {@link com.mipt.popikovdmitriy.repository.TaskRepository} bean definitions.
 *
 * <p>
 * Registers a {@link com.mipt.popikovdmitriy.repository.StubTaskRepository} as
 * a named bean so that it can be injected alongside the primary in-memory
 * implementation using the {@code @Qualifier("stubTaskRepository")}
 * annotation.</p>
 */
@Configuration
public class TaskRepositoryConfig {

    @Bean
    public TaskRepository stubTaskRepository() {
        return new StubTaskRepository();
    }
}

