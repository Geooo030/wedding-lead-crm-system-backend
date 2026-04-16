package com.leadcrm.domain.task.service;

import com.leadcrm.domain.task.aggregate.AgentTask;
import com.leadcrm.domain.task.vo.TaskInfo;
import com.leadcrm.domain.task.vo.TaskStatus;
import com.leadcrm.domain.task.vo.TaskPriority;

public interface TaskDomainService {
    AgentTask createTask(TaskInfo taskInfo, TaskPriority priority, Long agentId, 
                        Long leadId, String deadline);
    
    void updateTask(AgentTask task, TaskInfo taskInfo, TaskPriority priority, 
                   String deadline);
    
    void changeTaskStatus(AgentTask task, TaskStatus newStatus, Long operatorId);
    
    void assignTask(AgentTask task, Long agentId);
    
    void completeTask(AgentTask task, Long operatorId);
}