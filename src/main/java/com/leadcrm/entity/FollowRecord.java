package com.leadcrm.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "follow_records")
public class FollowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "lead_id")
    private Long leadId;
    
    @Column(name = "operator_id")
    private Long operatorId;
    
    @Column(name = "agent_id")
    private Long agentId;
    
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
    @JsonIgnore
    private Lead lead;
    
    @PrePersist
    protected void onCreate() {
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
