package com.leadcrm.infrastructure.persistence.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "follow_records")
public class FollowRecordJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "lead_id")
    private Long leadId;
    
    @Column(name = "operator_id")
    private Long operatorId;
    
    @Column(name = "agent_id")
    private Long agentId;
    
    @Column(name = "contact_method", length = 20, nullable = false)
    private String contactMethod;
    
    @Column(name = "contact_result", length = 20, nullable = false)
    private String contactResult;
    
    @Column(name = "customer_intention", length = 20)
    private String customerIntention = "medium";
    
    @Column(name = "current_stage", length = 20)
    private String currentStage = "new_lead";
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "next_action", columnDefinition = "TEXT")
    private String nextAction;
    
    @Column(name = "next_action_date")
    private LocalDate nextActionDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", insertable = false, updatable = false)
    private LeadJpaEntity lead;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}