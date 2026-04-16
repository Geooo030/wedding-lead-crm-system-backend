package com.leadcrm.infrastructure.persistence.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "leads", indexes = {
    @Index(name = "idx_country", columnList = "country"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_priority", columnList = "priority_level"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_agent_id", columnList = "agent_id")
})
public class LeadJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "company_name", nullable = false)
    private String companyName;
    
    @Column(name = "company_type", length = 100)
    private String companyType;
    
    @Column(length = 100)
    private String country;
    
    @Column(length = 100)
    private String region;
    
    @Column(length = 500)
    private String address;
    
    @Column(name = "contact_phone", length = 50)
    private String contactPhone;
    
    @Column(name = "contact_email", length = 100)
    private String contactEmail;
    
    private String website;
    
    @Column(name = "business_scope", columnDefinition = "TEXT")
    private String businessScope;
    
    @Column(name = "intent_signals", columnDefinition = "TEXT")
    private String intentSignals;
    
    @Column(name = "decision_maker_role", length = 100)
    private String decisionMakerRole;
    
    @Column(name = "priority_score")
    private Integer priorityScore = 0;
    
    @Column(name = "priority_level", length = 10)
    private String priorityLevel = "warm";
    
    @Column(name = "source_url", length = 500)
    private String sourceUrl;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "lead_source", length = 20)
    private String leadSource = "manual";
    
    @Column(name = "lead_channel", length = 100)
    private String leadChannel;
    
    @Column(name = "follow_operator", length = 100)
    private String followOperator;
    
    @Column(name = "agent_id")
    private Long agentId;
    
    @Column(length = 20)
    private String status = "new_lead";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
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