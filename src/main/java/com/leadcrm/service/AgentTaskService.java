package com.leadcrm.service;

import com.leadcrm.entity.AgentTask;
import com.leadcrm.repository.AgentTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgentTaskService {
    
    private final AgentTaskRepository agentTaskRepository;
    
    public AgentTaskService(AgentTaskRepository agentTaskRepository) {
        this.agentTaskRepository = agentTaskRepository;
    }
    
    public List<AgentTask> getRecentTasks() {
        return agentTaskRepository.findTop10ByOrderByCreatedAtDesc();
    }
    
    public List<AgentTask> getPendingTasks() {
        return agentTaskRepository.findByStatusOrderByCreatedAtDesc(AgentTask.TaskStatus.pending);
    }
    
    public AgentTask findById(String id) {
        return agentTaskRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public AgentTask createTask(AgentTask task) {
        if (task.getId() == null) {
            task.setId(java.util.UUID.randomUUID().toString());
        }
        if (task.getScheduledAt() == null) {
            task.setScheduledAt(LocalDateTime.now());
        }
        return agentTaskRepository.save(task);
    }
    
    @Transactional
    public AgentTask save(AgentTask task) {
        return agentTaskRepository.save(task);
    }
}