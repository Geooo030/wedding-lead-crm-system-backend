package com.leadcrm.infrastructure.repository.impl;

import com.leadcrm.domain.task.aggregate.AgentTask;
import com.leadcrm.domain.task.vo.*;
import com.leadcrm.infrastructure.persistence.entity.AgentTaskJpaEntity;
import com.leadcrm.infrastructure.persistence.repository.TaskJpaRepository;
import com.leadcrm.infrastructure.repository.TaskRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    
    private final TaskJpaRepository taskJpaRepository;
    
    public TaskRepositoryImpl(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }
    
    @Override
    public AgentTask save(AgentTask task) {
        AgentTaskJpaEntity jpaEntity = toJpaEntity(task);
        AgentTaskJpaEntity saved = taskJpaRepository.save(jpaEntity);
        return toDomainModel(saved);
    }
    
    @Override
    public Optional<AgentTask> findById(Long id) {
        return taskJpaRepository.findById(id).map(this::toDomainModel);
    }
    
    @Override
    public void deleteById(Long id) {
        taskJpaRepository.deleteById(id);
    }
    
    @Override
    public List<AgentTask> findAll() {
        return taskJpaRepository.findAll().stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgentTask> findByAgentId(Long agentId) {
        return taskJpaRepository.findByAgentId(agentId).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgentTask> findByStatus(TaskStatus status) {
        return taskJpaRepository.findByStatus(status.name()).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgentTask> findByPriority(TaskPriority priority) {
        return taskJpaRepository.findByPriority(priority.name()).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgentTask> findByLeadId(Long leadId) {
        return taskJpaRepository.findByLeadId(leadId).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AgentTask> findByDeadlineBefore(String deadline) {
        return taskJpaRepository.findByDeadlineBefore(deadline).stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Object[]> countByStatus() {
        return taskJpaRepository.countByStatus();
    }
    
    @Override
    public List<Object[]> countByPriority() {
        return taskJpaRepository.countByPriority();
    }
    
    @Override
    public List<Object[]> getAgentSummary() {
        return taskJpaRepository.getAgentSummary();
    }
    
    private AgentTaskJpaEntity toJpaEntity(AgentTask task) {
        AgentTaskJpaEntity entity = new AgentTaskJpaEntity();
        entity.setId(task.getId());
        entity.setTitle(task.getTaskInfo().getTitle());
        entity.setDescription(task.getTaskInfo().getDescription());
        entity.setStatus(task.getStatus().name());
        entity.setPriority(task.getPriority().name());
        entity.setAgentId(task.getAgentId());
        entity.setLeadId(task.getLeadId());
        entity.setDeadline(task.getDeadline());
        entity.setCreatedAt(task.getCreatedAt());
        entity.setUpdatedAt(task.getUpdatedAt());
        entity.setCompletedAt(task.getCompletedAt());
        entity.setCreatedBy(task.getCreatedBy());
        entity.setCompletedBy(task.getCompletedBy());
        return entity;
    }
    
    private AgentTask toDomainModel(AgentTaskJpaEntity entity) {
        AgentTask task = new AgentTask();
        task.setId(entity.getId());
        
        TaskInfo taskInfo = new TaskInfo(
            entity.getTitle(),
            entity.getDescription()
        );
        task.setTaskInfo(taskInfo);
        
        task.setStatus(TaskStatus.valueOf(entity.getStatus()));
        task.setPriority(TaskPriority.valueOf(entity.getPriority()));
        task.setAgentId(entity.getAgentId());
        task.setLeadId(entity.getLeadId());
        task.setDeadline(entity.getDeadline());
        task.setCreatedAt(entity.getCreatedAt());
        task.setUpdatedAt(entity.getUpdatedAt());
        task.setCompletedAt(entity.getCompletedAt());
        task.setCreatedBy(entity.getCreatedBy());
        task.setCompletedBy(entity.getCompletedBy());
        
        return task;
    }
}