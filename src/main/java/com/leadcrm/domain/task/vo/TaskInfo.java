package com.leadcrm.domain.task.vo;

import lombok.Value;

@Value
public class TaskInfo {
    private String title;
    private String description;
    
    public TaskInfo(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        this.title = title;
        this.description = description;
    }
}