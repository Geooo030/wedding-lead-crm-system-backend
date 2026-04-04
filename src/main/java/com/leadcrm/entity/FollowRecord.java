package com.leadcrm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "follow_records")
public class FollowRecord {
    @Id
    @Column(length = 36)
    private String id;
    
    @Column(name = "lead_id", length = 36, nullable = false)
    private String leadId;
    
    @Column(name = "operator_id", length = 36, nullable = false)
    private String operatorId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_method", length = 20, nullable = false)
    private ContactMethod contactMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_result", length = 20, nullable = false)
    private ContactResult contactResult;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_intention", length = 20)
    private CustomerIntention customerIntention = CustomerIntention.medium;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "current_stage", length = 20)
    private FollowStage currentStage = FollowStage.new_lead;
    
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
    private Lead lead;
    
    @PrePersist
    protected void onCreate() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }
    
    public enum ContactMethod {
        phone, whatsapp, email, visit
    }
    
    public enum ContactResult {
        reached, unreachable, callback, failed
    }
    
    public enum CustomerIntention {
        high, medium, low, none
    }
    
    public enum FollowStage {
        new_lead, first_contact, requirement, quotation, deal
    }
}