package com.leadcrm.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "leads", indexes = {
    @Index(name = "idx_country", columnList = "country"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_priority", columnList = "priority_level"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class Lead {
    @Id
    @Column(length = 36)
    private String id;
    
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
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level", length = 10)
    private PriorityLevel priorityLevel = PriorityLevel.warm;
    
    @Column(name = "source_url", length = 500)
    private String sourceUrl;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LeadStatus status = LeadStatus.new_lead;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        if (id == null) id = java.util.UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum PriorityLevel {
        hot, warm, cold
    }
    
    public enum LeadStatus {
        new_lead, contacting, negotiating, converted, lost
    }
}
