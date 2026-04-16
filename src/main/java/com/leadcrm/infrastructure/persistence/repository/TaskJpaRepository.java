package com.leadcrm.infrastructure.persistence.repository;

import com.leadcrm.infrastructure.persistence.entity.AgentTaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TaskJpaRepository extends JpaRepository<AgentTaskJpaEntity, Long> {
    
    List<AgentTaskJpaEntity> findByAgentId(Long agentId);
    
    List<AgentTaskJpaEntity> findByStatus(String status);
    
    List<AgentTaskJpaEntity> findByPriority(String priority);
    
    List<AgentTaskJpaEntity> findByLeadId(Long leadId);
    
    List<AgentTaskJpaEntity> findByDeadlineBefore(String deadline);
    
    @Query("SELECT t.status, COUNT(t) FROM AgentTaskJpaEntity t GROUP BY t.status")
    List<Object[]> countByStatus();
    
    @Query("SELECT t.priority, COUNT(t) FROM AgentTaskJpaEntity t GROUP BY t.priority")
    List<Object[]> countByPriority();
    
    @Query("SELECT u.username, COUNT(t) FROM AgentTaskJpaEntity t JOIN UserJpaEntity u ON t.agentId = u.id GROUP BY u.username")
    List<Object[]> getAgentSummary();
}