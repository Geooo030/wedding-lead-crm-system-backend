package com.leadcrm.domain.task.service.impl;

import com.leadcrm.domain.task.aggregate.AgentTask;
import com.leadcrm.domain.task.service.TaskDomainService;
import com.leadcrm.domain.task.vo.TaskInfo;
import com.leadcrm.domain.task.vo.TaskStatus;
import com.leadcrm.domain.task.vo.TaskPriority;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class TaskDomainServiceImpl implements TaskDomainService {
    
    @Override
    public AgentTask createTask(TaskInfo taskInfo, TaskPriority priority, Long agentId, 
                              Long leadId, String deadline) {
        AgentTask task = new AgentTask();
        task.setTaskInfo(taskInfo);
        task.setPriority(priority);
        task.setAgentId(agentId);
        task.setLeadId(leadId);
        if (deadline != null) {
            task.setDeadline(LocalDateTime.parse(deadline, DateTimeFormatter.ISO_DATE_TIME));
        }
        return task;
    }
    
    @Override
    public void updateTask(AgentTask task, TaskInfo taskInfo, TaskPriority priority, 
                         String deadline) {
        if (taskInfo != null) {
            task.setTaskInfo(taskInfo);
        }
        if (priority != null) {
            task.setPriority(priority);
        }
        if (deadline != null) {
            task.setDeadline(LocalDateTime.parse(deadline, DateTimeFormatter.ISO_DATE_TIME));
        }
    }
    
    @Override
    public void changeTaskStatus(AgentTask task, TaskStatus newStatus, Long operatorId) {
        task.changeStatus(newStatus, operatorId);
    }
    
    @Override
    public void assignTask(AgentTask task, Long agentId) {
        task.assignTo(agentId);
    }
    
    @Override
    public void completeTask(AgentTask task, Long operatorId) {
        task.changeStatus(TaskStatus.COMPLETED, operatorId);
    }
}