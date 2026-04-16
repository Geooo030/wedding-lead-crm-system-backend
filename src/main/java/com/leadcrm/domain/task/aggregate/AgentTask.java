package com.leadcrm.domain.task.aggregate;

import com.leadcrm.domain.task.vo.TaskInfo;
import com.leadcrm.domain.task.vo.TaskStatus;
import com.leadcrm.domain.task.vo.TaskPriority;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgentTask {
    private Long id;
    private TaskInfo taskInfo;
    private TaskStatus status;
    private TaskPriority priority;
    private Long agentId;
    private Long leadId;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
    private Long createdBy;
    private Long completedBy;
    
    public AgentTask() {
        this.status = TaskStatus.PENDING;
        this.priority = TaskPriority.MEDIUM;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public void changeStatus(TaskStatus newStatus, Long operatorId) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
        if (newStatus == TaskStatus.COMPLETED) {
            this.completedAt = LocalDateTime.now();
            this.completedBy = operatorId;
        }
    }
    
    public void assignTo(Long agentId) {
        this.agentId = agentId;
        this.updatedAt = LocalDateTime.now();
    }
}