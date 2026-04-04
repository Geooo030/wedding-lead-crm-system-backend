package com.leadcrm.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "agent_tasks")
public class AgentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", length = 20, nullable = false)
    private TaskType taskType;
    
    @Column(name = "task_params", columnDefinition = "TEXT")
    private String taskParams;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TaskStatus status = TaskStatus.pending;
    
    @Column(columnDefinition = "TEXT")
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
        createdAt = LocalDateTime.now();
    }
    
    public enum TaskType {
        lead_hunt, report_summary, custom
    }
    
    public enum TaskStatus {
        pending, running, completed, failed
    }
}
