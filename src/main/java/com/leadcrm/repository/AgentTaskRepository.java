package com.leadcrm.repository;

import com.leadcrm.entity.AgentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgentTaskRepository extends JpaRepository<AgentTask, Long> {
    List<AgentTask> findByStatusOrderByCreatedAtDesc(AgentTask.TaskStatus status);
    List<AgentTask> findByStatusAndScheduledAtBefore(AgentTask.TaskStatus status, LocalDateTime scheduledAt);
    List<AgentTask> findTop10ByOrderByCreatedAtDesc();
}