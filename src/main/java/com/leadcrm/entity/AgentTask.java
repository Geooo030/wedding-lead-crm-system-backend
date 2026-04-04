package com.leadcrm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "agent_tasks")
public class AgentTask {
    @Id
    @Column(length = 36)
    private String id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", length = 20, nullable = false)
    private TaskType taskType;
    
    @Column(name = "task_params", columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private String taskParams;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TaskStatus status = TaskStatus.pending;
    
    @Column(columnDefinition = "JSON")
    @JdbcTypeCode(SqlTypes.JSON)
    private String result;
    
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }
    
    public enum TaskType {
        lead_hunt, report_summary, custom
    }
    
    public enum TaskStatus {
        pending, running, completed, failed
    }
}