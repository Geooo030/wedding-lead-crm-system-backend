package com.leadcrm.application.service;

import com.leadcrm.application.command.CreateTaskCommand;
import com.leadcrm.application.command.UpdateTaskCommand;
import com.leadcrm.application.query.TaskQuery;
import com.leadcrm.domain.task.aggregate.AgentTask;
import com.leadcrm.domain.task.service.TaskDomainService;
import com.leadcrm.domain.task.vo.TaskInfo;
import com.leadcrm.domain.task.vo.TaskPriority;
import com.leadcrm.domain.task.vo.TaskStatus;
import com.leadcrm.infrastructure.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskApplicationService {
    
    private final TaskRepository taskRepository;
    private final TaskDomainService taskDomainService;
    
    public TaskApplicationService(TaskRepository taskRepository, TaskDomainService taskDomainService) {
        this.taskRepository = taskRepository;
        this.taskDomainService = taskDomainService;
    }
    
    @Transactional
    public AgentTask createTask(CreateTaskCommand command) {
        TaskInfo taskInfo = new TaskInfo(
            command.getTitle(),
            command.getDescription()
        );
        
        TaskPriority priority = TaskPriority.valueOf(command.getPriority());
        
        AgentTask task = taskDomainService.createTask(
            taskInfo,
            priority,
            command.getAgentId(),
            command.getLeadId(),
            command.getDeadline()
        );
        
        task.setCreatedBy(command.getCreatedBy());
        
        return taskRepository.save(task);
    }
    
    @Transactional
    public AgentTask updateTask(UpdateTaskCommand command) {
        Optional<AgentTask> optionalTask = taskRepository.findById(command.getId());
        if (!optionalTask.isPresent()) {
            throw new RuntimeException("Task not found");
        }
        
        AgentTask task = optionalTask.get();
        
        if (command.getTitle() != null) {
            TaskInfo taskInfo = new TaskInfo(
                command.getTitle(),
                command.getDescription()
            );
            taskDomainService.updateTask(task, taskInfo, null, command.getDeadline());
        }
        
        if (command.getPriority() != null) {
            TaskPriority priority = TaskPriority.valueOf(command.getPriority());
            taskDomainService.updateTask(task, null, priority, command.getDeadline());
        }
        
        return taskRepository.save(task);
    }
    
    @Transactional
    public AgentTask changeTaskStatus(Long id, String status, Long operatorId) {
        Optional<AgentTask> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            throw new RuntimeException("Task not found");
        }
        
        AgentTask task = optionalTask.get();
        taskDomainService.changeTaskStatus(task, TaskStatus.valueOf(status), operatorId);
        
        return taskRepository.save(task);
    }
    
    public Optional<AgentTask> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    public List<AgentTask> searchTasks(TaskQuery query) {
        if (query.getAgentId() != null) {
            return taskRepository.findByAgentId(query.getAgentId());
        }
        if (query.getStatus() != null) {
            return taskRepository.findByStatus(TaskStatus.valueOf(query.getStatus()));
        }
        if (query.getPriority() != null) {
            return taskRepository.findByPriority(TaskPriority.valueOf(query.getPriority()));
        }
        if (query.getLeadId() != null) {
            return taskRepository.findByLeadId(query.getLeadId());
        }
        return taskRepository.findAll();
    }
    
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    
    public List<Object[]> countByStatus() {
        return taskRepository.countByStatus();
    }
    
    public List<Object[]> countByPriority() {
        return taskRepository.countByPriority();
    }
    
    public List<Object[]> getAgentSummary() {
        return taskRepository.getAgentSummary();
    }
}