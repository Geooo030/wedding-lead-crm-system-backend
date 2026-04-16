package com.leadcrm.infrastructure.repository;

import com.leadcrm.domain.task.aggregate.AgentTask;
import com.leadcrm.domain.task.vo.TaskStatus;
import com.leadcrm.domain.task.vo.TaskPriority;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    AgentTask save(AgentTask task);
    Optional<AgentTask> findById(Long id);
    void deleteById(Long id);
    List<AgentTask> findAll();
    List<AgentTask> findByAgentId(Long agentId);
    List<AgentTask> findByStatus(TaskStatus status);
    List<AgentTask> findByPriority(TaskPriority priority);
    List<AgentTask> findByLeadId(Long leadId);
    List<AgentTask> findByDeadlineBefore(String deadline);
    List<Object[]> countByStatus();
    List<Object[]> countByPriority();
    List<Object[]> getAgentSummary();
}