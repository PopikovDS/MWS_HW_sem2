package com.mipt.popikovdmitriy.scope;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeScopedBean {

    private final String instanceId;

    public PrototypeScopedBean() {
        this.instanceId = UUID.randomUUID().toString();
    }

    public String newTaskId() {
        return "task-" + instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }
}
