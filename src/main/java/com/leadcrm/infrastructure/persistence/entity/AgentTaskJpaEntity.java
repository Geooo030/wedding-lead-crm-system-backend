package com.leadcrm.infrastructure.persistence.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "agent_tasks")
public class AgentTaskJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    private String description;
    
    private String status = "pending";
    
    private String priority = "medium";
    
    @Column(name = "agent_id")
    private Long agentId;
    
    @Column(name = "lead_id")
    private Long leadId;
    
    private LocalDateTime deadline;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "completed_by")
    private Long completedBy;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}